package com.swms.user.repository.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swms.user.repository.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swms.user.repository.model.UserHasRole;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author sws
 * @since 2020-12-31
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 分页查询用户信息(包含角色信息)
     *
     * @param page     分页信息
     * @param name     查询条件：名称
     * @param username 插件条件：用户名
     * @param status   查询条件：状态
     * @param locked   查询条件：是否被锁
     *
     * @return 分页
     */
    IPage<UserHasRole> getUserAndRoleInfo(IPage<?> page, @Param("name") String name, @Param("username") String username,
                                          @Param("status") Integer status, @Param("locked") Boolean locked);

}
