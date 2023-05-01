package com.swms.wms.task.infrastructure.repository.impl;

import com.swms.wms.task.domain.entity.TransferContainer;
import com.swms.wms.task.domain.repository.TransferContainerRepository;
import com.swms.wms.task.infrastructure.persistence.mapper.TransferContainerPORepository;
import com.swms.wms.task.infrastructure.persistence.mapper.TransferContainerTaskRelationPORepository;
import com.swms.wms.task.infrastructure.persistence.transfer.TransferContainerPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferContainerRepositoryImpl implements TransferContainerRepository {

    @Autowired
    private TransferContainerPORepository transferContainerPORepository;

    @Autowired
    private TransferContainerPOTransfer transferContainerPOTransfer;

    @Autowired
    private TransferContainerTaskRelationPORepository transferContainerTaskRelationPORepository;

    @Transactional
    @Override
    public void save(TransferContainer transferContainer) {
        transferContainerPORepository.save(transferContainerPOTransfer.toPO(transferContainer));
        transferContainerTaskRelationPORepository.saveAll(transferContainerPOTransfer.toPOs(transferContainer.getTransferContainerTasks()));
    }
}
