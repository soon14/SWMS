package com.swms.mdm.config.controller;

import com.swms.mdm.api.config.IParameterConfigApi;
import com.swms.mdm.api.config.dto.ParameterConfigDTO;
import com.swms.mdm.config.domain.entity.ParameterConfig;
import com.swms.mdm.config.domain.repository.ParameterConfigRepository;
import com.swms.mdm.config.domain.transfer.ParameterConfigTransfer;
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
@RequestMapping("parameterConfig")
@Validated
public class ParameterConfigController {

    @Autowired
    private IParameterConfigApi iParameterConfigApi;

    @Autowired
    private ParameterConfigRepository parameterConfigRepository;

    @Autowired
    private ParameterConfigTransfer parameterConfigTransfer;

    @PostMapping("createOrUpdate")
    public Object createOrUpdate(@RequestBody @Valid ParameterConfigDTO parameterConfigDTO) {
        if (parameterConfigDTO.getId() != null && parameterConfigDTO.getId() > 0) {
            iParameterConfigApi.update(parameterConfigDTO);
            return Response.success();
        }
        iParameterConfigApi.save(parameterConfigDTO);
        return Response.success();
    }

    @GetMapping("{id}")
    public Object getById(@PathVariable Long id) {
        ParameterConfig parameterConfig = parameterConfigRepository.findById(id);
        return Response.builder().data(parameterConfigTransfer.toDTO(parameterConfig)).build();
    }
}
