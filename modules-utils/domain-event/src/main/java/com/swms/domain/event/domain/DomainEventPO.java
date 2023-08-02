package com.swms.domain.event.domain;

import com.swms.common.utils.base.CreateUserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "d_domain_event"
)
public class DomainEventPO extends CreateUserDTO {

    @Id
    private Long id;

    @Column(nullable = false, columnDefinition = "text comment '事件'")
    private String event;

    //0 - success , 1 - failure
    private int status = 0;
}
