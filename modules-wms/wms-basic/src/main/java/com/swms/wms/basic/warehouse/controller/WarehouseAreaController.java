package com.swms.wms.basic.warehouse.controller;

import com.swms.utils.http.Response;
import com.swms.wms.api.basic.IWarehouseAreaApi;
import com.swms.wms.api.basic.IWarehouseConfigApi;
import com.swms.wms.api.basic.dto.WarehouseAreaDTO;
import com.swms.wms.api.basic.dto.WarehouseConfigDTO;
import com.swms.wms.basic.warehouse.domain.entity.WarehouseArea;
import com.swms.wms.basic.warehouse.domain.entity.WarehouseConfig;
import com.swms.wms.basic.warehouse.domain.repository.WarehouseConfigRepository;
import com.swms.wms.basic.warehouse.domain.transfer.WarehouseConfigTransfer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("warehouseArea")
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
}
