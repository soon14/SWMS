package com.swms.user.repository.mapper;

import com.swms.user.repository.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author sws
 * @since 2020-12-31
 */
public interface RoleMapper extends JpaRepository<Role, Long> {

    List<Role> findByCodeIn(Collection<String> codes);

    Role findByCode(String code);
}
