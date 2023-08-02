package com.swms.mdm.api.config;

import com.swms.common.utils.language.core.LanguageContext;
import com.swms.common.utils.utils.ObjectUtils;
import com.swms.mdm.api.BaseTest;
import com.swms.mdm.api.config.dto.DictionaryDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DictionaryApiTest extends BaseTest {

    @Autowired
    private IDictionaryApi dictionaryApi;

    @Test
    void testSave() {

        LanguageContext.setLanguage("en");
        DictionaryDTO dictionaryDTO = ObjectUtils.getRandomObject(DictionaryDTO.class);
        dictionaryApi.save(dictionaryDTO);
        dictionaryDTO = dictionaryApi.getByCode(dictionaryDTO.getCode());
        Assertions.assertNotNull(dictionaryDTO);

        String description = dictionaryDTO.getItems().get(0).getDescription();
        Assertions.assertNotNull(description);
    }
}
