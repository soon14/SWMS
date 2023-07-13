package com.swms.wms.basic.warehouse.controller;

import com.swms.utils.http.Response;
import com.swms.wms.api.basic.IWarehouseAreaGroupApi;
import com.swms.wms.api.basic.dto.WarehouseAreaGroupDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("warehouseAreaGroup")
@Validated
public class WarehouseAreaGroupController {

    @Autowired
    private IWarehouseAreaGroupApi iWarehouseAreaGroupApi;

    @PostMapping("createOrUpdate")
    public Object createOrUpdate(@RequestBody @Valid WarehouseAreaGroupDTO warehouseAreaGroupDTO) {
        if (warehouseAreaGroupDTO.getId() != null && warehouseAreaGroupDTO.getId() > 0) {
            iWarehouseAreaGroupApi.update(warehouseAreaGroupDTO);
            return Response.success();
        }
        iWarehouseAreaGroupApi.save(warehouseAreaGroupDTO);
        return Response.success();
    }
}
