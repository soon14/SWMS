package com.swms.wms.basic.warehouse.infrastructure.persistence.po;

import com.swms.utils.base.BaseUserPO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
    name = "w_warehouse_logic",
    indexes = {
        @Index(unique = true, name = "idx_warehouse_logic_area_group_code",
            columnList = "warehouseLogicCode,warehouseAreaCode,warehouseGroupCode,warehouseCode")
    }
)
public class WarehouseLogicPO extends BaseUserPO {


    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '仓库编码'")
    private String warehouseCode;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '仓区编码'")
    private String warehouseGroupCode;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '库区编码'")
    private String warehouseAreaCode;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '逻辑区编码'")
    private String warehouseLogicCode;

    @Column(nullable = false, columnDefinition = "varchar(128) comment '逻辑区编码'")
    private String warehouseLogicName;

    @Column(nullable = false, columnDefinition = "varchar(500) comment '备注'")
    private String remark;

    private boolean deleted;
    private boolean enable;

    @Version
    private long version;
}
