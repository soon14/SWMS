package com.swms.mdm.api.config;

import com.swms.mdm.api.config.dto.BatchAttributeConfigDTO;

public interface IBatchAttributeConfigApi {

    void save(BatchAttributeConfigDTO batchAttributeConfigDTO);

    void update(BatchAttributeConfigDTO batchAttributeConfigDTO);

    BatchAttributeConfigDTO getBatchAttributeConfig(String ownerCode,);
}
