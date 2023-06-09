package com.swms.mdm.config.domain.repository;

import com.swms.mdm.config.controller.parameter.DictionarySearchParameter;
import com.swms.mdm.config.domain.entity.Dictionary;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DictionaryRepository {

    void save(Dictionary dictionary);

    Dictionary findById(Long id);

    Dictionary findByCode(String code);

    Page<Dictionary> search(DictionarySearchParameter parameter);

    List<Dictionary> getAll();

}
