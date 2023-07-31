package com.swms.wms.stock.infrastructure.persistence.po;

import com.swms.utils.base.BaseUserPO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "w_container_stock_transaction",
    indexes = {
        @Index(name = "idx_container_code", columnList = "sourceContainerCode"),
        @Index(name = "idx_order_no", columnList = "orderNo")
    }
)
@DynamicUpdate
public class ContainerStockTransactionPO extends BaseUserPO {
    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "bigint default 0 comment '批次id'")
    private Long containerStockId = 0L;
    @Column(nullable = false, columnDefinition = "bigint default 0 comment '批次id'")
    private Long skuBatchAttributeId;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '原容器编码'")
    private String sourceContainerCode;
    @Column(nullable = false, columnDefinition = "varchar(64) comment '原容器格口编码'")
    private String sourceContainerSlotCode = "";

    @Column(nullable = false, columnDefinition = "varchar(64) comment '目标容器编码'")
    private String targetContainerCode;
    @Column(nullable = false, columnDefinition = "varchar(64) comment '目标容器格口编码'")
    private String targetContainerSlotCode;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '订单号'")
    private String orderNo;

    @Column(nullable = false, columnDefinition = "bigint default 0 comment '任务id'")
    private Long taskId = 0L;

    @Column(nullable = false, columnDefinition = "int(11) default 0 comment '数量'")
    private Integer transferQty;

    @Version
    private Long version;
}
