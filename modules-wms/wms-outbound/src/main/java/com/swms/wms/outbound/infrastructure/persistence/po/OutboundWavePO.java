package com.swms.wms.outbound.infrastructure.persistence.po;

import com.swms.common.utils.base.UpdateUserPO;
import com.swms.common.utils.jpa.converter.ListLongConverter;
import com.swms.wms.api.outbound.constants.OutboundWaveStatusEnum;
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

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "w_outbound_wave",
    indexes = {
        @Index(unique = true, name = "uk_wave_no", columnList = "waveNo")
    }
)
@DynamicUpdate
public class OutboundWavePO extends UpdateUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.common.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '仓库'")
    private String warehouseCode;

    @Column(nullable = false, columnDefinition = "varchar(32) comment '波次号'")
    private String waveNo;

    @Column(columnDefinition = "json comment '出库计划单ID'")
    @Convert(converter = ListLongConverter.class)
    private List<Long> outboundPlanOrderIds;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) comment '状态'")
    private OutboundWaveStatusEnum waveStatus;
}
