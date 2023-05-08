package com.swms.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swms.user.repository.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swms.user.service.model.AuthUserInfo;
import com.swms.user.repository.model.UserHasRole;
import com.swms.user.rest.param.user.UserAddParam;
import com.swms.user.rest.param.user.UserPageParam;
import com.swms.user.rest.param.user.UserUpdateParam;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author sws
 * @since 2020-12-31
 */
public interface UserService extends IService<User>, UserDetailsService {

    /**
     * 分页查询用户列表
     *
     * @param param 参数
     *
     * @return 分页结果
     */
    IPage<UserHasRole> getPage(UserPageParam param);


    /**
     * 通过用户名获取用户
     *
     * @param username 用户名
     *
     * @return 用户
     */
    User getByUsername(String username);


    /**
     * 得到当前认证后的用户信息
     *
     * @return AuthUserInfo
     *
     * @throws Exception
     */
    AuthUserInfo getAuthUserInfo() throws Exception;


    /**
     * 添加用户
     *
     * @param param 添加用户参数
     *
     * @throws Exception 添加异常
     */
    void addUser(UserAddParam param) throws Exception;


    /**
     * 修改用户
     *
     * @param param 更新用户参数
     *
     * @throws Exception 添加异常
     */
    void updateUser(UserUpdateParam param) throws Exception;

    /**
     * 修改用户状态
     *
     * @param userId 用户id
     * @param status 状态
     *
     * @throws Exception 更新状态异常
     */
    void updateStatus(Long userId, Integer status) throws Exception;

    /**
     * 重置用户密码
     *
     * @param userId      用户id
     * @param newPassword 重置的新密码
     *
     * @throws Exception 重置密码异常
     */
    void resetPassword(Long userId, String newPassword) throws Exception;


    /**
     * 通过用户id删除用户
     *
     * @param userId 用户id
     *
     * @throws Exception
     */
    void delete(Long userId) throws Exception;

    /**
     * 得到超级管理员用户
     *
     * @return User
     */
    User getSuperAdmin();

    /**
     * 获取当前用户所拥有的权限
     *
     * @param user 系统用户
     *
     * @return 权限集合
     */
    Set<? extends GrantedAuthority> getPermissionModels(User user);

    /**
     * 同步用户（适用于客户用户同步，支持新增，修改和删除）
     *
     * @param param
     */
    void syncUser(UserAddParam param) throws Exception;
}
