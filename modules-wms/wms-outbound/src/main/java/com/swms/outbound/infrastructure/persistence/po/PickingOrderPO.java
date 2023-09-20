package com.swms.outbound.infrastructure.persistence.po;

import com.swms.common.utils.base.UpdateUserPO;
import com.swms.common.utils.jpa.converter.MapConverter;
import com.swms.wms.api.outbound.constants.PickingOrderStatusEnum;
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
    name = "w_picking_order",
    indexes = {
        @Index(unique = true, name = "uk_picking_order_no", columnList = "pickingOrderNo")
    }
)
@DynamicUpdate
public class PickingOrderPO extends UpdateUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.common.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '仓库'")
    private String warehouseCode;

    @Column(nullable = false, columnDefinition = "varchar(32) comment '波次号'")
    private String waveNo;

    @Column(nullable = false, columnDefinition = "varchar(32) comment '拣选单号'")
    private String pickingOrderNo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) comment '状态'")
    private PickingOrderStatusEnum pickingOrderStatus = PickingOrderStatusEnum.NEW;

    /**
     * one picking order can be assigned to multiple station slot
     * <p>
     * Key is the station id
     * Value is the put wall slot code
     */
    @Column(columnDefinition = "json comment '分配的工作站格口'")
    @Convert(converter = MapConverter.class)
    private Map<Long, String> assignedStationSlot;
}
