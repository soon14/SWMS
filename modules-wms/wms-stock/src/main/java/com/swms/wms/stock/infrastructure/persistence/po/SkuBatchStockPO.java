package com.swms.wms.stock.infrastructure.persistence.po;

import com.swms.utils.base.BaseUserPO;
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
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "w_sku_batch_stock",
    indexes = {
        @Index(unique = true, name = "idx_sku_batch_attribute_warehouse_area", columnList = "skuBatchAttributeId,warehouseAreaId")
    }
)
@DynamicUpdate
public class SkuBatchStockPO extends BaseUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "bigint default 0 comment 'skuId'")
    private Long skuId;

    @Column(nullable = false, columnDefinition = "bigint default 0 comment '批次ID'")
    private Long skuBatchAttributeId;

    @Column(nullable = false, columnDefinition = "bigint default 0 comment '库区ID'")
    private Long warehouseAreaId;

    @Column(nullable = false, columnDefinition = "int(11) default 0 comment '总数量'")
    private Integer totalQty = 0;
    @Column(nullable = false, columnDefinition = "int(11) default 0 comment '可用数量'")
    private Integer availableQty = 0;
    // outbound locked qty
    @Column(nullable = false, columnDefinition = "int(11) default 0 comment '出库锁定数量'")
    private Integer outboundLockedQty = 0;
    // other operation locked qty in the warehouse
    @Column(nullable = false, columnDefinition = "int(11) default 0 comment '库内锁定数量'")
    private Integer noOutboundLockedQty = 0;

    @Version
    private Long version;
}
