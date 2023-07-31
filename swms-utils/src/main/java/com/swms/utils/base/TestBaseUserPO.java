package com.swms.utils.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@Data
@MappedSuperclass
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TestBaseUserPO {

    @CreatedBy
    @Column(nullable = false, columnDefinition = "varchar(60) default '' comment 'Create user'")
    private String createUser;
    @LastModifiedBy
    @Column(nullable = false, columnDefinition = "varchar(60) default '' comment 'Update user'")
    private String updateUser;
}
