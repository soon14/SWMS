package com.swms.wms.inbound.domain.repository;

import com.swms.wms.inbound.domain.entity.PutAwayTask;

public interface PutAwayTaskRepository {

    void save(PutAwayTask putAwayTask);
}
