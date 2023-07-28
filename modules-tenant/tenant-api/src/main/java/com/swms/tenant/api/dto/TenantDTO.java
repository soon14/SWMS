package com.swms.tenant.api.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class TenantDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String name;
    private String mobile;
    private String email;

    private String tenantId;

    private String url;
    private String username;
    private String password;
    private String driverClassName;

}
