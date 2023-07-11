package com.swms.mdm.config.infrastructure.persistence.po;

import com.swms.mdm.api.config.constants.BusinessFlowEnum;
import com.swms.mdm.api.config.constants.ExecuteTimeEnum;
import com.swms.mdm.api.config.constants.UnionLocationEnum;
import com.swms.utils.base.BaseUserPO;
import com.swms.utils.id.IdGenerator;
import com.swms.utils.jpa.converter.ListStringConverter;
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
        @Index(unique = true, name = "idx_barcode_parse_code", columnList = "code")
    }
)
public class BarcodeParseRulePO extends BaseUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '编码'")
    private String code;

    @Column(nullable = false, columnDefinition = "varchar(128) comment '名称'")
    private String name;

    @Column(columnDefinition = "varchar(64) comment '货主编码'")
    private String ownerCode;
    @Column(columnDefinition = "varchar(64) comment '品牌'")
    private String brand;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) comment '执行时间'")
    private ExecuteTimeEnum executeTime;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) comment '业务流程'")
    private BusinessFlowEnum businessFlow;

    private boolean enable;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20) comment '拼接位置'")
    private UnionLocationEnum unionLocation;

    @Column(columnDefinition = "varchar(255) comment '拼接符'")
    private String unionStr;

    @Column(nullable = false, columnDefinition = "varchar(500) comment '正则表达式'")
    private String regularExpression;

    @Column(columnDefinition = "json comment '解析规则参数'")
    @Convert(converter = ListStringConverter.class)
    private List<String> resultFields;

    @Version
    private Long version;
}
