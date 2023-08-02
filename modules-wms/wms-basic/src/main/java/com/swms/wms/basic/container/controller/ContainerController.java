package com.swms.wms.basic.container.controller;

import com.swms.common.utils.http.Response;
import com.swms.wms.api.basic.IContainerApi;
import com.swms.wms.api.basic.dto.CreateContainerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("container")
@Validated
public class ContainerController {

    @Autowired
    private IContainerApi iContainerApi;

    @PostMapping("create")
    public Object create(@RequestBody CreateContainerDTO createContainerDTO) {
        iContainerApi.createContainer(createContainerDTO);
        return Response.success();
    }
}
