package com.swms.mdm.config.application;

import com.swms.mdm.api.config.IDictionaryApi;
import com.swms.mdm.api.config.dto.DictionaryDTO;
import com.swms.mdm.config.domain.repository.DictionaryRepository;
import com.swms.mdm.config.domain.transfer.DictionaryTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class DictionaryApplicationImpl implements IDictionaryApi {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @Autowired
    private DictionaryTransfer dictionaryTransfer;

    @Override
    public void save(DictionaryDTO dictionaryDTO) {
        dictionaryRepository.save(dictionaryTransfer.toDO(dictionaryDTO));
    }

    /**
     * @param dictionaryDTO
     */
    @Override
    public void update(DictionaryDTO dictionaryDTO) {
        dictionaryRepository.save(dictionaryTransfer.toDO(dictionaryDTO));
    }

    @Override
    public DictionaryDTO getByCode(String code) {
        return dictionaryTransfer.toDTO(dictionaryRepository.findByCode(code));
    }
}
