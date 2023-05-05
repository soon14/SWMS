package com.swms.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.swms.user.repository.entity.User;
import com.swms.user.rest.param.user.CurrentUserInfoUpdatedParam;
import com.swms.user.service.CurrentUserService;
import com.swms.user.service.LoginLogService;
import com.swms.user.service.UserService;
import com.swms.user.utils.TimeUtil;
import com.swms.utils.exception.WmsException;
import com.swms.utils.exception.code_enum.UserErrorDescEnum;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sws
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class CurrentUserServiceImpl implements CurrentUserService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final LoginLogService loginLogService;

    @Override
    public synchronized void updateCurrentUserPassword(String currentUsername, String oldPassword, String newPassword) throws Exception {
        if (newPassword.length() < 6) {
            throw new WmsException(UserErrorDescEnum.ERR_CRED_TOO_SHORT);
        }
        if (StrUtil.isEmpty(currentUsername)) {
            throw new WmsException(UserErrorDescEnum.NO_AUTHED_USER_FOUND);
        }
        User databaseUser = userService.getByUsername(currentUsername);
        if (databaseUser == null) {
            throw new WmsException(UserErrorDescEnum.NO_AUTHED_USER_FOUND);
        }
        if (!passwordEncoder.matches(oldPassword, databaseUser.getPassword())) {
            throw new WmsException(UserErrorDescEnum.ERROR_WRONG_OLD_CRED);
        }
        User updateBean = new User();
        updateBean.setId(databaseUser.getId());
        updateBean.setPassword(passwordEncoder.encode(newPassword));
        userService.updateById(updateBean);
    }

    @Transactional
    @Override
    public void loginSuccess(Long userId, String username, String ip, Long timestamp) {
        User user = new User();
        user.setLastLoginIp(ip);
        user.setLastGmtLoginTime(TimeUtil.formatTime(timestamp));
        user.setId(userId);
        userService.updateById(user);
        loginLogService.addSuccess(username, ip, timestamp);
    }

    @Override
    public void updateInfo(String currentUsername, CurrentUserInfoUpdatedParam param) throws Exception {
        if (StrUtil.isEmpty(currentUsername)) {
            throw new WmsException(UserErrorDescEnum.NO_AUTHED_USER_FOUND);
        }
        Wrapper<User> wrapper = Wrappers.<User>lambdaQuery()
            .eq(User::getUsername, currentUsername);
        User user = new User();
        BeanUtils.copyProperties(param, user);
        userService.update(user, wrapper);
    }

}
