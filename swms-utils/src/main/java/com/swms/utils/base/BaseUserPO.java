package com.swms.utils.base;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseUserPO extends BaseDatePO {
    @Column(nullable = false, columnDefinition = "bigint default 0 comment 'Creation time'")
    @CreatedDate
    public Long createTime;

    @Column(nullable = false, columnDefinition = "bigint default 0 comment 'Update time'")
    @LastModifiedDate
    public Long updateTime;

    @CreatedBy
    @Column(nullable = false, columnDefinition = "varchar(60) default '' comment 'Create user'")
    private String createUser;
    @LastModifiedBy
    @Column(nullable = false, columnDefinition = "varchar(60) default '' comment 'Update user'")
    private String updateUser;

}
