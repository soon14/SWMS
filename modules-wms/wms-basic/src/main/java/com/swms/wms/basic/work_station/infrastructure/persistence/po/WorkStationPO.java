package com.swms.wms.basic.work_station.infrastructure.persistence.po;

import com.swms.utils.base.BaseUserPO;
import com.swms.wms.api.basic.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.basic.constants.WorkStationStatusEnum;
import com.swms.wms.api.basic.dto.WorkStationDTO;
import com.swms.wms.basic.work_station.infrastructure.persistence.converter.ListWorkLocationConverter;
import com.swms.wms.basic.work_station.infrastructure.persistence.converter.ListWorkStationOperationTypeConverter;
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
import jakarta.persistence.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    indexes = {
        @Index(unique = true, name = "idx_station_code_warehouse", columnList = "stationCode,warehouseCode")
    }
)
public class WorkStationPO extends BaseUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '工作站编码'")
    private String stationCode;

    @Column(nullable = false, columnDefinition = "varchar(128) comment '工作站编码'")
    private String stationName;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20) comment '状态'")
    private WorkStationStatusEnum workStationStatus = WorkStationStatusEnum.OFFLINE;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '仓库编码'")
    private String warehouseCode;
    @Column(nullable = false, columnDefinition = "varchar(64) comment '库区编码'")
    private String warehouseAreaCode;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20) comment '操作类型'")
    private WorkStationOperationTypeEnum operationType;

    @Column(columnDefinition = "json comment '工作站允许的操作'")
    @Convert(converter = ListWorkStationOperationTypeConverter.class)
    private List<WorkStationOperationTypeEnum> allowOperationTypes;

    @Column(nullable = false, columnDefinition = "bigint comment '工作站任务规则'")
    private Long workStationRuleId = 0L;

    @Column(columnDefinition = "json comment '工作站工作位'")
    @Convert(converter = ListWorkLocationConverter.class)
    private List<WorkStationDTO.WorkLocation<WorkStationDTO.WorkLocationSlot>> workLocations;

    private boolean enable;

    private boolean deleted;

    @Version
    private Long version;
}
