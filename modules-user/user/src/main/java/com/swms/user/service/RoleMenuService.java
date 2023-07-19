package com.swms.user.service;

import java.util.Collection;


/**
 * <p>
 * 角色菜单权限表 服务类
 * </p>
 *
 * @author sws
 * @since 2020-12-31
 */
public interface RoleMenuService {

    /**
     * 通过菜单id删除
     *
     * @param menuIds 菜单id集合
     */
    void removeByMenuId(Collection<Long> menuIds);


    /**
     * 角色id
     *
     * @param roleId 角色id
     */
    void removeByRoleId(Long roleId);

}
