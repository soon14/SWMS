package com.swms.mdm.main.data.infrastructure.persistence.mapper;

import com.swms.mdm.main.data.infrastructure.persistence.po.OwnerMainDataPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface OwnerMainDataPORepository extends JpaRepository<OwnerMainDataPO, String> {
    OwnerMainDataPO findByOwnerCode(String ownerCode);

    List<OwnerMainDataPO> findAllByOwnerCodeIn(Collection<String> ownerCodes);
}
