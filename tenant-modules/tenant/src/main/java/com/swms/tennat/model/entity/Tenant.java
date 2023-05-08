package com.swms.tennat.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    indexes = {
        @Index(unique = true, name = "idx_name", columnList = "name"),
        @Index(unique = true, name = "idx_tenant_id", columnList = "tenantId")
    }
)
public class Tenant {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '租户名称'")
    private String name;

    @Column(nullable = false, columnDefinition = "varchar(128) comment '加密租户名称'")
    private String tenantId;

    @Column(nullable = false, columnDefinition = "varchar(128) comment '邮箱'")
    private String email;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '手机号'")
    private String mobile;

    //database
    @Column(nullable = false, columnDefinition = "varchar(256) comment '数据库url'")
    private String url;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '数据库账号'")
    private String username;

    @Column(nullable = false, columnDefinition = "varchar(128) comment '数据库密码'")
    private String password;

    @Column(nullable = false, columnDefinition = "varchar(128) comment '数据库驱动'")
    private String driverClassName;

    @CreatedBy
    @Column(nullable = false, columnDefinition = "varchar(60) default '' comment 'Create user'")
    private String createUser;
    @LastModifiedBy
    @Column(nullable = false, columnDefinition = "varchar(60) default '' comment 'Update user'")
    private String updateUser;

    @Column(nullable = false, columnDefinition = "bigint default 0 comment 'Creation time'")
    @CreatedDate
    public Long createTime;

    @Column(nullable = false, columnDefinition = "bigint default 0 comment 'Update time'")
    @LastModifiedDate
    public Long updateTime;
}
