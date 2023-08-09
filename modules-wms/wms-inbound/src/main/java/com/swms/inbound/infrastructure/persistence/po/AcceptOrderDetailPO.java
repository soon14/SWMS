package com.swms.inbound.infrastructure.persistence.po;

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
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Map;
import java.util.TreeMap;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "w_accept_order_detail",
    indexes = {
        @Index(name = "idx_inbound_plan_order_detail_id", columnList = "inboundPlanOrderDetailId"),
        @Index(name = "idx_accept_order_id", columnList = "acceptOrderId")
    }
)
@DynamicUpdate
public class AcceptOrderDetailPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.common.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "bigint comment '验收单ID'")
    private Long acceptOrderId;

    @Column(nullable = false, columnDefinition = "bigint comment '入库通知单明细ID'")
    private Long inboundPlanOrderDetailId;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '箱号'")
    private String boxNo = "";

    // if sku is loose , then they will be packed into a box
    @Column(nullable = false, columnDefinition = "varchar(64) comment '箱号'")
    private String packBoxNo = "";

    @Column(nullable = false, columnDefinition = "varchar(64) comment '目标容器编码'")
    private String targetContainerCode;
    @Column(nullable = false, columnDefinition = "varchar(64) comment '目标容器规格编码'")
    private String targetContainerSpecCode;
    @Column(nullable = false, columnDefinition = "varchar(64) comment '目标容器格口编码'")
    private String targetContainerSlotCode;

    @Column(nullable = false, columnDefinition = "int(11) comment '验收数量'")
    private Integer qtyAccepted = 0;

    @Column(nullable = false, columnDefinition = "varchar(64) comment 'sku编码'")
    private String skuCode;
    @Column(nullable = false, columnDefinition = "varchar(128) comment 'sku名称'")
    private String skuName = "";
    @Column(columnDefinition = "varchar(64) comment '款式'")
    private String style;
    @Column(columnDefinition = "varchar(64) comment '颜色'")
    private String color;
    @Column(columnDefinition = "varchar(64) comment '尺码'")
    private String size;
    @Column(columnDefinition = "varchar(64) comment '品牌'")
    private String brand;

    @Column(columnDefinition = "json comment '批次属性'")
    @Convert(converter = MapConverter.class)
    private Map<String, Object> batchAttributes = new TreeMap<>();

    @Column(nullable = false, columnDefinition = "bigint comment '工作站ID'")
    private Long stationId;
}
