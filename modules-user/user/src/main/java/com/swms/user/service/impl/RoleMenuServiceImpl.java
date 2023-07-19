package com.swms.user.service.impl;

import com.swms.user.repository.mapper.RoleMenuMapper;
import com.swms.user.service.RoleMenuService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RoleMenuServiceImpl implements RoleMenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public void removeByMenuId(Collection<Long> menuIds) {
        if (menuIds == null || menuIds.isEmpty()) {
            return;
        }

        roleMenuMapper.deleteByMenuIdIn(menuIds);
    }

    @Override
    public void removeByRoleId(Long roleId) {
        if (null == roleId) {
            return;
        }
        roleMenuMapper.deleteByRoleId(roleId);
    }
}
