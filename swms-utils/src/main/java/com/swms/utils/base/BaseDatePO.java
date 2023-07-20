package com.swms.utils.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@MappedSuperclass
public class BaseDatePO {
    @Column(nullable = false, columnDefinition = "bigint default 0 comment 'Creation time'")
    @CreatedDate
    public Long createTime;

    @Column(nullable = false, columnDefinition = "bigint default 0 comment 'Update time'")
    @LastModifiedDate
    public Long updateTime;
}
