package com.swms.mdm.main.data.infrastructure.repository.impl;

import static com.swms.common.utils.exception.code_enum.MainDataErrorDescEnum.OWNER_CODE_NOT_EXIST;
import static com.swms.common.utils.exception.code_enum.MainDataErrorDescEnum.OWNER_NOT_EXIST;

import com.swms.common.utils.exception.WmsException;
import com.swms.mdm.main.data.domain.entity.OwnerMainData;
import com.swms.mdm.main.data.domain.repository.OwnerMainDataRepository;
import com.swms.mdm.main.data.infrastructure.persistence.mapper.OwnerMainDataPORepository;
import com.swms.mdm.main.data.infrastructure.persistence.transfer.OwnerMainDataPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OwnerMainDataRepositoryImpl implements OwnerMainDataRepository {

    @Autowired
    private OwnerMainDataPORepository ownerMainDataPORepository;

    @Autowired
    private OwnerMainDataPOTransfer ownerMainDataPOTransfer;

    @Override
    public void save(OwnerMainData ownerMainData) {
        Optional.ofNullable(ownerMainData)
            .map(ownerMainDataPOTransfer::toPO)
            .ifPresent(ownerMainDataPORepository::save);
    }

    @Override
    public void update(OwnerMainData ownerMainData) {
        Optional.ofNullable(ownerMainData)
            .map(ownerMainDataPOTransfer::toPO)
            .ifPresent(ownerMainDataPORepository::save);
    }

    @Override
    public OwnerMainData getOwnerMainData(String ownerCode) {
        return ownerMainDataPORepository.findByOwnerCode(ownerCode)
            .map(ownerMainDataPOTransfer::toDO)
            .orElseThrow(WmsException.throwWmsExceptionSup(OWNER_CODE_NOT_EXIST, ownerCode));
    }

    @Override
    public Collection<OwnerMainData> getOwnersMainData(Collection<String> ownCodes) {
        return ownerMainDataPORepository.findAllByOwnerCodeIn(ownCodes)
            .stream()
            .flatMap(Collection::stream)
            .map(ownerMainDataPOTransfer::toDO)
            .collect(Collectors.toSet());
    }

    @Override
    public OwnerMainData findById(Long id) {
        return ownerMainDataPORepository.findById(id)
            .map(ownerMainDataPOTransfer::toDO)
            .orElseThrow(WmsException.throwWmsExceptionSup(OWNER_NOT_EXIST));
    }
}
