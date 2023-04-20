package com.swms.mdm.main.data.domain.repository;

import com.swms.mdm.main.data.domain.entity.OwnerMainData;
import com.swms.mdm.main.data.domain.entity.SkuMainData;

import java.util.Collection;
import java.util.List;

public interface OwnerMainDataRepository {

    void save(OwnerMainData ownerMainData);

    void update(OwnerMainData ownerMainData);

    OwnerMainData getOwnerMainData(String ownerCode);

    List<OwnerMainData> getOwnerMainData(Collection<String> ownCodes);

}
