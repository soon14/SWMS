package com.swms.wms.inbound.infrastructure.persistence.po;

import com.swms.common.utils.base.CreateUserPO;
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
    name = "w_receive_order_detail",
    indexes = {
        @Index(name = "idx_receive_order_id", columnList = "receiveOrderId"),
        @Index(name = "idx_inbound_plan_order_detail_id", columnList = "inboundPlanOrderDetailId")
    }
)
@DynamicUpdate
public class ReceiveOrderDetailPO extends CreateUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.common.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "bigint comment '入库通知明细ID'")
    private Long inboundPlanOrderDetailId;
    @Column(nullable = false, columnDefinition = "bigint comment '收货单ID'")
    private Long receiveOrderId;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '箱号'")
    private String boxNo = "";

    @Column(nullable = false, columnDefinition = "int(11) comment '收货数量'")
    private Integer qtyReceived = 0;

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

    @Column(columnDefinition = "varchar(64) comment '收货方'")
    private String receiver;

    @Column(columnDefinition = "json comment '批次属性'")
    @Convert(converter = MapConverter.class)
    private Map<String, Object> batchAttributes = new TreeMap<>();
}
