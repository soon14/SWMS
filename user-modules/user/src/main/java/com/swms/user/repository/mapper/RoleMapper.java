package com.swms.user.repository.mapper;

import com.swms.user.repository.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author sws
 * @since 2020-12-31
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 通过用户id获取角色信息
     *
     * @param userId 用户id
     *
     * @return 角色列表
     */
    List<Role> getRoleByUserId(Long userId);

}
