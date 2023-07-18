package com.swms.wms.basic.work_station.controller;

import com.swms.utils.http.Response;
import com.swms.wms.api.basic.IPutWallApi;
import com.swms.wms.api.basic.dto.CreatePutWallDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("putWall")
@RestController
@Validated
public class PutWallController {

    @Autowired
    private IPutWallApi iPutWallApi;

    @PostMapping("createOrUpdate")
    public Object create(@RequestBody @Valid CreatePutWallDTO createPutWallDTO) {
        if (createPutWallDTO.getId() != null && createPutWallDTO.getId() > 0) {
            iPutWallApi.update(createPutWallDTO);
            return Response.success();
        }
        iPutWallApi.create(createPutWallDTO);
        return Response.success();
    }

}
