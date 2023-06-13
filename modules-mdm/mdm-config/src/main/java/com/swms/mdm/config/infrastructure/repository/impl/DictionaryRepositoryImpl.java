package com.swms.mdm.config.infrastructure.repository.impl;

import com.swms.mdm.config.domain.entity.Dictionary;
import com.swms.mdm.config.domain.repository.DictionaryRepository;
import com.swms.mdm.config.infrastructure.persistence.mapper.DictionaryPORepository;
import com.swms.mdm.config.infrastructure.persistence.po.DictionaryPO;
import com.swms.mdm.config.infrastructure.persistence.transfer.DictionaryPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionaryRepositoryImpl implements DictionaryRepository {

    @Autowired
    private DictionaryPORepository dictionaryPORepository;

    @Autowired
    private DictionaryPOTransfer dictionaryPOTransfer;

    @Override
    public void save(Dictionary dictionary) {
        dictionaryPORepository.save(dictionaryPOTransfer.toPO(dictionary));
    }

    @Override
    public Dictionary findById(Long id) {
        DictionaryPO dictionaryPO = dictionaryPORepository.findById(id).orElseThrow();
        return dictionaryPOTransfer.toDO(dictionaryPO);
    }

    @Override
    public Dictionary findByCode(String code) {
        return dictionaryPOTransfer.toDO(dictionaryPORepository.findByCode(code));
    }

    @Override
    public List<Dictionary> getAll() {
        return dictionaryPOTransfer.toDOS(dictionaryPORepository.findAll());
    }
}
