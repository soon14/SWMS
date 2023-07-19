package com.swms.user.service.impl;

import com.swms.user.repository.entity.UserRole;
import com.swms.user.repository.mapper.UserRoleMapper;
import com.swms.user.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * <p>
 * 系统用户角色关联表 服务实现类
 * </p>
 *
 * @author sws
 * @since 2020-12-25
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public void add(Long userId, Set<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return;
        }
        List<UserRole> userRoles = roleIds.stream()
            .filter(Objects::nonNull)
            .map(roleId -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                return userRole;
            }).toList();
        if (userRoles.isEmpty()) {
            return;
        }
        userRoleMapper.saveAll(userRoles);
    }

    @Override
    public void removeByRoleId(Long roleId) {
        if (null == roleId) {
            return;
        }
        userRoleMapper.deleteByRoleId(roleId);
    }

    @Override
    public void removeByUserId(Long userId) {
        if (null == userId) {
            return;
        }
        userRoleMapper.deleteByUserId(userId);
    }

    @Override
    public List<UserRole> getByUserId(Long userId) {
        if (null == userId) {
            return Collections.emptyList();
        }
        return userRoleMapper.findByUserId(userId);
    }

    @Override
    public List<UserRole> getByRoleId(Long roleId) {
        if (roleId == null) {
            return Collections.emptyList();
        }

        return userRoleMapper.findByRoleId(roleId);
    }
}
