package com.swms.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swms.user.repository.entity.UserRole;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统用户角色关联表 服务类
 * </p>
 *
 * @author sws
 * @since 2020-12-31
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 新增映射
     *
     * @param userId  用户id
     * @param roleIds 角色id集合
     */
    void add(Long userId, Set<Long> roleIds);

    /**
     * 删除角色与用户的关联
     *
     * @param roleId 角色id
     */
    void removeByRoleId(Long roleId);

    /**
     * 删除角色与用户的关联
     *
     * @param userId 用户id
     */
    void removeByUserId(Long userId);

    /**
     * 通过用户名获取角色关联
     *
     * @param userId 用户id
     *
     * @return List<UserRole>
     */
    List<UserRole> getByUserId(Long userId);

    List<UserRole> getByRoleId(Long roleId);
}
