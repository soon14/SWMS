package com.swms.international.core.domain.entity;

import com.swms.common.utils.base.UpdateUserPO;
import com.swms.internatinal.api.dto.InternationalEntryDTO;
import com.swms.international.core.domain.convertor.ListLanguageValueMappingConverter;
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
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "i_international_entry",
    indexes = {
        @Index(unique = true, name = "uk_international_entry_key", columnList = "entryKey"),
    }
)
@DynamicUpdate
public class InternationalEntry extends UpdateUserPO {

    @Id
    @GeneratedValue(generator = "databaseIdGenerator")
    @GenericGenerator(name = "databaseIdGenerator", strategy = "com.swms.common.utils.id.IdGenerator")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(256) comment '词条key'")
    private String entryKey;

    @Column(nullable = false, columnDefinition = "varchar(512) comment '词条描述'")
    private String description = "";

    @Version
    private Long version;

    @Column(columnDefinition = "json comment '语种对应的value'")
    @Convert(converter = ListLanguageValueMappingConverter.class)
    private List<InternationalEntryDTO.LanguageValueMapping> languageValueMappings;

}
