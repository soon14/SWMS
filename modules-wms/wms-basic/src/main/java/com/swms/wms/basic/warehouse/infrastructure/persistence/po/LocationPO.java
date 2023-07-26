package com.swms.wms.basic.warehouse.infrastructure.persistence.po;

import com.swms.utils.base.BaseUserPO;
import com.swms.wms.api.basic.constants.LocationStatusEnum;
import com.swms.wms.api.basic.constants.LocationTypeEnum;
import jakarta.persistence.Column;
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

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "w_location",
    indexes = {
        @Index(unique = true, name = "idx_warehouse_area_location", columnList = "locationCode,warehouseAreaId"),
        @Index(name = "idx_aisle_warehouse_area", columnList = "aisleCode,warehouseAreaId"),
        @Index(name = "idx_warehouse_area_id", columnList = "warehouseAreaId"),
        @Index(name = "idx_warehouse_logic_id", columnList = "warehouseLogicId")
    }
)
public class LocationPO extends BaseUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '库位编码'")
    private String locationCode;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '巷道编码'")
    private String aisleCode;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '货架编码'")
    private String shelfCode;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '仓库编码'")
    private String warehouseCode;

    @Column(nullable = false, columnDefinition = "bigint default 0 comment '库区ID'")
    private Long warehouseAreaId;

    @Column(nullable = false, columnDefinition = "bigint default 0 comment '逻辑区ID'")
    private Long warehouseLogicId;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '库位类型'")
    private LocationTypeEnum locationType;

    @Column(columnDefinition = "varchar(64) default '' comment '热度'")
    private String heat;
    private boolean occupied;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) comment '库位状态'")
    private LocationStatusEnum locationStatus = LocationStatusEnum.PUT_AWAY_PUT_DOWN;

    @Version
    private long version;
}
