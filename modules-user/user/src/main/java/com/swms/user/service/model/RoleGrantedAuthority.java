package com.swms.user.service.model;

import com.swms.common.utils.user.AuthConstants;
import com.swms.user.repository.entity.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * 角色授权模型
 *
 * @author sws
 * @version 1.0
 * @since 2020-08-07
 */
@Data
public class RoleGrantedAuthority implements GrantedAuthority {

    private final String authority;

    public RoleGrantedAuthority(Role role) {
        this.authority = AuthConstants.ROLE_GRANTED_AUTHORITY_PREFIX + role.getCode();
    }

    @Override
    public String getAuthority() {
        return authority;
    }


}
