package com.swms.wms.basic.work_station.infrastructure.persistence.po;

import com.swms.utils.base.BaseUserPO;
import com.swms.utils.id.IdGenerator;
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
        @Index(unique = true, name = "idx_put_wall_slot_code", columnList = "putWallSlotCode")
    }
)
public class PutWallSlotPO extends BaseUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", type = IdGenerator.class)
    private Long id;

    private String face;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '工作站编码'")
    private String stationCode;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '播种墙编码'")
    private String putWallCode;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '播种墙格口编码'")
    private String putWallSlotCode;

    @Column(nullable = false, columnDefinition = "int default0 comment '层'")
    private Integer level;
    @Column(nullable = false, columnDefinition = "int default0 comment '层'")
    private Integer bay;
    private boolean enable;

    @Column(columnDefinition = "json comment '播种墙格口订单'")
    @Convert(converter = ListLongConverter.class)
    private List<Long> orderIds;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) comment '状态'")
    private PutWallSlotStatusEnum putWallSlotStatus;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '周转容器编码'")
    private String transferContainerCode;

    @Version
    private Long version;
}
