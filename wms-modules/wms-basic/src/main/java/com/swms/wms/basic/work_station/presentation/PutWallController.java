package com.swms.wms.basic.work_station.presentation;

import com.swms.utils.http.Response;
import com.swms.wms.api.basic.IPutWallApi;
import com.swms.wms.api.basic.dto.PutWallDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/put-wall")
public class PutWallController {

    @Autowired
    private IPutWallApi putWallApi;

    @PostMapping("save")
    public void save(PutWallDTO putWallDTO) {
        putWallApi.save(putWallDTO);
    }

    @PostMapping("update")
    public void update(PutWallDTO putWallDTO) {
        putWallApi.update(putWallDTO);
    }

    @PostMapping("enable")
    public void enable(String putWallCode) {
        putWallApi.enable(putWallCode);
    }

    @PostMapping("disable")
    public void disable(String putWallCode) {
        putWallApi.disable(putWallCode);
    }

    @PostMapping("delete")
    public void delete(String putWallCode) {
        putWallApi.delete(putWallCode);
    }

    @PostMapping("list")
    public List<PutWallDTO> getPutWallSlots() {

        return null;
    }
}
