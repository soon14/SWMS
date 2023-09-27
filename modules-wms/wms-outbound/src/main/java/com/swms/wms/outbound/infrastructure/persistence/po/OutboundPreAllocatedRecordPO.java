package com.swms.wms.outbound.infrastructure.persistence.po;

import com.swms.common.utils.base.CreateUserPO;
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

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "w_outbound_pre_allocated_record",
    indexes = {
        @Index(name = "idx_outbound_plan_order_id", columnList = "outboundPlanOrderId")
    }
)
@DynamicUpdate
public class OutboundPreAllocatedRecordPO extends CreateUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.common.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "bigint comment '出库计划单ID'")
    private Long outboundPlanOrderId;

    @Column(nullable = false, columnDefinition = "bigint comment '出库计划单明细ID'")
    private Long outboundPlanOrderDetailId;

    @Column(nullable = false, columnDefinition = "bigint comment 'skuID'")
    private Long skuId;

    @Column(nullable = false, columnDefinition = "bigint comment 'sku batch stock ID'")
    private Long skuBatchStockId;

    @Column(columnDefinition = "json comment '批次属性'")
    @Convert(converter = MapConverter.class)
    private Map<String, Object> batchAttributes;

    @Column(nullable = false, columnDefinition = "int(11) comment '预占用数量'")
    private Integer qtyPreAllocated;
}
