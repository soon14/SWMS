package com.swms.mdm.main.data.application;

import com.swms.mdm.api.main.data.IOwnerMainDataApi;
import com.swms.mdm.api.main.data.dto.OwnerMainDataDTO;
import com.swms.mdm.main.data.domain.repository.OwnerMainDataRepository;
import com.swms.mdm.main.data.domain.transfer.OwnerMainDataTransfer;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@DubboService
public class OwnerMainDataApiImpl implements IOwnerMainDataApi {

    @Autowired
    private OwnerMainDataRepository ownerMainDataRepository;

    @Autowired
    private OwnerMainDataTransfer ownerMainDataTransfer;


    @Override
    public void createOwner(OwnerMainDataDTO ownerMainDataDTO) {
        ownerMainDataRepository.save(ownerMainDataTransfer.toOwnerMainData(ownerMainDataDTO));
    }

    @Override
    public void updateOwner(OwnerMainDataDTO ownerMainDataDTO) {
        ownerMainDataRepository.save(ownerMainDataTransfer.toOwnerMainData(ownerMainDataDTO));
    }

    @Override
    public OwnerMainDataDTO getOwner(String ownerCode) {
        return ownerMainDataTransfer.toOwnerMainDataDTO(ownerMainDataRepository.getOwnerMainData(ownerCode));
    }
}
