package com.swms.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swms.user.repository.entity.UserRole;
import com.swms.user.repository.mapper.UserRoleMapper;
import com.swms.user.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户角色关联表 服务实现类
 * </p>
 *
 * @author sws
 * @since 2020-12-25
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

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
            })
            .collect(Collectors.toList());
        if (userRoles.isEmpty()) {
            return;
        }
        saveBatch(userRoles);
    }

    @Override
    public void removeByRoleId(Long roleId) {
        if (null == roleId) {
            return;
        }
        Wrapper<UserRole> wrapper = Wrappers.<UserRole>lambdaQuery()
            .eq(UserRole::getRoleId, roleId);
        remove(wrapper);
    }

    @Override
    public void removeByUserId(Long userId) {
        if (null == userId) {
            return;
        }
        Wrapper<UserRole> wrapper = Wrappers.<UserRole>lambdaQuery()
            .eq(UserRole::getUserId, userId);
        remove(wrapper);
    }

    @Override
    public List<UserRole> getByUserId(Long userId) {
        if (null == userId) {
            return Collections.emptyList();
        }
        Wrapper<UserRole> wrapper = Wrappers.<UserRole>lambdaQuery()
            .eq(UserRole::getUserId, userId);
        return list(wrapper);
    }

    @Override
    public List<UserRole> getByRoleId(Long roleId) {
        if (roleId == null) {
            return Collections.emptyList();
        }

        Wrapper<UserRole> wrapper = Wrappers.<UserRole>lambdaQuery()
            .eq(UserRole::getRoleId, roleId);
        return list(wrapper);
    }
}
