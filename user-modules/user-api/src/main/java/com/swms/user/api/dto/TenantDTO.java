package com.swms.user.api.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TenantDTO {

    private String id;

    @NotEmpty
    private String name;

    // md5加密
    private String tenantId;
    private String url;
    private String username;
    private String password;
    private String driverClassName;

}
