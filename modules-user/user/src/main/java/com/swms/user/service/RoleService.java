package com.swms.user.service;

import com.swms.user.repository.entity.Role;
import com.swms.user.rest.param.role.RoleAddParam;
import com.swms.user.rest.param.role.RoleMenuUpdateParam;
import com.swms.user.rest.param.role.RoleUpdateParam;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author sws
 * @since 2020-12-31
 */
public interface RoleService {

    /**
     * 判断角色编号是否存在
     *
     * @param roleCode 角色编号
     *
     * @return 是否存在
     */
    boolean exist(String roleCode);

    /**
     * 添加角色
     *
     * @param param 添加参数
     *
     * @throws Exception Exception 异常
     */
    void addRole(RoleAddParam param);

    /**
     * 修改角色
     *
     * @param param 修改参数
     *
     * @throws Exception 异常
     */
    void updateRole(RoleUpdateParam param);

    /**
     * 删除角色
     *
     * @param roleId 角色id
     *
     * @throws Exception 删除角色异常
     */
    void deleteRole(Long roleId);


    /**
     * 修改角色状态
     *
     * @param roleId 角色id
     * @param status 角色状态
     *
     * @throws Exception 修改异常
     */
    void updateStatus(Long roleId, Integer status);

    /**
     * 修改角色权限
     *
     * @param param 修改角色权限bean
     *
     * @throws Exception
     */
    void updateRoleMenu(RoleMenuUpdateParam param);

    Role getRole(Long id);
}
