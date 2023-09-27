package com.swms.wms.inbound.infrastructure.persistence.po;

import com.swms.common.utils.base.AuditUserPO;
import com.swms.wms.api.inbound.constants.ReceiveOrderCreateTypeEnum;
import com.swms.wms.api.inbound.constants.ReceiveOrderStatusEnum;
import com.swms.wms.api.inbound.constants.StorageTypeEnum;
import jakarta.persistence.Column;
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

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "w_receive_order",
    indexes = {
        @Index(unique = true, name = "idx_order_no", columnList = "orderNo")
    }
)
@DynamicUpdate
public class ReceiveOrderPO extends AuditUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.common.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '订单编号'")
    private String orderNo;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '仓库'")
    private String warehouseCode;
    @Column(nullable = false, columnDefinition = "varchar(64) comment '货主'")
    private String ownerCode;

    @Column(nullable = false, columnDefinition = "varchar(20) comment '收货单类型'")
    private String receiveOrderType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) comment '存储类型'")
    private StorageTypeEnum storageType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) comment '创建方式'")
    private ReceiveOrderCreateTypeEnum createType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) comment '收货单状态'")
    private ReceiveOrderStatusEnum receiveOrderStatus;

}
