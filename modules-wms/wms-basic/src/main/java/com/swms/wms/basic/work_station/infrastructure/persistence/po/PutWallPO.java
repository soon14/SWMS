package com.swms.wms.basic.work_station.infrastructure.persistence.po;

import com.swms.common.utils.base.UpdateUserPO;
import com.swms.wms.api.basic.dto.PutWallDTO;
import com.swms.wms.basic.work_station.infrastructure.persistence.converter.ListPutWallSlotConverter;
import com.swms.wms.basic.work_station.infrastructure.persistence.converter.ListWorkLocationConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "w_put_wall",
    indexes = {
        @Index(unique = true, name = "uk_put_wall_code_station", columnList = "putWallCode,workStationId"),
        @Index(name = "idx_work_station_id", columnList = "workStationId")
    }
)
@DynamicUpdate
public class PutWallPO extends UpdateUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.common.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "bigint(11) comment '工作站ID'")
    private Long workStationId;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '播种墙编码'")
    private String putWallCode;

    @Column(nullable = false, columnDefinition = "varchar(128) comment '播种墙名称'")
    private String putWallName;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '容器规格'")
    private String containerSpecCode;

    private boolean deleted;

    private boolean enable;

    @Column(columnDefinition = "json comment '播种墙格口'")
    @Convert(converter = ListPutWallSlotConverter.class)
    private List<PutWallDTO.PutWallSlot> putWallSlots;

    @Version
    private Long version;
}
