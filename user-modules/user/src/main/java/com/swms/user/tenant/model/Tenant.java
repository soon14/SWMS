package com.swms.user.tenant.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.swms.user.repository.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors
public class Tenant extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    private String name;

    // md5加密
    private String tenantId;

    private String url;
    private String username;
    private String password;
    private String driverClassName;

}
