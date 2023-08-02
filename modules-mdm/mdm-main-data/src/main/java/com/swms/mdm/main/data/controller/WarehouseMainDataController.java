package com.swms.mdm.main.data.controller;

import com.swms.mdm.api.main.data.IWarehouseMainDataApi;
import com.swms.mdm.api.main.data.dto.WarehouseMainDataDTO;
import com.swms.mdm.main.data.controller.parameter.WarehouseMainDataVO;
import com.swms.mdm.main.data.domain.entity.WarehouseMainData;
import com.swms.mdm.main.data.domain.repository.WarehouseMainDataRepository;
import com.swms.mdm.main.data.domain.transfer.WarehouseMainDataTransfer;
import com.swms.common.utils.http.Response;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("warehouseMainData")
public class WarehouseMainDataController {

    @Autowired
    private IWarehouseMainDataApi iWarehouseApi;

    @Autowired
    private WarehouseMainDataRepository warehouseMainDataRepository;

    @Autowired
    private WarehouseMainDataTransfer warehouseMainDataTransfer;

    @PostMapping("createOrUpdate")
    public Object createOrUpdate(@RequestBody @Valid WarehouseMainDataVO warehouseMainDataVO) {
        WarehouseMainDataDTO warehouseMainDataDTO = new WarehouseMainDataDTO();
        BeanUtils.copyProperties(warehouseMainDataVO, warehouseMainDataDTO);

        if (warehouseMainDataDTO.getId() != null && warehouseMainDataDTO.getId() > 0) {
            iWarehouseApi.updateWarehouse(warehouseMainDataDTO);
            return Response.success();
        }
        iWarehouseApi.createWarehouse(warehouseMainDataDTO);
        return Response.success();
    }

    @PostMapping("getById")
    public Object getById(@RequestParam Long id) {
        WarehouseMainData warehouseMainData = warehouseMainDataRepository.findById(id);
        return Response.builder().data(warehouseMainDataTransfer.toDTO(warehouseMainData)).build();
    }
}
