package com.swms.wms.basic.container.application;

import com.swms.utils.validate.ValidationSequence;
import com.swms.wms.api.basic.IContainerSpecApi;
import com.swms.wms.api.basic.dto.ContainerSpecDTO;
import com.swms.wms.basic.container.domain.repository.ContainerSpecRepository;
import com.swms.wms.basic.container.domain.transfer.ContainerSpecTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated(ValidationSequence.class)
@Service
public class ContainerSpecApiImpl implements IContainerSpecApi {

    @Autowired
    private ContainerSpecRepository containerSpecRepository;

    @Autowired
    private ContainerSpecTransfer containerSpecTransfer;

    @Override
    public void save(ContainerSpecDTO containerSpecDTO) {
        containerSpecRepository.save(containerSpecTransfer.toDO(containerSpecDTO));
    }

    @Override
    public void update(ContainerSpecDTO containerSpecDTO) {
        containerSpecRepository.save(containerSpecTransfer.toDO(containerSpecDTO));
    }
}
