package com.swms.outbound.infrastructure.persistence.po;

import com.swms.common.utils.base.AuditUserPO;
import com.swms.common.utils.jpa.converter.MapConverter;
import com.swms.wms.api.outbound.constants.OutboundPlanOrderStatusEnum;
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
import jakarta.persistence.Version;
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
    name = "w_outbound_plan_order",
    indexes = {
        @Index(name = "idx_customer_order_no", columnList = "customerOrderNo"),
        @Index(unique = true, name = "idx_order_no", columnList = "orderNo")
    }
)
@DynamicUpdate
public class OutboundPlanOrderPO extends AuditUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.common.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '仓库'")
    private String warehouseCode;
    @Column(nullable = false, columnDefinition = "varchar(64) comment '货主'")
    private String ownerCode;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '波次号'")
    private String waveNo = "";
    @Column(nullable = false, columnDefinition = "varchar(64) comment '客户订单编号'")
    private String customerOrderNo;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '订单类型'")
    private String customerOrderType = "";

    @Column(nullable = false, columnDefinition = "varchar(64) comment '承运商'")
    private String currierCode = "";
    @Column(nullable = false, columnDefinition = "varchar(64) comment '运单号'")
    private String waybillNo = "";
    @Column(nullable = false, columnDefinition = "varchar(64) comment '来源平台'")
    private String origPlatformCode = "";

    @Column(nullable = false, columnDefinition = "bigint comment '截单时间'")
    private Long expiredTime = 0L;
    @Column(nullable = false, columnDefinition = "bigint comment '优先级'")
    private Integer priority = 0;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '订单编号'")
    private String orderNo;

    @Column(nullable = false, columnDefinition = "int(11) comment 'SKU种类'")
    private Integer skuKindNum;
    @Column(nullable = false, columnDefinition = "int(11) comment '总数量'")
    private Integer totalQty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) comment '状态'")
    private OutboundPlanOrderStatusEnum outboundPlanOrderStatus = OutboundPlanOrderStatusEnum.NEW;

    private boolean abnormal;
    @Column(nullable = false, columnDefinition = "varchar(128) comment '订单编号'")
    private String abnormalReason = "";

    @Column(columnDefinition = "json comment '扩展字段'")
    @Convert(converter = MapConverter.class)
    private Map<String, String> reservedFields;

    @Version
    private Long version;
}
