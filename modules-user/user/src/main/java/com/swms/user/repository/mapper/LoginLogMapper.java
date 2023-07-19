package com.swms.user.repository.mapper;

import com.swms.user.repository.entity.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * 登录日志 Mapper 接口
 * </p>
 *
 * @author sws
 * @since 2021-01-29
 */
public interface LoginLogMapper extends JpaRepository<LoginLog, Long> {

}
