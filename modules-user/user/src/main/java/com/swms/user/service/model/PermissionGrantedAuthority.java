package com.swms.user.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * 权限授权模型
 *
 * @author sws
 * @version 1.0
 * @since 2020-08-07
 */
@Data
@AllArgsConstructor
public class PermissionGrantedAuthority implements GrantedAuthority {

    private final String permission;

    @Override
    public String getAuthority() {
        return permission;
    }

}
