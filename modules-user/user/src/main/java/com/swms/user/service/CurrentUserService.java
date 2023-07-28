package com.swms.user.service;

import com.swms.user.rest.param.user.CurrentUserInfoUpdatedParam;

/**
 * 当前用户服务
 *
 * @author sws
 * @version 1.0
 */
public interface CurrentUserService {

    /**
     * 修改当前认证用户的密码
     *
     * @param currentUsername 用户名
     * @param oldPassword     旧密码
     * @param newPassword     新密码
     *
     * @throws Exception 修改密码异常
     */
    void updateCurrentUserPassword(String currentUsername, String oldPassword, String newPassword);

    /**
     * 登录成功
     *
     * @param userId    用户id
     * @param username  用户名
     * @param ip        登录的ip
     * @param timestamp 时间戳
     */
    void loginSuccess(Long userId, String username, String ip, Long timestamp);

    /**
     * 更新当前用户信息
     *
     * @param currentUsername 用户名
     * @param param           更新参数
     *
     * @throws Exception 更新异常
     */
    void updateInfo(String currentUsername, CurrentUserInfoUpdatedParam param);

}
