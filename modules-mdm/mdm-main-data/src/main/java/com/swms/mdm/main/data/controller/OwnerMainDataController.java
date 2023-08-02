package com.swms.mdm.main.data.controller;

import com.swms.mdm.api.main.data.IOwnerMainDataApi;
import com.swms.mdm.api.main.data.dto.OwnerMainDataDTO;
import com.swms.mdm.main.data.controller.parameter.OwnerMainDataVO;
import com.swms.mdm.main.data.domain.entity.OwnerMainData;
import com.swms.mdm.main.data.domain.repository.OwnerMainDataRepository;
import com.swms.mdm.main.data.domain.transfer.OwnerMainDataTransfer;
import com.swms.common.utils.http.Response;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ownerMainData")
public class OwnerMainDataController {

    @Autowired
    private IOwnerMainDataApi iOwnerApi;

    @Autowired
    private OwnerMainDataRepository ownerDataRepository;

    @Autowired
    private OwnerMainDataTransfer ownerDataTransfer;

    @PostMapping("createOrUpdate")
    public Object createOrUpdate(@RequestBody @Valid OwnerMainDataVO ownerDataVO) {
        OwnerMainDataDTO ownerDataDTO = new OwnerMainDataDTO();
        BeanUtils.copyProperties(ownerDataVO, ownerDataDTO);

        if (ownerDataDTO.getId() != null && ownerDataDTO.getId() > 0) {
            iOwnerApi.createOwner(ownerDataDTO);
            return Response.success();
        }
        iOwnerApi.updateOwner(ownerDataDTO);
        return Response.success();
    }

    @PostMapping("getById")
    public Object getById(@RequestParam Long id) {
        OwnerMainData skuMainData = ownerDataRepository.findById(id);
        return Response.builder().data(ownerDataTransfer.toOwnerMainDataDTO(skuMainData)).build();
    }
}
