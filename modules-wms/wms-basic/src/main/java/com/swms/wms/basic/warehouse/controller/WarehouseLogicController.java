package com.swms.wms.basic.warehouse.controller;

import com.swms.common.utils.http.Response;
import com.swms.wms.api.basic.IWarehouseLogicApi;
import com.swms.wms.api.basic.dto.WarehouseLogicDTO;
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
@RequestMapping("warehouseLogic")
@Validated
public class WarehouseLogicController {

    @Autowired
    private IWarehouseLogicApi iWarehouseLogicApi;

    @PostMapping("createOrUpdate")
    public Object createOrUpdate(@RequestBody @Valid WarehouseLogicDTO warehouseLogicDTO) {
        if (warehouseLogicDTO.getId() != null && warehouseLogicDTO.getId() > 0) {
            iWarehouseLogicApi.update(warehouseLogicDTO);
            return Response.success();
        }
        iWarehouseLogicApi.save(warehouseLogicDTO);
        return Response.success();
    }

    @GetMapping("enable/{id}")
    public Object enable(@PathVariable Long id) {
        iWarehouseLogicApi.enable(id);
        return Response.success();
    }

    @GetMapping("disable/{id}")
    public Object disable(@PathVariable Long id) {
        iWarehouseLogicApi.disable(id);
        return Response.success();
    }

    @GetMapping("delete/{id}")
    public Object delete(@PathVariable Long id) {
        iWarehouseLogicApi.delete(id);
        return Response.success();
    }
}
