package com.swms.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.swms.user.config.prop.SystemProp;
import com.swms.user.repository.entity.Role;
import com.swms.user.repository.entity.RoleMenu;
import com.swms.user.repository.mapper.RoleMapper;
import com.swms.user.repository.mapper.UserMapper;
import com.swms.user.rest.common.enums.YesOrNo;
import com.swms.user.rest.param.role.RoleAddParam;
import com.swms.user.rest.param.role.RoleMenuUpdateParam;
import com.swms.user.rest.param.role.RoleUpdateParam;
import com.swms.user.service.MenuService;
import com.swms.user.service.RoleMenuService;
import com.swms.user.service.RoleService;
import com.swms.user.service.UserRoleService;
import com.swms.user.service.model.MenuTree;
import com.swms.utils.exception.WmsException;
import com.swms.utils.exception.code_enum.UserErrorDescEnum;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMenuService roleMenuService;
    private final UserRoleService userRoleService;
    private final UserMapper userMapper;
    private final SystemProp systemProp;
    private final MenuService menuService;


    @Override
    public boolean exist(String roleCode) {
        if (StrUtil.isEmpty(roleCode)) {
            return false;
        }
        Wrapper<Role> wrapper = Wrappers.<Role>lambdaQuery()
            .eq(Role::getCode, roleCode);

        return count(wrapper) > 0;
    }

    @Override
    public synchronized void addRole(RoleAddParam param) throws Exception {
        checkRoleCode(param.getCode());
        Role role = new Role();
        BeanUtils.copyProperties(param, role);
        save(role);
    }

    @Override
    public synchronized void updateRole(RoleUpdateParam param) throws Exception {
        Role role = getById(param.getId());
        if (!Objects.equal(role.getCode(), param.getCode())) {
            checkRoleCode(param.getCode());
        }
        checkSuperRole(role);
        Role updateBean = new Role();
        BeanUtils.copyProperties(param, updateBean);
        updateById(updateBean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized void deleteRole(Long roleId) throws Exception {
        if (null == roleId) {
            return;
        }
        Role role = getById(roleId);
        if (role == null) {
            return;
        }
        checkSuperRole(role);
        checkRoleStatus(role);
        // 先删除角色, 再删除关联权限和菜单
        removeById(roleId);
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
        Role role = getById(roleId);
        if (role == null) {
            throw new WmsException(UserErrorDescEnum.ERR_ROLE_NOT_FOUND);
        }
        checkSuperRole(role);
        if (Objects.equal(role.getStatus(), status)) {
            return;
        }
        Role updateBean = new Role();
        updateBean.setId(roleId);
        updateBean.setStatus(status);
        updateById(updateBean);
    }

    @Override
    public synchronized void updateRoleMenu(RoleMenuUpdateParam param) throws Exception {
        Role role = getById(param.getRoleId());
        if (role == null) {
            throw new WmsException(UserErrorDescEnum.ERR_ROLE_NOT_FOUND);
        }
        checkSuperRole(role);
        roleMenuService.removeByRoleId(param.getRoleId());
        Set<Long> menus = param.getMenus();
        if (menus == null || menus.isEmpty()) {
            return;
        }
        List<RoleMenu> roleMenus = menus.stream()
            .map(m -> {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(param.getRoleId());
                roleMenu.setMenuId(m);
                roleMenu.setIsParent(Integer.valueOf(YesOrNo.NO.getCode()));
                return roleMenu;
            })
            .collect(Collectors.toList());

        //前端无法传入parentMenus，此处需要后端来计算
        Set<Long> parents = Sets.newHashSet();
        List<MenuTree> menuTree = menuService.getAllMenuTreeNoChildren();
        if (!CollectionUtils.isEmpty(menuTree)) {
            Map<Long, MenuTree> newMap = Maps.newHashMap();
            for (MenuTree tree : menuTree) {
                newMap.put(tree.getId(), tree);
            }
            getParentMenus(menus, newMap, parents, menus);
        }
        if (!CollectionUtils.isEmpty(parents)) {
            for (Long parentMenuId : parents) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(param.getRoleId());
                roleMenu.setMenuId(parentMenuId);
                roleMenu.setIsParent(Integer.valueOf(YesOrNo.YES.getCode()));
                roleMenus.add(roleMenu);
            }
        }

        roleMenuService.saveBatch(roleMenus);
    }

    private void checkSuperRole(Role role) throws Exception {
        String superAdminCode = systemProp.getSuperRoleCode();
        if (Objects.equal(superAdminCode, role.getCode())) {
            throw new WmsException(UserErrorDescEnum.ERR_ROLE_ADMIN_IS_IMMUTABLE);
        }
    }

    private void checkRoleCode(String roleCode) throws Exception {
        if (exist(roleCode)) {
            throw new WmsException(UserErrorDescEnum.ERR_ROLE_CODE_EXISTS);
        }
    }

    private void getParentMenus(Set<Long> menus, Map<Long, MenuTree> newMap, Set<Long> parent, Set<Long> oldMenus) {
        if (CollectionUtils.isEmpty(menus) || CollectionUtils.isEmpty(newMap)) {
            return;
        }
        Set<Long> result = Sets.newHashSet();
        for (Long mId : menus) {
            MenuTree menu = newMap.get(mId);
            if (null == menu) {
                continue;
            }
            if (null != menu.getParentId() && menu.getParentId() != 0
                && !menus.contains(menu.getParentId()) && !oldMenus.contains(menu.getParentId())) {
                result.add(menu.getParentId());
            }
        }
        parent.addAll(result);
        getParentMenus(result, newMap, parent, oldMenus);
    }

    private void checkRoleStatus(Role role) throws Exception {
        // 当角色处于启用状态且被用户关联时，不允许删除
        if (Objects.equal(role.getStatus().toString(), YesOrNo.YES.getCode())) {
            if (!CollectionUtils.isEmpty(userRoleService.getByRoleId(role.getId()))) {
                throw new WmsException(UserErrorDescEnum.ERR_ROLE_IS_ENABLE_AND_USED);
            }
        }
    }
}
