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

import java.util.Map;
import java.util.TreeMap;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "w_outbound_plan_order_deatil",
    indexes = {
        @Index(name = "idx_customer_order_no", columnList = "customerOrderNo"),
        @Index(unique = true, name = "idx_order_no", columnList = "orderNo")
    }
)
@DynamicUpdate
public class OutboundPlanOrderDetailPO extends UpdateUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.common.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "bigint comment '出库计划单ID'")
    private Long outboundPlanOrderId;

    @Column(nullable = false, columnDefinition = "varchar(64) comment 'sku编码'")
    private String skuCode;
    @Column(nullable = false, columnDefinition = "varchar(128) comment 'sku名称'")
    private String skuName = "";

    @Column(columnDefinition = "json comment '批次属性'")
    @Convert(converter = MapConverter.class)
    private Map<String, Object> batchAttributes = new TreeMap<>();

    @Column(nullable = false, columnDefinition = "int(11) comment '计划数量'")
    private Integer qtyRequired;
    @Column(nullable = false, columnDefinition = "int(11) comment '实际拣货数量'")
    private Integer qtyActual = 0;

    @Column(columnDefinition = "json comment '扩展字段'")
    @Convert(converter = MapConverter.class)
    private Map<String, String> reservedFields;
}
