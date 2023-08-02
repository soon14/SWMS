package com.swms.utils.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.LastModifiedDate;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public class AuditUserDTO extends UpdateUserDTO {

    @Column(nullable = false, columnDefinition = "varchar(60) default '' comment 'audit user'")
    private String auditUser;

    @Column(nullable = false, columnDefinition = "bigint default 0 comment 'audit time'")
    @LastModifiedDate
    private Long auditTime;
}
