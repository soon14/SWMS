package com.swms.inbound.infrastructure.persistence.po;

import com.swms.common.utils.base.CreateUserPO;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.SortedMap;
import java.util.TreeMap;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "w_receive_order_detail",
    indexes = {
        @Index(name = "idx_receive_order_id", columnList = "receiveOrderId"),
        @Index(name = "idx_inbound_plan_order_detail_id", columnList = "inboundPlanOrderDetailId")
    }
)
@DynamicUpdate
public class ReceiveOrderDetailPO extends CreateUserPO {

    private Long id;

    private Long inboundPlanOrderDetailId;
    private Long receiveOrderId;

    private String containerCode;
    private String containerSpecCode;
    private String containerSlotCode;

    private String boxNo;

    private Integer qtyReceived;

    @NotEmpty
    @Size(max = 64)
    private String skuCode;
    @Size(max = 128)
    private String skuName;
    private String style;
    private String color;
    private String size;
    private String brand;

    private String receiver;

    private SortedMap<String, Object> batchAttributes = new TreeMap<>();
}
