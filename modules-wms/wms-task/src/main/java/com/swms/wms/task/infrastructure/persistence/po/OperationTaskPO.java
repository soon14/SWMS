package com.swms.wms.task.infrastructure.persistence.po;

import com.swms.common.utils.base.UpdateUserPO;
import com.swms.wms.api.task.constants.OperationTaskStatusEnum;
import com.swms.wms.api.task.constants.OperationTaskTypeEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "w_operation_task",
    indexes = {
        @Index(unique = true, name = "idx_task_no", columnList = "taskNo"),
        @Index(name = "idx_source_container_code", columnList = "sourceContainerCode"),
        @Index(name = "idx_work_station_id", columnList = "workStationId")
    }
)
public class OperationTaskPO extends UpdateUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.common.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '任务编号'")
    private String taskNo;

    @Column(nullable = false, columnDefinition = "varchar(20) comment '任务类型'")
    @Enumerated(EnumType.STRING)
    private OperationTaskTypeEnum taskType;

    @Column(nullable = false, columnDefinition = "varchar(64) comment 'SKU编号'")
    private String skuCode;
    @Column(nullable = false, columnDefinition = "bigint default 0 comment '批次库存id'")
    private Long skuBatchStockId;
    @Column(nullable = false, columnDefinition = "bigint default 0 comment '容器库存id'")
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

    @Column(nullable = false, columnDefinition = "varchar(64) comment '目标容器编码'")
    private String targetLocationCode = "";
    @Column(nullable = false, columnDefinition = "varchar(64) comment '目标容器格口编码'")
    private String targetContainerCode = "";
    @Column(nullable = false, columnDefinition = "varchar(64) comment '目标容器格口编码'")
    private String targetContainerSlotCode = "";

    @Column(nullable = false, columnDefinition = "bigint comment '原始订单id'")
    private Long originalOrderId;
    @Column(nullable = false, columnDefinition = "bigint comment '原始订单明细id'")
    private Long originalOrderDetailId;

    @Column(nullable = false, columnDefinition = "varchar(20) comment '任务状态'")
    @Enumerated(EnumType.STRING)
    private OperationTaskStatusEnum taskStatus = OperationTaskStatusEnum.CREATED;
}
