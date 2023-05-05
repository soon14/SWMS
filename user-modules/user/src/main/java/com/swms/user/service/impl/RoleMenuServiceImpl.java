package com.swms.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.swms.user.repository.entity.RoleMenu;
import com.swms.user.repository.mapper.RoleMenuMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swms.user.service.RoleMenuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * <p>
 * 角色菜单权限表 服务实现类
 * </p>
 *
 * @author sws
 * @since 2020-12-31
 */
@Service
@AllArgsConstructor
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Override
    public void removeByMenuId(Collection<Long> menuIds) {
        if (menuIds == null || menuIds.isEmpty()) {
            return;
        }
        Wrapper<RoleMenu> wrapper = Wrappers.<RoleMenu>lambdaQuery()
            .in(RoleMenu::getMenuId, menuIds);
        remove(wrapper);
    }

    @Override
    public void removeByRoleId(Long roleId) {
        if (null == roleId) {
            return;
        }
        Wrapper<RoleMenu> wrapper = Wrappers.<RoleMenu>lambdaQuery()
            .eq(RoleMenu::getRoleId, roleId);
        remove(wrapper);
    }
}
