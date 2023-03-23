package com.swms.wms.task.domain.repository;

import com.swms.wms.task.domain.entity.TransferContainerTaskRelation;

import java.util.List;

public interface TransferContainerTaskRelationRepository {
    void saveAll(List<TransferContainerTaskRelation> transferContainerTaskRelations);
}
