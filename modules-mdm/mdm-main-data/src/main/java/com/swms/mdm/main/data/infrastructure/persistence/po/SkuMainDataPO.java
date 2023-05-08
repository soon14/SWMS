package com.swms.mdm.main.data.infrastructure.persistence.po;

import com.swms.utils.base.BaseUserPO;
import com.swms.utils.id.IdGenerator;
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

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    indexes = {
        @Index(unique = true, name = "idx_sku_code_owner_code", columnList = "skuCode,ownerCode")
    }
)
public class SkuMainDataPO extends BaseUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(64) comment 'sku编码'")
    private String skuCode;
    @Column(nullable = false, columnDefinition = "varchar(64) comment '货主编码'")
    private String ownerCode;

    @Column(nullable = false, columnDefinition = "varchar(128) comment 'sku名称'")
    private String skuName;

    @Column(columnDefinition = "varchar(64) comment '款式'")
    private String style;
    @Column(columnDefinition = "varchar(64) comment '颜色'")
    private String color;
    @Column(columnDefinition = "varchar(64) comment '尺码'")
    private String size;
    @Column(columnDefinition = "varchar(64) comment '品牌'")
    private String brand;

    @Column(nullable = false, columnDefinition = "bigint comment '净重'")
    private Long grossWeight = 0L;
    @Column(nullable = false, columnDefinition = "bigint comment '毛重'")
    private Long netWeight = 0L;

    @Column(nullable = false, columnDefinition = "bigint comment '体积'")
    private Long volume = 0L;
    @Column(nullable = false, columnDefinition = "bigint comment '高度'")
    private Long height = 0L;
    @Column(nullable = false, columnDefinition = "bigint comment '宽度'")
    private Long width = 0L;
    @Column(nullable = false, columnDefinition = "bigint comment '长度'")
    private Long length = 0L;

    @Column(columnDefinition = "varchar(255) comment '图片地址'")
    private String imageUrl;
    @Column(columnDefinition = "varchar(64) comment '单位'")
    private String unit;

    @Column(columnDefinition = "varchar(64) comment '一级分类'")
    private String skuFirstCategory;
    @Column(columnDefinition = "varchar(64) comment '二级分类'")
    private String skuSecondCategory;
    @Column(columnDefinition = "varchar(64) comment '三级分类'")
    private String skuThirdCategory;

    @Column(columnDefinition = "varchar(64) comment '一级属性'")
    private String skuAttributeCategory;
    @Column(columnDefinition = "varchar(64) comment '二级属性'")
    private String skuAttributeSubCategory;

    private boolean enableSn;
    private boolean enableEffective;

    @Column(nullable = false, columnDefinition = "int comment '保质期(天)'")
    private Integer shelfLife = 0;
    @Column(nullable = false, columnDefinition = "int comment '效期限制天数'")
    private Integer effectiveDays = 0;

    @Column(columnDefinition = "varchar(64) comment '条码规则编码'")
    private String barcodeRuleCode;

    @Column(columnDefinition = "varchar(64) comment '温度'")
    private String heat;
    private boolean calculateHeat;

    private boolean noBarcode;

    @Version
    private Long version;
}
