package com.swms.wms.basic.work_station.domain.service;

import com.swms.wms.basic.work_station.domain.entity.WorkLocation;
import com.swms.wms.basic.work_station.domain.repository.IWorkLocationRepository;
import com.swms.wms.basic.work_station.domain.repository.IWorkLocationSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkLocationService {

    @Autowired
    private IWorkLocationRepository iWorkLocationRepository;

    @Autowired
    private IWorkLocationSlotRepository iWorkLocationSlotRepository;

    public void addWorkLocation(WorkLocation workLocation) {
        iWorkLocationRepository.save(workLocation);
        iWorkLocationSlotRepository.saveAll(workLocation.getWorkLocationSlots());
    }

    public void updateWorkLocation(WorkLocation workLocation) {
        iWorkLocationRepository.update(workLocation);
        iWorkLocationSlotRepository.updateAll(workLocation.getWorkLocationSlots());
    }

    public List<WorkLocation> getWorkLocationsByStationCode(String stationCode) {
        return iWorkLocationRepository.findByStationCode(stationCode);
    }

    public void removeWorkLocation(Long id) {
        iWorkLocationRepository.deleteById(id);
    }
}
