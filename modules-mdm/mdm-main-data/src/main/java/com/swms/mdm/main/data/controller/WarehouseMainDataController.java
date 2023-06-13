package com.swms.mdm.main.data.controller;

import com.swms.mdm.api.main.data.IWarehouseApi;
import com.swms.mdm.api.main.data.dto.WarehouseMainDataDTO;
import com.swms.utils.http.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("warehouseMainData")
public class WarehouseMainDataController {

    @Autowired
    private IWarehouseApi iWarehouseApi;

    @PostMapping("createOrUpdate")
    public Object createOrUpdate(@RequestBody @Valid WarehouseMainDataDTO warehouseMainDataDTO) {
        if (warehouseMainDataDTO.getId() != null && warehouseMainDataDTO.getId() > 0) {
            iWarehouseApi.updateWarehouse(warehouseMainDataDTO);
            return Response.success();
        }
        iWarehouseApi.createWarehouse(warehouseMainDataDTO);
        return Response.success();
    }

}
