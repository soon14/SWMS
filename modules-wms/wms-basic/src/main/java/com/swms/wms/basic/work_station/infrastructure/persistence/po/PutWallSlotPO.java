package com.swms.wms.basic.work_station.infrastructure.persistence.po;

import com.swms.utils.base.BaseUserPO;
import com.swms.utils.jpa.converter.ListLongConverter;
import com.swms.wms.api.basic.constants.PutWallSlotStatusEnum;
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
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "w_put_wall_slot",
    indexes = {
        @Index(unique = true, name = "idx_put_wall_slot_code_station", columnList = "putWallSlotCode,workStationId"),
        @Index(name = "idx_work_station_id", columnList = "workStationId")
    }
)
@DynamicUpdate
public class PutWallSlotPO extends BaseUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.utils.id.IdGenerator")
    private Long id;

    private String face;

    @Column(nullable = false, columnDefinition = "bigint(11) comment '工作站ID'")
    private Long workStationId;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '播种墙编码'")
    private String putWallCode;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '播种墙格口编码'")
    private String putWallSlotCode;

    @Column(columnDefinition = "json comment '播种墙格口订单'")
    @Convert(converter = ListLongConverter.class)
    private List<Long> orderIds;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) comment '状态'")
    private PutWallSlotStatusEnum putWallSlotStatus = PutWallSlotStatusEnum.IDLE;

    @Column(columnDefinition = "varchar(64) comment '周转容器编码'")
    private String transferContainerCode;

    private boolean enable;

    @Version
    private Long version;
}
