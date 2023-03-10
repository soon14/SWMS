package com.swms.wms.warehouse.work_station.domain.service;

import com.swms.wms.warehouse.work_station.domain.entity.PutWall;
import com.swms.wms.warehouse.work_station.infrastructure.repository.IPutWallRepository;
import com.swms.wms.warehouse.work_station.infrastructure.repository.IPutWallSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PutWallService {

    @Autowired
    private IPutWallRepository iPutWallRepository;

    @Autowired
    private IPutWallSlotRepository iPutWallSlotRepository;

    public void addPutWall(PutWall putWall) {
        iPutWallRepository.save(putWall);
        iPutWallSlotRepository.saveAll(putWall.getPutWallSlots());
    }

    public void updatePutWall(PutWall putWall) {
        iPutWallRepository.update(putWall);
        iPutWallSlotRepository.updateAll(putWall.getPutWallSlots());
    }

    public List<PutWall> getPutWallsByStationCode(String stationCode) {
        return iPutWallRepository.findByStationCode(stationCode);
    }
}
