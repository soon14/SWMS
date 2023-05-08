package com.swms.wms.stock.infrastructure.persistence.po;

import com.swms.utils.base.BaseUserPO;
import com.swms.utils.id.IdGenerator;
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
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    indexes = {
        @Index(unique = true, name = "idx_container_slot_sku_batch_attribute", columnList = "containerCode,containerSlotCode,skuBatchAttributeId"),
    }
)
public class ContainerStockPO extends BaseUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "bigint default 0 comment '批次id'")
    private Long skuBatchAttributeId;

    /**
     * container is not must be a physical container. e.g.
     * when sku received to a place but not a physical container,
     * then the container code will be received order no ,
     * and when sku put away on the rack, then the container code is the location code;
     */
    @Column(nullable = false, columnDefinition = "varchar(64) comment '容器编码'")
    private String containerCode;
    @Column(nullable = false, columnDefinition = "varchar(64) comment '容器格口编码'")
    private String containerSlotCode;

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

    /**
     * it means the container is or not a physical container
     */
    private boolean boxStock;
    @Column(nullable = false, columnDefinition = "varchar(64) comment '箱号'")
    private String boxNo = "";

    @Column(nullable = false, columnDefinition = "varchar(64) comment '库区编码'")
    private String warehouseAreaCode;

    @Version
    private Long version;
}
