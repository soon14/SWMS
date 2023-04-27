package com.swms.wms.basic.work_station.presentation;

import com.swms.wms.api.basic.IWorkStationApi;
import com.swms.wms.api.basic.dto.WorkStationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/work-station")
public class WorkStationController {

    @Autowired
    private IWorkStationApi workStationApi;

    @PostMapping("save")
    public void save(WorkStationDTO workStationDTO) {
        workStationApi.save(workStationDTO);
    }

    @PostMapping("update")
    public void update(WorkStationDTO workStationDTO) {
        workStationApi.update(workStationDTO);
    }

    @PostMapping("enable")
    public void enable(String stationCode) {
        workStationApi.enable(stationCode);
    }

    @PostMapping("disable")
    public void disable(String stationCode) {
        workStationApi.disable(stationCode);
    }

    @PostMapping("delete")
    public void delete(String stationCode) {
        workStationApi.delete(stationCode);
    }

    @RequestMapping("getWorkStationList")
    public String getWorkStationList() {
        return "work-station";
    }
}
