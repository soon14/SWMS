package com.swms.common.utils.base;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.LastModifiedDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuditUserDTO extends UpdateUserDTO {

    @Column(nullable = false, columnDefinition = "varchar(60) default '' comment 'audit user'")
    private String auditUser;

    @Column(nullable = false, columnDefinition = "bigint default 0 comment 'audit time'")
    @LastModifiedDate
    private Long auditTime;
}
