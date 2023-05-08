package com.swms.wms.task.domain.service;

import com.swms.wms.task.domain.entity.TransferContainer;

public interface TransferContainerService {

    void setIndexAndTotal(TransferContainer transferContainer);

    void setDestination(TransferContainer transferContainer);
}
