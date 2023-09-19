package com.swms.outbound.infrastructure.persistence.po;

import com.swms.common.utils.base.UpdateUserPO;
import com.swms.common.utils.jpa.converter.MapConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.TreeMap;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "w_picking_order_detail",
    indexes = {
        @Index(name = "idx_picking_order_id", columnList = "pickingOrderId")
    }
)
@DynamicUpdate
public class PickingOrderDetailPO extends UpdateUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.common.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "bigint comment '拣货单ID'")
    private Long pickingOrderId;

    @Column(nullable = false, columnDefinition = "bigint comment '出库计划单ID'")
    private Long outboundOrderPlanId;

    @Column(nullable = false, columnDefinition = "bigint comment '出库计划单明细ID'")
    private Long outboundOrderPlanDetailId;

    @Column(nullable = false, columnDefinition = "bigint comment 'skuID'")
    private Long skuId;

    @Column(columnDefinition = "json comment '批次属性'")
    @Convert(converter = MapConverter.class)
    private TreeMap<String, Object> batchAttributes;

    @Column(nullable = false, columnDefinition = "bigint comment 'sku batch stock ID'")
    private Long skuBatchStockId;

    @Column(nullable = false, columnDefinition = "int(11) comment '计划数量'")
    private Integer qtyRequired;
    @Column(nullable = false, columnDefinition = "int(11) comment '实际拣货数量'")
    private Integer qtyActual = 0;
}
