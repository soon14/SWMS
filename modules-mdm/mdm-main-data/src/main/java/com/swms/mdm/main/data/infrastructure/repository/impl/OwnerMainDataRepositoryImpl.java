package com.swms.mdm.main.data.infrastructure.repository.impl;

import com.swms.mdm.main.data.domain.entity.OwnerMainData;
import com.swms.mdm.main.data.domain.repository.OwnerMainDataRepository;
import com.swms.mdm.main.data.infrastructure.persistence.mapper.OwnerMainDataPORepository;
import com.swms.mdm.main.data.infrastructure.persistence.transfer.OwnerMainDataPOTransfer;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

@Service
public class OwnerMainDataRepositoryImpl implements OwnerMainDataRepository {

    @Autowired
    private OwnerMainDataPORepository ownerMainDataPORepository;

    @Autowired
    private OwnerMainDataPOTransfer ownerMainDataPOTransfer;

    @Override
    public void save(OwnerMainData ownerMainData) {
        ownerMainDataPORepository.save(ownerMainDataPOTransfer.toPO(ownerMainData));
    }

    @Override
    public void update(OwnerMainData ownerMainData) {
        ownerMainDataPORepository.save(ownerMainDataPOTransfer.toPO(ownerMainData));
    }

    @Override
    public OwnerMainData getOwnerMainData(String ownerCode) {
        return ownerMainDataPOTransfer.toDO(ownerMainDataPORepository.findByOwnerCode(ownerCode));
    }

    @Override
    public List<OwnerMainData> getOwnerMainData(Collection<String> ownCodes) {
        return ownerMainDataPOTransfer.toDOS(ownerMainDataPORepository.findAllByOwnerCodeIn(ownCodes));
    }

    @Override
    public OwnerMainData findById(Long id) {
        return ownerMainDataPOTransfer.toDO(ownerMainDataPORepository.findById(id).orElseThrow());
    }
}
