package com.swms.mdm.config.controller;

import com.swms.mdm.api.main.data.dto.AddressDTO;
import com.swms.mdm.config.domain.repository.AddressRepository;
import com.swms.utils.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
@Validated
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @PostMapping(value = "getNextAddresses")
    public Object getNextAddresses(@RequestBody AddressDTO addressDTO) {
        return Response.builder().data(addressRepository.getNextAddresses(addressDTO)).build();
    }
}
