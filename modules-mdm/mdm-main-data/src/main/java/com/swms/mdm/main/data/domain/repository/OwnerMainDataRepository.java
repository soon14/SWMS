package com.swms.mdm.main.data.domain.repository;

import com.swms.mdm.main.data.domain.entity.OwnerMainData;

import java.util.Collection;

public interface OwnerMainDataRepository {

    void save(OwnerMainData ownerMainData);

    void update(OwnerMainData ownerMainData);

    OwnerMainData getOwnerMainData(String ownerCode);

    Collection<OwnerMainData> getOwnersMainData(Collection<String> ownCodes);

    OwnerMainData findById(Long id);
}
