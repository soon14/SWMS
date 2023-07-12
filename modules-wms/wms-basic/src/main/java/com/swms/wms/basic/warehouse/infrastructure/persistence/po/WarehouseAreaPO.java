package com.swms.wms.basic.warehouse.infrastructure.persistence.po;

import com.swms.utils.base.BaseUserPO;
import com.swms.wms.api.basic.constants.WarehouseAreaTypeEnum;
import com.swms.wms.api.basic.constants.WarehouseAreaUseEnum;
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
    indexes = {
        @Index(unique = true, name = "idx_warehouse_area_code", columnList = "warehouseAreaCode")
    }
)
public class WarehouseAreaPO extends BaseUserPO {


    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '仓库编码'")
    private String warehouseCode;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '仓区名称'")
    private String warehouseGroupCode;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '库区编码'")
    private String warehouseAreaCode;

    @Column(nullable = false, columnDefinition = "varchar(128) comment '库区名称'")
    private String warehouseAreaName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) comment '库区类型'")
    private WarehouseAreaTypeEnum warehouseAreaType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) comment '库区用途'")
    private WarehouseAreaUseEnum warehouseAreaUse;

    @Column(nullable = false, columnDefinition = "varchar(500) comment '备注'")
    private String remark;

    private int level;
    private int temperatureLimit;
    private int wetLimit;

    private boolean deleted;
    private boolean enable;

    @Version
    private long version;
}