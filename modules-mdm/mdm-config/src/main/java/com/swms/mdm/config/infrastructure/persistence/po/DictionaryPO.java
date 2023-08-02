package com.swms.mdm.config.infrastructure.persistence.po;

import com.swms.mdm.config.domain.entity.Dictionary;
import com.swms.mdm.config.infrastructure.persistence.converter.ListDictionaryItemConverter;
import com.swms.utils.base.UpdateUserPO;
import com.swms.utils.language.MultiLanguage;
import com.swms.utils.language.converter.MultiLanguageConverter;
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

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "m_dictionary",
    indexes = {
        @Index(unique = true, name = "idx_dictionary_code", columnList = "code")
    }
)
public class DictionaryPO extends UpdateUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(64) comment '编码'")
    private String code;

    private boolean editable;

    @Column(nullable = false, columnDefinition = "json comment '名称'")
    @Convert(converter = MultiLanguageConverter.class)
    private MultiLanguage name;


    @Column(columnDefinition = "json comment '描述'")
    @Convert(converter = MultiLanguageConverter.class)
    private MultiLanguage description;

    @Column(nullable = false, columnDefinition = "json comment '字典内容'")
    @Convert(converter = ListDictionaryItemConverter.class)
    private List<Dictionary.DictionaryItem> items;

    @Version
    private long version;

}
