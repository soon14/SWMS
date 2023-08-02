package com.swms.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.swms.common.utils.exception.code_enum.UserErrorDescEnum;
import com.swms.user.repository.entity.User;
import com.swms.user.repository.mapper.UserMapper;
import com.swms.user.rest.param.user.CurrentUserInfoUpdatedParam;
import com.swms.user.service.CurrentUserService;
import com.swms.user.service.LoginLogService;
import com.swms.user.utils.TimeUtil;
import com.swms.common.utils.exception.WmsException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final LoginLogService loginLogService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void updateCurrentUserPassword(String currentUsername, String oldPassword, String newPassword) {
        if (newPassword.length() < 6) {
            throw new WmsException(UserErrorDescEnum.ERR_CRED_TOO_SHORT);
        }
        if (StrUtil.isEmpty(currentUsername)) {
            throw new WmsException(UserErrorDescEnum.NO_AUTHED_USER_FOUND);
        }
        User user = userMapper.findByUsername(currentUsername);
        if (user == null) {
            throw new WmsException(UserErrorDescEnum.NO_AUTHED_USER_FOUND);
        }
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new WmsException(UserErrorDescEnum.ERROR_WRONG_OLD_CRED);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userMapper.save(user);
    }

    @Transactional
    @Override
    public void loginSuccess(Long userId, String username, String ip, Long timestamp) {
        User user = userMapper.findById(userId).orElseThrow();
        user.setLastLoginIp(ip);
        user.setLastGmtLoginTime(TimeUtil.formatTime(timestamp));
        userMapper.save(user);
        loginLogService.addSuccess(username, ip, timestamp);
    }

    @Override
    public void updateInfo(String currentUsername, CurrentUserInfoUpdatedParam param) {
        if (StrUtil.isEmpty(currentUsername)) {
            throw new WmsException(UserErrorDescEnum.NO_AUTHED_USER_FOUND);
        }
        User user = userMapper.findByUsername(currentUsername);
        BeanUtils.copyProperties(param, user);
        userMapper.save(user);
    }

}
