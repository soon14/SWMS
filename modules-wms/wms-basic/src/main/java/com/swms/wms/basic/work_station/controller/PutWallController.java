package com.swms.wms.basic.work_station.controller;

import com.swms.utils.http.Response;
import com.swms.wms.api.basic.IPutWallApi;
import com.swms.wms.api.basic.IWorkStationApi;
import com.swms.wms.api.basic.dto.CreatePutWallDTO;
import com.swms.wms.api.basic.dto.WorkStationDTO;
import com.swms.wms.basic.work_station.controller.parameter.WorkStationVO;
import com.swms.wms.basic.work_station.domain.entity.WorkStation;
import com.swms.wms.basic.work_station.domain.repository.WorkStationRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private WorkStationRepository workStationRepository;

    @PostMapping("create")
    public Object create(@RequestBody @Valid CreatePutWallDTO createPutWallDTO) {
        iPutWallApi.create(createPutWallDTO);
        return Response.success();
    }

    @GetMapping("{stationCode}")
    public Object getByStationCode(@PathVariable String stationCode) {
        WorkStation workStation = workStationRepository.findByStationCode(stationCode);
        WorkStationVO workStationVO = new WorkStationVO();
        BeanUtils.copyProperties(workStation, workStationVO);
        workStationVO.flatWorkLocation();
        return Response.builder().data(workStationVO).build();
    }
}
