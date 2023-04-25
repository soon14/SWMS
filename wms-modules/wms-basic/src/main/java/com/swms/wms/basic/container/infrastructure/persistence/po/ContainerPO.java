package com.swms.wms.basic.container.infrastructure.persistence.po;

import com.swms.utils.base.BaseUserPO;
import com.swms.utils.id.IdGenerator;
import com.swms.wms.api.basic.constants.ContainerStatusEnum;
import com.swms.wms.api.basic.dto.ContainerDTO;
import com.swms.wms.basic.container.infrastructure.persistence.converter.ListContainerSlotConverter;
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

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    indexes = {
        @Index(unique = true, name = "idx_container_code", columnList = "containerCode")
    }
)
public class ContainerPO extends BaseUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", type = IdGenerator.class)
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '容器编码'")
    private String containerCode;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '容器规格编码'")
    private String containerSpecCode;

    @Column(columnDefinition = "varchar(64) comment '仓库编码'")
    private String warehouseCode;
    @Column(columnDefinition = "varchar(64) comment '库区编码'")
    private String warehouseAreaCode;
    @Column(columnDefinition = "varchar(64) comment '逻辑区编码'")
    private String warehouseLogicCode;
    @Column(columnDefinition = "varchar(64) comment '库位编码'")
    private String locationCode;
    @Column(columnDefinition = "varchar(64) comment '库位类型'")
    private String locationType;

    @Column(nullable = false, columnDefinition = "decimal(18,6) default '0.000000' comment 'SKU体积占用比例'")
    private BigDecimal occupationRatio = BigDecimal.ZERO;

    private boolean emptyContainer;
    private boolean locked;
    private boolean opened;

    @Column(nullable = false, columnDefinition = "int(11) default 0 comment '空格口数量'")
    private Integer emptySlotNum;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) comment '容器状态'")
    private ContainerStatusEnum containerStatus;

    @Column(columnDefinition = "json comment '容器格口'")
    @Convert(converter = ListContainerSlotConverter.class)
    private List<ContainerDTO.ContainerSlot> containerSlots;

    @Version
    private Long version;
}
