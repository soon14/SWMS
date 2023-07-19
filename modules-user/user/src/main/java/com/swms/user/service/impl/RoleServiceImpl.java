package com.swms.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Objects;
import com.swms.user.config.prop.SystemProp;
import com.swms.user.repository.entity.Role;
import com.swms.user.repository.entity.RoleMenu;
import com.swms.user.repository.mapper.RoleMapper;
import com.swms.user.repository.mapper.RoleMenuMapper;
import com.swms.user.rest.common.enums.YesOrNo;
import com.swms.user.rest.param.role.RoleAddParam;
import com.swms.user.rest.param.role.RoleMenuUpdateParam;
import com.swms.user.rest.param.role.RoleUpdateParam;
import com.swms.user.service.RoleMenuService;
import com.swms.user.service.RoleService;
import com.swms.user.service.UserRoleService;
import com.swms.utils.exception.WmsException;
import com.swms.utils.exception.code_enum.UserErrorDescEnum;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author sws
 * @since 2020-12-25
 */
@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleMenuService roleMenuService;
    private final UserRoleService userRoleService;
    private final SystemProp systemProp;
    private RoleMapper roleMapper;
    private RoleMenuMapper roleMenuMapper;

    @Override
    public boolean exist(String roleCode) {
        if (StrUtil.isEmpty(roleCode)) {
            return false;
        }
        return roleMapper.findByCode(roleCode) != null;
    }

    @Override
    public synchronized void addRole(RoleAddParam param) throws Exception {
        checkRoleCode(param.getCode());
        Role role = new Role();
        BeanUtils.copyProperties(param, role);
        roleMapper.save(role);
    }

    @Override
    public synchronized void updateRole(RoleUpdateParam param) throws Exception {
        Role role = roleMapper.findById(param.getId()).orElseThrow();
        if (!Objects.equal(role.getCode(), param.getCode())) {
            checkRoleCode(param.getCode());
        }
        checkSuperRole(role);
        BeanUtils.copyProperties(param, role);
        roleMapper.save(role);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized void deleteRole(Long roleId) throws Exception {
        if (null == roleId) {
            return;
        }
        Role role = roleMapper.findById(roleId).orElseThrow(() -> new WmsException(UserErrorDescEnum.ERR_ROLE_NOT_FOUND));
        checkSuperRole(role);
        checkRoleStatus(role);
        // 先删除角色, 再删除关联权限和菜单
        roleMapper.delete(role);
        roleMenuService.removeByRoleId(roleId);
        // 解除该角色对应的用户
        userRoleService.removeByRoleId(roleId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized void updateStatus(Long roleId, Integer status) throws Exception {
        if (null == roleId) {
            return;
        }
        Role role = roleMapper.findById(roleId).orElseThrow(() -> new WmsException(UserErrorDescEnum.ERR_ROLE_NOT_FOUND));
        checkSuperRole(role);
        if (Objects.equal(role.getStatus(), status)) {
            return;
        }
        role.setStatus(status);
        roleMapper.save(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized void updateRoleMenu(RoleMenuUpdateParam param) {
        Role role = roleMapper.findById(param.getRoleId()).orElseThrow(() -> new WmsException(UserErrorDescEnum.ERR_ROLE_NOT_FOUND));
        checkSuperRole(role);
        roleMenuService.removeByRoleId(param.getRoleId());
        Set<Long> menus = param.getMenuSet();
        if (menus == null || menus.isEmpty()) {
            return;
        }
        List<RoleMenu> roleMenus = menus.stream()
            .map(m -> {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(param.getRoleId());
                roleMenu.setMenuId(m);
                return roleMenu;
            }).toList();

        roleMenuMapper.saveAll(roleMenus);
    }

    private void checkSuperRole(Role role) {
        String superAdminCode = systemProp.getSuperRoleCode();
        if (Objects.equal(superAdminCode, role.getCode())) {
            throw new WmsException(UserErrorDescEnum.ERR_ROLE_ADMIN_IS_IMMUTABLE);
        }
    }

    private void checkRoleCode(String roleCode) {
        if (exist(roleCode)) {
            throw new WmsException(UserErrorDescEnum.ERR_ROLE_CODE_EXISTS);
        }
    }

    private void checkRoleStatus(Role role) {
        // 当角色处于启用状态且被用户关联时，不允许删除
        if (Objects.equal(role.getStatus().toString(), YesOrNo.YES.getCode())) {
            if (!CollectionUtils.isEmpty(userRoleService.getByRoleId(role.getId()))) {
                throw new WmsException(UserErrorDescEnum.ERR_ROLE_IS_ENABLE_AND_USED);
            }
        }
    }
}
