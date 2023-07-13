package com.swms.mdm.config.controller;

import com.swms.mdm.api.config.IBatchAttributeConfigApi;
import com.swms.mdm.api.config.dto.BatchAttributeConfigDTO;
import com.swms.mdm.config.domain.entity.BatchAttributeConfig;
import com.swms.mdm.config.domain.repository.BatchAttributeConfigRepository;
import com.swms.mdm.config.domain.transfer.BatchAttributeConfigTransfer;
import com.swms.utils.http.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("batchAttributeConfig")
@Validated
public class BatchAttributeConfigController {

    @Autowired
    private IBatchAttributeConfigApi iBatchAttributeConfigApi;

    @Autowired
    private BatchAttributeConfigRepository batchAttributeConfigRepository;

    @Autowired
    private BatchAttributeConfigTransfer batchAttributeConfigTransfer;

    @PostMapping("createOrUpdate")
    public Object createOrUpdate(@RequestBody @Valid BatchAttributeConfigDTO batchAttributeConfigDTO) {
        if (batchAttributeConfigDTO.getId() != null && batchAttributeConfigDTO.getId() > 0) {
            iBatchAttributeConfigApi.update(batchAttributeConfigDTO);
            return Response.success();
        }
        iBatchAttributeConfigApi.save(batchAttributeConfigDTO);
        return Response.success();
    }

    @GetMapping("{id}")
    public Object getById(@PathVariable Long id) {
        BatchAttributeConfig batchAttributeConfig = batchAttributeConfigRepository.findById(id);
        return Response.builder().data(batchAttributeConfigTransfer.toBatchAttributeConfigDTO(batchAttributeConfig)).build();
    }
}
