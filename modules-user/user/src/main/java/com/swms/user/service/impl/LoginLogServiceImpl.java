package com.swms.user.service.impl;

import com.swms.user.repository.entity.LoginLog;
import com.swms.user.repository.mapper.LoginLogMapper;
import com.swms.user.rest.common.enums.YesOrNo;
import com.swms.user.service.LoginLogService;
import com.swms.user.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录日志 服务实现类
 * </p>
 *
 * @author sws
 * @since 2021-01-29
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    /**
     * 自行实现ip转换物理地址
     *
     * @param ip
     *
     * @return
     */
    private String getAddressByIp(String ip) {
        return "无";
    }

    @Override
    public void addSuccess(String username, String ip, Long loginTimestamp) {
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        loginLog.setLoginIp(ip);
        loginLog.setLoginResult(Integer.valueOf(YesOrNo.YES.getCode()));
        loginLog.setLoginAddress(getAddressByIp(ip));
        setTime(loginLog, loginTimestamp);
        loginLogMapper.save(loginLog);
    }

    @Override
    public void addFailure(String username, String ip, Long loginTimestamp, String failureMsg) {
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        loginLog.setLoginIp(ip);
        loginLog.setLoginResult(Integer.valueOf(YesOrNo.NO.getCode()));
        loginLog.setLoginFailureMsg(failureMsg);
        loginLog.setLoginAddress(getAddressByIp(ip));
        setTime(loginLog, loginTimestamp);
        loginLogMapper.save(loginLog);
    }


    private void setTime(LoginLog loginLog, Long loginTimestamp) {
        loginLog.setGmtLoginTimestamp(loginTimestamp);
        loginLog.setGmtLoginTime(TimeUtil.formatTime(loginTimestamp));
    }
}
