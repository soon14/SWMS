package com.swms.user.repository.mapper;

import com.swms.user.repository.entity.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 角色菜单权限表 Mapper 接口
 * </p>
 *
 * @author sws
 * @since 2020-12-31
 */
public interface RoleMenuMapper extends JpaRepository<RoleMenu, Long> {

    List<RoleMenu> findByRoleIdIn(Collection<Long> roleIds);

    void deleteByMenuIdIn(Collection<Long> menuIds);

    void deleteByRoleId(Long roleId);
}
