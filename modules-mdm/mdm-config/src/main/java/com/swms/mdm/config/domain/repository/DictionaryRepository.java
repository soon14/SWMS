package com.swms.mdm.config.domain.repository;

import com.swms.mdm.config.domain.entity.Dictionary;

import java.util.List;

public interface DictionaryRepository {

    void save(Dictionary dictionary);

    Dictionary findById(Long id);

    Dictionary findByCode(String code);

    List<Dictionary> getAll();

    void saveAll(List<Dictionary> dictionarys);
}
