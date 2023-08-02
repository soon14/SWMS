package com.swms.inbound.infrastructure.persistence.po;

import com.swms.common.utils.base.UpdateUserPO;
import com.swms.utils.jpa.converter.MapConverter;
import com.swms.wms.api.inbound.constants.InboundPlanOrderStatusEnum;
import com.swms.wms.api.inbound.constants.StorageTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    name = "w_inbound_plan_order",
    indexes = {
        @Index(unique = true, name = "idx_container_code_warehouse_code", columnList = "containerCode,warehouseCode")
    }
)
@DynamicUpdate
public class InboundPlanOrderPO extends UpdateUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.common.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '订单编号'")
    private String orderNo;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '客户订单编号'")
    private String customerOrderNo;

    @Column(nullable = false, columnDefinition = "varchar(64) comment 'LPN'")
    private String lpnCode = "";

    @Column(nullable = false, columnDefinition = "varchar(64) comment '仓库'")
    private String warehouseCode;
    @Column(nullable = false, columnDefinition = "varchar(64) comment '货主'")
    private String ownerCode;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '订单类型'")
    private String inboundOrderType;

//    private boolean allowMultipleArrivals;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) comment '存储类型'")
    private StorageTypeEnum storageType;
    private boolean abnormal;

    @Column(nullable = false, columnDefinition = "varchar(128) comment '发货方'")
    private String sender;
    @Column(nullable = false, columnDefinition = "varchar(128) comment '承运商'")
    private String carrier;
    @Column(nullable = false, columnDefinition = "varchar(64) comment '承运方式'")
    private String shippingMethod;
    @Column(nullable = false, columnDefinition = "varchar(128) comment '承运单号'")
    private String trackingNumber;
    @Column(nullable = false, columnDefinition = "bigint comment '预计到达时间'")
    private Long estimatedArrivalDate = 0L;

    @Column(nullable = false, columnDefinition = "varchar(255) comment '备注'")
    private String remark = "";

    @Column(nullable = false, columnDefinition = "int(11) comment 'SKU种类'")
    private Integer skuKindNum;
    @Column(nullable = false, columnDefinition = "int(11) comment '总数量'")
    private Integer totalQty;
    @Column(nullable = false, columnDefinition = "int(11) comment '总箱数'")
    private Integer totalBox;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) comment '状态'")
    private InboundPlanOrderStatusEnum inboundPlanOrderStatus = InboundPlanOrderStatusEnum.NEW;

    @Column(columnDefinition = "json comment '扩展字段'")
    @Convert(converter = MapConverter.class)
    private Map<String, Object> extendFields;
}
