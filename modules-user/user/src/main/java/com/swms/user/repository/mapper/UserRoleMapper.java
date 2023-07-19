package com.swms.user.repository.mapper;

import com.swms.user.repository.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <p>
 * 系统用户角色关联表 Mapper 接口
 * </p>
 *
 * @author sws
 * @since 2020-12-31
 */
public interface UserRoleMapper extends JpaRepository<UserRole, Long> {

    List<UserRole> findByUserId(Long userId);

    List<UserRole> findByRoleId(Long roleId);

    void deleteByRoleId(Long roleId);

    void deleteByUserId(Long userId);
}
