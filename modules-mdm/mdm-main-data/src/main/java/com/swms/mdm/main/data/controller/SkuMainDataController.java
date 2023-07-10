package com.swms.mdm.main.data.controller;

import com.swms.mdm.api.main.data.ISkuApi;
import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.mdm.main.data.controller.parameter.SkuMainDataVO;
import com.swms.mdm.main.data.controller.transfer.SkuMainDataDTOTransfer;
import com.swms.mdm.main.data.domain.entity.SkuMainData;
import com.swms.mdm.main.data.domain.repository.SkuMainDataRepository;
import com.swms.mdm.main.data.domain.transfer.SkuMainDataTransfer;
import com.swms.utils.http.Response;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("skuMainData")
public class SkuMainDataController {

    @Autowired
    private ISkuApi iSkuApi;

    @Autowired
    private SkuMainDataRepository skuMainDataRepository;

    @Autowired
    private SkuMainDataDTOTransfer skuMainDataDTOTransfer;

    @PostMapping("createOrUpdate")
    public Object createOrUpdate(@RequestBody @Valid SkuMainDataVO skuMainDataVO) {
        SkuMainDataDTO skuMainDataDTO = new SkuMainDataDTO();
        BeanUtils.copyProperties(skuMainDataVO, skuMainDataDTO);

        if (skuMainDataDTO.getId() != null && skuMainDataDTO.getId() > 0) {
            iSkuApi.create(skuMainDataDTO);
            return Response.success();
        }
        iSkuApi.update(skuMainDataDTO);
        return Response.success();
    }

    @GetMapping("{id}")
    public Object getById(@PathVariable Long id) {
        SkuMainData ownerData = skuMainDataRepository.findById(id);
        return Response.builder().data(skuMainDataDTOTransfer.toVO(ownerData)).build();
    }
}
