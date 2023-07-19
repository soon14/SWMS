package com.swms.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.swms.user.api.UserContext;
import com.swms.user.config.prop.SystemProp;
import com.swms.user.repository.entity.Menu;
import com.swms.user.repository.entity.Role;
import com.swms.user.repository.entity.RoleMenu;
import com.swms.user.repository.entity.User;
import com.swms.user.repository.entity.UserRole;
import com.swms.user.repository.mapper.MenuMapper;
import com.swms.user.repository.mapper.RoleMapper;
import com.swms.user.repository.mapper.RoleMenuMapper;
import com.swms.user.repository.mapper.UserMapper;
import com.swms.user.repository.mapper.UserRoleMapper;
import com.swms.user.rest.common.enums.UserTypeEnum;
import com.swms.user.rest.common.enums.YesOrNo;
import com.swms.user.rest.param.user.UserAddParam;
import com.swms.user.rest.param.user.UserUpdateParam;
import com.swms.user.service.UserRoleService;
import com.swms.user.service.UserService;
import com.swms.user.service.model.AuthUserInfo;
import com.swms.user.service.model.PermissionGrantedAuthority;
import com.swms.user.service.model.RoleGrantedAuthority;
import com.swms.user.service.model.UserDetailsModel;
import com.swms.utils.exception.WmsException;
import com.swms.utils.exception.code_enum.UserErrorDescEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author sws
 * @since 2020-12-25
 */
@SuppressWarnings("ALL")
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final RoleMapper roleMapper;
    private final UserRoleService userRoleService;
    private final MenuMapper menuMapper;
    private final SystemProp systemProp;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    private User superUser;

    public UserServiceImpl(RoleMapper roleMapper,
                           UserRoleService userRoleService,
                           MenuMapper menuMapper,
                           SystemProp systemProp,
                           PasswordEncoder passwordEncoder) {
        this.roleMapper = roleMapper;
        this.userRoleService = userRoleService;
        this.menuMapper = menuMapper;
        this.systemProp = systemProp;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StrUtil.isEmpty(username)) {
            throw new WmsException(UserErrorDescEnum.ERR_WRONG_CREDENTIALS);
        }
        User user = userMapper.findByUsername(username);
        if (!user.getUsername().equals(username)) {
            //mysql对大小写不敏感,单独处理大小写不匹配
            throw new WmsException(UserErrorDescEnum.ERR_WRONG_CREDENTIALS);
        }

        if (UserTypeEnum.NORMAL.getCode().equalsIgnoreCase(user.getType())) {
            return new UserDetailsModel(user, getPermissionModels(user));
        } else {
            log.error("username:{},user:{}", username, user);
            throw new WmsException(UserErrorDescEnum.ERR_WRONG_CREDENTIALS);
        }
    }

    @Override
    public AuthUserInfo getAuthUserInfo() {
        String currentUsername = UserContext.getCurrentUser();
        if (StrUtil.isEmpty(currentUsername)) {
            throw new WmsException(UserErrorDescEnum.NO_AUTHED_USER_FOUND);
        }
        User user = getByUsername(currentUsername);
        if (user == null) {
            throw new WmsException(UserErrorDescEnum.NO_AUTHED_USER_FOUND);
        }
        AuthUserInfo authUserInfo = new AuthUserInfo();
        authUserInfo.setName(user.getName());
        authUserInfo.setUsername(currentUsername);
        authUserInfo.setRoles(UserContext.getCurrentRoleCodes());
        authUserInfo.setPermissions(UserContext.getCurrentPermissions());
        authUserInfo.setAvatar(user.getAvatar());
        authUserInfo.setLastLoginIp(user.getLastLoginIp());
        authUserInfo.setLastGmtLoginTime(user.getLastGmtLoginTime());
        authUserInfo.setPhone(user.getPhone());
        authUserInfo.setEmail(user.getEmail());
        authUserInfo.setRoleNames(authUserInfo.getRoles().stream().map(v -> v).collect(Collectors.joining(",")));
        return authUserInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUser(UserAddParam param) {
        String username = param.getUsername();
        User databaseUser = getByUsername(username);
        if (databaseUser != null) {
            throw new WmsException(UserErrorDescEnum.ERR_USER_NAME_EXISTS);
        }

        User user = new User();
        BeanUtils.copyProperties(param, user);
        // 设置密码
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setLocked(0);
        if (StringUtils.isEmpty(user.getUsername())) {
            user.setUsername(user.getName());
        }
        userMapper.save(user);

        if (StringUtils.isNotEmpty(param.getRoleIds())) {
            Set<Long> roleIds = Arrays.stream(param.getRoleIds().split(",")).map(Long::parseLong).collect(Collectors.toSet());
            List<UserRole> userRoles = getUserRole(user.getId(), roleIds);
            // 分配角色
            userRoleMapper.saveAll(userRoles);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUser(UserUpdateParam param) {
        checkSuperUser(param.getId());
        synchronized (this) {
            User user = userMapper.findById(param.getId()).orElseThrow(() -> new WmsException(UserErrorDescEnum.NO_AUTHED_USER_FOUND));
            if (!Objects.equals(user.getUsername(), param.getUsername())) {
                if (getByUsername(param.getUsername()) != null) {
                    throw new WmsException(UserErrorDescEnum.ERR_USER_NAME_EXISTS);
                }
                // 禁止第三方账号修改其在海柔系统内的识别标识：用户名
                if (!Objects.equals(UserTypeEnum.NORMAL, UserTypeEnum.getByCode(user.getType()))) {
                    throw new WmsException("update external account username is not allowed");
                }
            }
            BeanUtils.copyProperties(param, user);
            if (StringUtils.isNotBlank(param.getPassword())) {
                user.setPassword(passwordEncoder.encode(param.getPassword()));
            }
            userMapper.save(user);
            // 处理角色
            userRoleService.removeByUserId(param.getId());
            userRoleService.add(param.getId(), param.getRoleIds());
        }

    }

    @Override
    public void updateStatus(Long userId, Integer status) {
        if (status == null) {
            return;
        }
        checkSuperUser(userId);
        User user = userMapper.findById(userId).orElseThrow(() -> new WmsException(UserErrorDescEnum.NO_AUTHED_USER_FOUND));
        if (Objects.equals(user.getStatus(), status)) {
            return;
        }
        if (Objects.equals(status.toString(), YesOrNo.NO.getCode())) {
            checkSelfUser(userId);
        }
        user.setStatus(status);
        userMapper.save(user);
    }

    @Override
    public void resetPassword(Long userId, String newPassword) {
        if (newPassword.length() < 6) {
            throw new WmsException(UserErrorDescEnum.ERR_CRED_TOO_SHORT);
        }
        User user = userMapper.findById(userId).orElseThrow(() -> new WmsException(UserErrorDescEnum.NO_AUTHED_USER_FOUND));
        user.setPassword(passwordEncoder.encode(newPassword));
        userMapper.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long userId) {
        if (null == userId) {
            return;
        }
        User user = userMapper.findById(userId).orElseThrow(() -> new WmsException(UserErrorDescEnum.NO_AUTHED_USER_FOUND));
        checkSuperUser(userId);
        checkDisabledUser(userId);
        checkSelfUser(userId);
        synchronized (this) {
            userMapper.delete(user);
            userRoleService.removeByUserId(userId);
        }
    }

    @Override
    public synchronized User getSuperAdmin() {
        if (superUser != null) {
            return superUser;
        }
        return userMapper.findById(systemProp.getSuperAdminId())
            .orElseThrow(() -> new WmsException(UserErrorDescEnum.NO_AUTHED_USER_FOUND));
    }

    /**
     * 获取当前用户所拥有的权限
     *
     * @param user 系统用户
     *
     * @return 权限集合
     */
    @Override
    public Set<? extends GrantedAuthority> getPermissionModels(User user) {
        List<UserRole> userRoles = userRoleMapper.findByUserId(user.getId());
        List<Role> roles = roleMapper.findAllById(userRoles.stream().map(v -> v.getRoleId()).toList());

        if (roles == null || roles.isEmpty()) {
            return Collections.emptySet();
        }

        Set<GrantedAuthority> grantedAuthorities = Sets.newHashSet();
        boolean haveSuperRole = false;
        Set<Long> roleIds = Sets.newHashSetWithExpectedSize(roles.size());
        for (Role role : roles) {
            if (role == null || StringUtils.isEmpty(role.getCode())) {
                continue;
            }
            String code = role.getCode();
            if (Objects.equals(systemProp.getSuperRoleCode(), code)) {
                haveSuperRole = true;
            }
            grantedAuthorities.add(new RoleGrantedAuthority(role));
            roleIds.add(role.getId());
        }

        if (haveSuperRole) {
            // 如果是超级角色, 则返回 *:* 权限
            grantedAuthorities.add(new PermissionGrantedAuthority(UserContext.SUPPER_PERMISSION));
            return grantedAuthorities;
        }

        if (roleIds.isEmpty()) {
            return grantedAuthorities;
        }

        List<RoleMenu> roleMenus = roleMenuMapper.findByRoleIdIn(roleIds);
        List<Menu> menus = menuMapper.findAllById(roleMenus.stream().map(v -> v.getMenuId()).toList());
        // 拥有的权限
        if (menus == null || menus.isEmpty()) {
            return grantedAuthorities;
        }

        for (Menu menu : menus) {
            if (menu == null) {
                continue;
            }
            String permissionsString = menu.getPermissions();
            if (StrUtil.isEmpty(permissionsString)) {
                continue;
            }
            if (permissionsString.contains(",")) {
                List<String> splitPermissions = Splitter.on(",")
                    .trimResults()
                    .omitEmptyStrings()
                    .splitToList(permissionsString);
                for (String splitPermission : splitPermissions) {
                    grantedAuthorities.add(new PermissionGrantedAuthority(splitPermission));
                }
            } else {
                grantedAuthorities.add(new PermissionGrantedAuthority(permissionsString));
            }
        }
        return grantedAuthorities;
    }

    @Override
    public void syncUser(UserAddParam param) {
        String username = param.getUsername();
        synchronized (this) {
            User databaseUser = getByUsername(username);
            //新增用户
            if (null == databaseUser) {
                addUser(param);
                return;
            }

            //删除用户
            if (Integer.valueOf(YesOrNo.NO.getCode()).equals(param.getStatus())) {
                checkSuperUser(databaseUser.getId());
                userMapper.delete(databaseUser);
                userRoleService.removeByUserId(databaseUser.getId());
                return;
            }

            //修改用户
            UserUpdateParam param2 = new UserUpdateParam();
            BeanUtils.copyProperties(param, param2);
            param2.setId(databaseUser.getId());
            updateUser(param2);
        }
    }

    @Override
    public User getById(Long id) {
        return userMapper.findById(id).orElseThrow(() -> new WmsException(UserErrorDescEnum.NO_AUTHED_USER_FOUND));
    }

    private List<UserRole> getUserRole(Long userId, Set<Long> roleIds) {
        List<Role> roles = roleMapper.findAllById(roleIds);
        if (roles == null || roles.isEmpty()) {
            throw new WmsException(UserErrorDescEnum.ERR_ROLE_NOT_FOUND);
        }
        List<UserRole> userRoles = Lists.newArrayListWithCapacity(roles.size());
        for (Role role : roles) {
            if (role == null) {
                continue;
            }
            if (role.getStatus() == 0) {
                throw new WmsException(UserErrorDescEnum.ERR_ROLE_IS_DISABLE);
            }
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(role.getId());
            userRoles.add(userRole);
        }
        if (userRoles.isEmpty()) {
            throw new WmsException(UserErrorDescEnum.NO_AUTHED_USER_FOUND);
        }
        return userRoles;
    }

    private void checkSuperUser(Long userId) {
        Long superAdminId = systemProp.getSuperAdminId();
        if (Objects.equals(superAdminId, userId)) {
            throw new WmsException(UserErrorDescEnum.ERR_ROLE_ADMIN_IS_IMMUTABLE);
        }
    }

    private void checkDisabledUser(Long userId) {
        // 不允许删除启用中的用户
        User user = userMapper.findById(userId).orElse(null);
        if (user == null) {
            return;
        }
        if (user.getStatus().toString().equals(YesOrNo.YES.getCode())) {
            throw new WmsException(UserErrorDescEnum.ERR_USER_IS_NOT_DISABLED);
        }
    }

    private void checkSelfUser(Long userId) {
        // 不允许当前用户删除或禁用自身
        String currentUsername = UserContext.getCurrentUser();
        if (StrUtil.isEmpty(currentUsername)) {
            throw new WmsException(UserErrorDescEnum.NO_AUTHED_USER_FOUND);
        }
        User currentUser = getByUsername(currentUsername);
        if (currentUser == null) {
            throw new WmsException(UserErrorDescEnum.NO_AUTHED_USER_FOUND);
        }
        if (Objects.equals(currentUser.getId(), userId)) {
            throw new WmsException(UserErrorDescEnum.ERR_USER_CAN_NOT_DISABLE_OR_DELETE_SELF);
        }
    }
}
