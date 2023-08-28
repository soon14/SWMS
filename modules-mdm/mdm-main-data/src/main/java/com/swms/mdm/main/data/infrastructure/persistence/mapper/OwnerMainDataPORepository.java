package com.swms.mdm.main.data.infrastructure.persistence.mapper;

import com.swms.mdm.main.data.infrastructure.persistence.po.OwnerMainDataPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface OwnerMainDataPORepository extends JpaRepository<OwnerMainDataPO, Long> {
    Optional<OwnerMainDataPO> findByOwnerCode(String ownerCode);

    Optional<Collection<OwnerMainDataPO>> findAllByOwnerCodeIn(Collection<String> ownerCodes);

}
