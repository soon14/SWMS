package com.swms.user.repository.mapper;

import com.swms.user.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author sws
 * @since 2020-12-31
 */
public interface UserMapper extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
