package com.swms.wms.warehouse.work_station.presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/work-station")
public class WorkStationController {

    @RequestMapping("addWorkStation")
    public void addWorkStation() {

    }

    @RequestMapping("getWorkStationList")
    public String getWorkStationList() {
        return "work-station";
    }
}
