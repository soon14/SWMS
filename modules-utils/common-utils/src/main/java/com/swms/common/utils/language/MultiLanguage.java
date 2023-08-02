package com.swms.common.utils.language;

import com.swms.common.utils.language.core.LanguageContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultiLanguage {

    private Map<String, String> languages;

    public MultiLanguage(String language, String name) {
        this.languages = new HashMap<>();
        this.languages.put(language, name);
    }

    public void put(String lang, String value) {
        languages.put(lang, value);
    }

    public String get(String lang) {
        return languages.get(lang);
    }

    public String get() {
        String lang = StringUtils.isNotEmpty(LanguageContext.getLanguage()) ? LanguageContext.getLanguage() : "zh_CN";
        return languages.get(lang);
    }
}
