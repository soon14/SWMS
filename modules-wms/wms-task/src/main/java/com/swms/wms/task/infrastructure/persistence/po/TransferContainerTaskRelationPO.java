package com.swms.wms.task.infrastructure.persistence.po;

import com.swms.utils.base.CreateUserPO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "w_transfer_container_task_relation",
    indexes = {
        @Index(unique = true, name = "idx_operation_task_id", columnList = "operationTaskId"),
        @Index(name = "idx_transfer_container_id", columnList = "transferContainerId")
    }
)
public class TransferContainerTaskRelationPO extends CreateUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "bigint comment '周转箱id'")
    private Long transferContainerId;

    @Column(nullable = false, columnDefinition = "bigint comment '任务id'")
    private Long operationTaskId;
}
