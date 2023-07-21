package com.swms.wms.stock.infrastructure.persistence.po;

import com.swms.utils.base.BaseUserPO;
import com.swms.utils.jpa.converter.MapConverter;
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
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.TreeMap;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "w_sku_batch_attribute",
    indexes = {
        @Index(name = "id_sku_id", columnList = "skuId"),
        @Index(name = "batchNo", columnList = "batchNo", unique = true)
    }
)
public class SkuBatchAttributePO extends BaseUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "bigint default 0 comment 'skuId'")
    private Long skuId;

    @Column(columnDefinition = "json comment '批次属性'")
    @Convert(converter = MapConverter.class)
    private TreeMap<String, Object> skuAttributes;

    @Column(nullable = false, columnDefinition = "varchar(128) comment '批次号'")
    private String batchNo;

    @Column(columnDefinition = "varchar(128) comment '批次属性预留'")
    private String skuAttribute1;
    @Column(columnDefinition = "varchar(128) comment '批次属性预留'")
    private String skuAttribute2;
    @Column(columnDefinition = "varchar(128) comment '批次属性预留'")
    private String skuAttribute3;
    @Column(columnDefinition = "varchar(128) comment '批次属性预留'")
    private String skuAttribute4;
    @Column(columnDefinition = "varchar(128) comment '批次属性预留'")
    private String skuAttribute5;
    @Column(columnDefinition = "varchar(128) comment '批次属性预留'")
    private String skuAttribute6;
    @Column(columnDefinition = "varchar(128) comment '批次属性预留'")
    private String skuAttribute7;
    @Column(columnDefinition = "varchar(128) comment '批次属性预留'")
    private String skuAttribute8;
    @Column(columnDefinition = "varchar(128) comment '批次属性预留'")
    private String skuAttribute9;
    @Column(columnDefinition = "varchar(128) comment '批次属性预留'")
    private String skuAttribute10;

    @Version
    private Long version;
}
