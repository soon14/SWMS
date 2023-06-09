package com.swms.mdm.config.infrastructure.repository.impl;

import com.swms.mdm.config.controller.parameter.DictionarySearchParameter;
import com.swms.mdm.config.domain.entity.Dictionary;
import com.swms.mdm.config.domain.repository.DictionaryRepository;
import com.swms.mdm.config.infrastructure.persistence.mapper.DictionaryPORepository;
import com.swms.mdm.config.infrastructure.persistence.po.DictionaryPO;
import com.swms.mdm.config.infrastructure.persistence.transfer.DictionaryPOTransfer;
import com.swms.utils.utils.PaginationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    /**
     * TODO
     *
     * @param parameter
     *
     * @return
     */
    @Override
    public Page<Dictionary> search(DictionarySearchParameter parameter) {

        Pageable pageable = PageRequest.ofSize(PaginationContext.getPageSize()).withPage(PaginationContext.getPageNum())
            .withSort(Sort.by("id").descending());

        return dictionaryPORepository.findAll(pageable).map(dictionaryPOTransfer::toDO);
    }

    @Override
    public List<Dictionary> getAll() {
        return dictionaryPOTransfer.toDOS(dictionaryPORepository.findAll());
    }
}
