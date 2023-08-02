package com.swms.wms.basic.container.controller;

import com.swms.common.utils.http.Response;
import com.swms.wms.api.basic.IContainerSpecApi;
import com.swms.wms.api.basic.dto.ContainerSpecDTO;
import com.swms.wms.basic.container.domain.repository.ContainerSpecRepository;
import com.swms.wms.basic.container.domain.transfer.ContainerSpecTransfer;
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
@RequestMapping("containerSpec")
@Validated
public class ContainerSpecController {

    @Autowired
    private IContainerSpecApi iContainerSpecApi;

    @Autowired
    private ContainerSpecRepository containerSpecRepository;

    @Autowired
    private ContainerSpecTransfer containerSpecTransfer;

    @PostMapping("createOrUpdate")
    public Object createOrUpdate(@RequestBody @Valid ContainerSpecDTO containerSpecDTO) {
        if (containerSpecDTO.getId() != null && containerSpecDTO.getId() > 0) {
            iContainerSpecApi.update(containerSpecDTO);
            return Response.success();
        }
        iContainerSpecApi.save(containerSpecDTO);
        return Response.success();
    }

    @GetMapping("{id}")
    public Object getById(@PathVariable Long id) {
        return containerSpecTransfer.toDTO(containerSpecRepository.findById(id));
    }
}
