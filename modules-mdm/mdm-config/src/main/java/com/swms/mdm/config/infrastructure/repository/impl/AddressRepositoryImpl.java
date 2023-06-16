package com.swms.mdm.config.infrastructure.repository.impl;

import com.google.common.collect.Maps;
import com.swms.mdm.api.main.data.dto.AddressDTO;
import com.swms.mdm.config.domain.repository.AddressRepository;
import com.swms.mdm.config.infrastructure.persistence.mapper.AddressPORepository;
import com.swms.mdm.config.infrastructure.persistence.po.AddressPO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AddressRepositoryImpl implements AddressRepository {

    @Autowired
    private AddressPORepository addressPORepository;

    @Override
    public Map<String, List<String>> getNextAddresses(AddressDTO addressDTO) {

        Map<String, List<String>> addresses = Maps.newHashMap();
        if (StringUtils.isEmpty(addressDTO.getCountry())) {
            List<String> counties = addressPORepository.findDistinctCountry();
            addresses.put("country", counties);
            return addresses;
        }

        if (StringUtils.isEmpty(addressDTO.getProvince())) {
            List<AddressPO> addressPOS = addressPORepository.findDistinctByCountry(addressDTO.getCountry());
            addresses.put(addressDTO.getCountry(), addressPOS.stream().map(AddressPO::getProvince).distinct().toList());
            return addresses;
        }

        if (StringUtils.isEmpty(addressDTO.getCity())) {
            List<AddressPO> addressPOS = addressPORepository
                .findDistinctByCountryAndProvince(addressDTO.getCountry(), addressDTO.getProvince());
            addresses.put(addressDTO.getProvince(), addressPOS.stream().map(AddressPO::getCity).distinct().toList());
            return addresses;
        }

        List<AddressPO> addressPOS = addressPORepository
            .findDistinctByCountryAndProvinceAndCity(addressDTO.getCountry(), addressDTO.getProvince(), addressDTO.getCity());
        addresses.put(addressDTO.getCity(), addressPOS.stream().map(AddressPO::getDistrict).distinct().toList());
        return addresses;
    }
}
