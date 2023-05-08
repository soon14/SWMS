package com.swms.mdm.main.data.infrastructure.persistence.mapper;

import com.swms.mdm.main.data.infrastructure.persistence.po.SkuMainDataPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SkuMainDataPORepository extends JpaRepository<SkuMainDataPO, Long> {
    SkuMainDataPO findBySkuCodeAndOwnerCode(String skuCode, String ownerCode);

    List<SkuMainDataPO> findAllBySkuCodeIn(Collection<String> skuCodes);
}
