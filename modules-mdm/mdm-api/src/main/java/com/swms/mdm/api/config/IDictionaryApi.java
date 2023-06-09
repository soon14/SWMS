package com.swms.mdm.api.config;

import com.swms.mdm.api.config.dto.DictionaryDTO;
import jakarta.validation.Valid;

public interface IDictionaryApi {

    void save(@Valid DictionaryDTO dictionaryDTO);

    void update(@Valid DictionaryDTO dictionaryDTO);

    DictionaryDTO getByCode(String code);
}
