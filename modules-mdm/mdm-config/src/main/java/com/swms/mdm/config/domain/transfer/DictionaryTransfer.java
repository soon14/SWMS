package com.swms.mdm.config.domain.transfer;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;

import com.swms.mdm.api.config.dto.DictionaryDTO;
import com.swms.mdm.config.domain.entity.Dictionary;
import com.swms.utils.language.MultiLanguage;
import com.swms.utils.language.core.LanguageContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
    nullValueCheckStrategy = ALWAYS,
    nullValueMappingStrategy = RETURN_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DictionaryTransfer {

    @Mapping(source = "name", target = "name", qualifiedByName = "toMultiLanguage")
    @Mapping(source = "description", target = "description", qualifiedByName = "toMultiLanguage")
    Dictionary toDO(DictionaryDTO dictionaryDTO);

    List<Dictionary> toDOS(List<DictionaryDTO> dictionaryDTOS);

    @Mapping(source = "name", target = "name", qualifiedByName = "toCurrentLanguage")
    @Mapping(source = "description", target = "description", qualifiedByName = "toCurrentLanguage")
    DictionaryDTO toDTO(Dictionary dictionary);

    default Dictionary.DictionaryItem toDOItem(DictionaryDTO.DictionaryItem dictionaryItemDTO) {
        Dictionary.DictionaryItem dictionaryItem = new Dictionary.DictionaryItem();
        dictionaryItem.setDefaultItem(dictionaryItemDTO.isDefaultItem());
        dictionaryItem.setDescription(new MultiLanguage(LanguageContext.getLanguage(), dictionaryItemDTO.getDescription()));
        dictionaryItem.setOrder(dictionaryItemDTO.getOrder());
        dictionaryItem.setShowContext(new MultiLanguage(LanguageContext.getLanguage(), dictionaryItemDTO.getShowContext()));
        dictionaryItem.setValue(dictionaryItemDTO.getValue());
        return dictionaryItem;
    }

    default DictionaryDTO.DictionaryItem toDTOItem(Dictionary.DictionaryItem dictionaryItem) {
        DictionaryDTO.DictionaryItem dictionaryItemDTO = new DictionaryDTO.DictionaryItem();
        dictionaryItemDTO.setDefaultItem(dictionaryItem.isDefaultItem());
        dictionaryItemDTO.setDescription(toCurrentLanguage(dictionaryItem.getDescription()));
        dictionaryItemDTO.setOrder(dictionaryItem.getOrder());
        dictionaryItemDTO.setShowContext(toCurrentLanguage(dictionaryItem.getShowContext()));
        dictionaryItemDTO.setValue(dictionaryItem.getValue());
        return dictionaryItemDTO;
    }

    @Named("toMultiLanguage")
    static MultiLanguage toMultiLanguage(String value) {
        return new MultiLanguage(LanguageContext.getLanguage(), value);
    }

    @Named("toCurrentLanguage")
    static String toCurrentLanguage(MultiLanguage language) {
        if (language == null) {
            return "";
        }
        return language.get();
    }
}
