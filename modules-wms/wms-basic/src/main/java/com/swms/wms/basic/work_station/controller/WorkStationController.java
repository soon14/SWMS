package com.swms.wms.basic.work_station.controller;

import com.swms.common.utils.http.Response;
import com.swms.wms.api.basic.IWorkStationApi;
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

@RequestMapping("workStation")
@RestController
@Validated
public class WorkStationController {

    @Autowired
    private IWorkStationApi iWorkStationApi;

    @Autowired
    private WorkStationRepository workStationRepository;

    @PostMapping("createOrUpdate")
    public Object createOrUpdate(@RequestBody @Valid WorkStationVO workStationVO) {

        WorkStationDTO workStationDTO = new WorkStationDTO();
        BeanUtils.copyProperties(workStationVO, workStationDTO);

        if (workStationDTO.getId() != null && workStationDTO.getId() > 0) {
            iWorkStationApi.update(workStationDTO);
            return Response.success();
        }
        iWorkStationApi.save(workStationDTO);
        return Response.success();
    }

    @GetMapping("{id}")
    public Object getByStationCode(@PathVariable Long id) {
        WorkStation workStation = workStationRepository.findById(id);
        WorkStationVO workStationVO = new WorkStationVO();
        BeanUtils.copyProperties(workStation, workStationVO);
        workStationVO.flatWorkLocation();
        return Response.builder().data(workStationVO).build();
    }
}
