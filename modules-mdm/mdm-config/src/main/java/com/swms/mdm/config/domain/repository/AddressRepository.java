package com.swms.mdm.config.domain.repository;

import com.swms.mdm.api.main.data.dto.AddressDTO;

import java.util.List;
import java.util.Map;

public interface AddressRepository {

    Map<String, List<String>> getNextAddresses(AddressDTO addressDTO);
}
