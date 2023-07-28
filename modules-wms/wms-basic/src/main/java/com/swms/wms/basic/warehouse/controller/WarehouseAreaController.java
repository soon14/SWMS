package com.swms.wms.basic.warehouse.controller;

import com.swms.utils.http.Response;
import com.swms.wms.api.basic.IWarehouseAreaApi;
import com.swms.wms.api.basic.dto.WarehouseAreaDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("warehouseArea")
@Validated
public class WarehouseAreaController {

    @Autowired
    private IWarehouseAreaApi iWarehouseAreaApi;

    @PostMapping("createOrUpdate")
    public Object createOrUpdate(@RequestBody @Valid WarehouseAreaDTO warehouseAreaDTO) {
        if (warehouseAreaDTO.getId() != null && warehouseAreaDTO.getId() > 0) {
            iWarehouseAreaApi.update(warehouseAreaDTO);
            return Response.success();
        }
        iWarehouseAreaApi.save(warehouseAreaDTO);
        return Response.success();
    }

    @GetMapping("enable/{id}")
    public Object enable(@PathVariable Long id) {
        iWarehouseAreaApi.enable(id);
        return Response.success();
    }

    @GetMapping("disable/{id}")
    public Object disable(@PathVariable Long id) {
        iWarehouseAreaApi.disable(id);
        return Response.success();
    }

    @DeleteMapping("{id}")
    public Object delete(@PathVariable Long id) {
        iWarehouseAreaApi.delete(id);
        return Response.success();
    }
}
