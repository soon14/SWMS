package com.swms.utils.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public class BaseUserPO extends BaseDatePO {

    @CreatedBy
    @Column(nullable = false, columnDefinition = "varchar(60) default '' comment 'Create user'", updatable = false)
    private String createUser;
    @LastModifiedBy
    @Column(nullable = false, columnDefinition = "varchar(60) default '' comment 'Update user'")
    private String updateUser;
}
