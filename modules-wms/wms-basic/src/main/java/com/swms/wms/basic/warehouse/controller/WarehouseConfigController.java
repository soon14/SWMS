package com.swms.wms.basic.warehouse.controller;

import com.swms.utils.http.Response;
import com.swms.wms.api.basic.IWarehouseConfigApi;
import com.swms.wms.api.basic.dto.WarehouseConfigDTO;
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
@RequestMapping("warehouseConfig")
public class WarehouseConfigController {

    @Autowired
    private IWarehouseConfigApi iWarehouseConfigApi;

    @Autowired
    private WarehouseConfigRepository warehouseConfigRepository;

    @Autowired
    private WarehouseConfigTransfer warehouseConfigTransfer;

    @PostMapping("createOrUpdate")
    public Object createOrUpdate(@RequestBody @Valid WarehouseConfigDTO warehouseConfigDTO) {
        if (warehouseConfigDTO.getId() != null && warehouseConfigDTO.getId() > 0) {
            iWarehouseConfigApi.update(warehouseConfigDTO);
            return Response.success();
        }
        iWarehouseConfigApi.save(warehouseConfigDTO);
        return Response.success();
    }

    @GetMapping("{code}")
    public Object getById(@PathVariable String code) {
        WarehouseConfig warehouseConfig = warehouseConfigRepository.findByWarehouseCode(code);
        return Response.builder().data(warehouseConfigTransfer.toWarehouseConfigDTO(warehouseConfig)).build();
    }
}
