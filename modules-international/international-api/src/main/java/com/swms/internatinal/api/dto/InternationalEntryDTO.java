package com.swms.internatinal.api.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class InternationalEntryDTO {

    private Long id;

    @NotEmpty
    private String entryKey;

    private String description;

    private Long version;

    @NotEmpty
    private List<LanguageValueMapping> languageValueMappings;

    public String getEntryValue(String language) {

        if (languageValueMappings == null) {
            return null;
        }

        InternationalEntryDTO.LanguageValueMapping languageValueMapping = languageValueMappings.stream()
            .filter(v -> Objects.equals(v.getLanguage(), language))
            .findFirst().orElse(null);

        return languageValueMapping == null ? "" : languageValueMapping.getEntryValue();
    }

    @Data
    public static class LanguageValueMapping {
        @NotEmpty
        private String language;
        @NotEmpty
        private String entryValue;
    }
}
