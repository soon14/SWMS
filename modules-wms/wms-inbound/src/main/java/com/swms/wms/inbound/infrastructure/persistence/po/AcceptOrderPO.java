package com.swms.wms.inbound.infrastructure.persistence.po;

import com.swms.common.utils.base.AuditUserPO;
import com.swms.wms.api.inbound.constants.AcceptMethodEnum;
import com.swms.wms.api.inbound.constants.AcceptOrderStatusEnum;
import com.swms.wms.api.inbound.constants.AcceptTypeEnum;
import jakarta.persistence.Column;
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

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "w_accept_order",
    indexes = {
        @Index(name = "idx_inbound_plan_order_id", columnList = "inboundPlanOrderId"),
        @Index(unique = true, name = "idx_order_no", columnList = "orderNo")
    }
)
@DynamicUpdate
public class AcceptOrderPO extends AuditUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.common.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '订单编号'")
    private String orderNo;

    @Column(nullable = false, columnDefinition = "bigint comment '入库通知单ID'")
    private Long inboundPlanOrderId;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '仓库'")
    private String warehouseCode;
    @Column(nullable = false, columnDefinition = "varchar(64) comment '货主'")
    private String ownerCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) comment '收货方式'")
    private AcceptMethodEnum acceptMethod;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) comment '收货类型'")
    private AcceptTypeEnum acceptType;

    private boolean putAway;

    @Column(nullable = false, columnDefinition = "int(11) comment '验收总数量'")
    private Integer totalQty = 0;
    @Column(nullable = false, columnDefinition = "int(11) comment '验收总箱数'")
    private Integer totalBox = 0;

    @Column(nullable = false, columnDefinition = "varchar(500) comment '备注'")
    private String remark = "";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) comment '验收单状态'")
    private AcceptOrderStatusEnum acceptOrderStatus = AcceptOrderStatusEnum.NEW;

    @Version
    private Long version;
}
