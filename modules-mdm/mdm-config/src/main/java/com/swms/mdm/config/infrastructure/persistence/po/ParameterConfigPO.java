package com.swms.mdm.config.infrastructure.persistence.po;

import com.swms.common.utils.base.UpdateUserPO;
import com.swms.mdm.api.config.constants.ConfigApplyModuleEnum;
import com.swms.mdm.api.config.constants.ConfigTypeEnum;
import com.swms.mdm.api.config.constants.ParameterCodeEnum;
import com.swms.mdm.api.config.dto.ParameterConfigDTO;
import com.swms.mdm.config.infrastructure.persistence.converter.ListParameterConfigConditionConverter;
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
    name = "m_parameter_config",
    indexes = {
        @Index(unique = true, name = "idx_parameter_config_code", columnList = "code")
    }
)
public class ParameterConfigPO extends UpdateUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.common.utils.id.IdGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(64) comment '参数编码'")
    private ParameterCodeEnum code;

    @Column(nullable = false, columnDefinition = "varchar(128) comment '名称'")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) comment '应用模块'")
    private ConfigApplyModuleEnum configApplyModule;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) comment '参数类型'")
    private ConfigTypeEnum configType;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '默认值'")
    private String defaultValue;

    private boolean enable;

    @Column(nullable = false, columnDefinition = "varchar(255) comment '描述'")
    private String description;

    @Column(columnDefinition = "varchar(500) comment '备注'")
    private String remark;

    @Column(columnDefinition = "json comment '参数条件'")
    @Convert(converter = ListParameterConfigConditionConverter.class)
    private List<ParameterConfigDTO.ParameterConfigCondition> parameterConfigConditions;

    @Version
    private Long version;
}
