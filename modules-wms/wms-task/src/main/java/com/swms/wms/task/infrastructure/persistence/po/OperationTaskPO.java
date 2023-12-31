package com.swms.wms.task.infrastructure.persistence.po;

import com.swms.common.utils.base.UpdateUserPO;
import com.swms.common.utils.jpa.converter.MapConverter;
import com.swms.wms.api.task.constants.OperationTaskStatusEnum;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
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
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "w_operation_task",
    indexes = {
        @Index(name = "idx_source_container_code", columnList = "sourceContainerCode"),
        @Index(name = "idx_work_station_target_location", columnList = "workStationId,targetLocationCode")
    }
)
public class OperationTaskPO extends UpdateUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.common.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(20) comment '任务类型'")
    @Enumerated(EnumType.STRING)
    private OperationTaskTypeEnum taskType;

    @Column(nullable = false, columnDefinition = "bigint default 0 comment 'SKU ID'")
    private Long skuId;
    @Column(nullable = false, columnDefinition = "bigint default 0 comment '批次库存ID'")
    private Long skuBatchStockId;
    @Column(nullable = false, columnDefinition = "bigint default 0 comment '容器库存ID'")
    private Long containerStockId;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '原容器编码'")
    private String sourceContainerCode;
    @Column(nullable = false, columnDefinition = "varchar(64) comment '原容器格口编码'")
    private String sourceContainerSlot;

    @Column(nullable = false, columnDefinition = "varchar(64) default '' comment '目标容器编码'")
    private String boxNo = "";

    @Column(nullable = false, columnDefinition = "bigint comment '工作站ID'")
    private Long workStationId = 0L;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '仓库编号'")
    private String warehouseCode;

    @Column(nullable = false, columnDefinition = "int(11) comment '需求数量'")
    private Integer requiredQty = 0;
    @Column(nullable = false, columnDefinition = "int(11) comment '操作数量'")
    private Integer operatedQty = 0;
    @Column(nullable = false, columnDefinition = "int(11) comment '异常数量'")
    private Integer abnormalQty = 0;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '目标库位'")
    private String targetLocationCode = "";
    @Column(nullable = false, columnDefinition = "varchar(64) comment '目标容器编码'")
    private String targetContainerCode = "";
    @Column(nullable = false, columnDefinition = "varchar(64) comment '目标容器格口编码'")
    private String targetContainerSlotCode = "";

    @Column(nullable = false, columnDefinition = "bigint comment '单据ID：可能是拣货单/盘点单/理货单'")
    private Long orderId;
    @Column(nullable = false, columnDefinition = "bigint comment '单据明细ID'")
    private Long detailId;

    /**
     * one picking order can be assigned to multiple station slot
     * <p>
     * Key is the station id
     * Value is the put wall slot code
     */
    @Column(columnDefinition = "json comment '分配的工作站格口'")
    @Convert(converter = MapConverter.class)
    private Map<Long, String> assignedStationSlot;

    @Column(nullable = false, columnDefinition = "varchar(20) comment '任务状态'")
    @Enumerated(EnumType.STRING)
    private OperationTaskStatusEnum taskStatus = OperationTaskStatusEnum.CREATED;
}
