package com.swms.inbound.infrastructure.persistence.po;

import com.swms.utils.base.UpdateUserPO;
import com.swms.utils.jpa.converter.MapConverter;
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
import java.util.SortedMap;
import java.util.TreeMap;

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
public class InboundPlanOrderDetailPO extends UpdateUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "bigint comment '入库通知单ID'")
    private Long inboundPlanOrderId;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '容器号'")
    private String containerCode = "";
    @Column(nullable = false, columnDefinition = "varchar(64) comment '容器规格号'")
    private String containerSpecCode = "";
    @Column(nullable = false, columnDefinition = "varchar(64) comment '容器格口号'")
    private String containerSlotCode = "";

    @Column(nullable = false, columnDefinition = "varchar(64) comment '箱号'")
    private String boxNo = "";

    @Column(nullable = false, columnDefinition = "int(11) comment '计划数量'")
    private Integer qtyRestocked = 0;
    @Column(nullable = false, columnDefinition = "int(11) comment '验收数量'")
    private Integer qtyAccepted = 0;
    @Column(nullable = false, columnDefinition = "int(11) comment '未收货数量'")
    private Integer qtyUnreceived = 0;

    @Column(nullable = false, columnDefinition = "int(11) comment '异常数量'")
    private Integer qtyAbnormal = 0;
    @Column(nullable = false, columnDefinition = "varchar(128) comment '异常原因'")
    private String abnormalReason;
    @Column(nullable = false, columnDefinition = "varchar(128) comment '异常原因责任方'")
    private String responsibleParty;

    @Column(nullable = false, columnDefinition = "varchar(64) comment 'sku编码'")
    private String skuCode;
    @Column(nullable = false, columnDefinition = "varchar(128) comment 'sku名称'")
    private String skuName;

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
    private SortedMap<String, Object> batchAttributes = new TreeMap<>();

    @Column(columnDefinition = "json comment '扩展字段'")
    @Convert(converter = MapConverter.class)
    private Map<String, Object> extendFields;
}
