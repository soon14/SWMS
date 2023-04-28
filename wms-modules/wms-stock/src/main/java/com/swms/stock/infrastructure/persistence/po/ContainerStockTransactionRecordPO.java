package com.swms.stock.infrastructure.persistence.po;

import com.swms.utils.base.BaseUserPO;
import com.swms.utils.id.IdGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    indexes = {
        @Index(unique = true, name = "idx_container_code", columnList = "containerCode")
    }
)
public class ContainerStockTransactionRecordPO extends BaseUserPO {
    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", type = IdGenerator.class)
    private Long id;

    @Column(nullable = false, columnDefinition = "bigint default 0 comment '批次id'")
    private Long containerStockId;
    @Column(nullable = false, columnDefinition = "bigint default 0 comment '批次id'")
    private Long batchAttributeId;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '原容器编码'")
    private String sourceContainerCode;
    @Column(nullable = false, columnDefinition = "varchar(64) comment '原容器格口编码'")
    private String sourceContainerSlotCode;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '目标容器编码'")
    private String targetContainerCode;
    @Column(nullable = false, columnDefinition = "varchar(64) comment '目标容器格口编码'")
    private String targetContainerSlotCode;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '订单号'")
    private String orderNo;

    @Column(nullable = false, columnDefinition = "bigint default 0 comment '任务id'")
    private Long taskId;

    @Column(nullable = false, columnDefinition = "int(11) default 0 comment '数量'")
    private Integer transferQty;

    private boolean processed;

    @Version
    private Long version;
}
