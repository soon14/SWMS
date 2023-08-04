package com.swms.station.domain.service.impl;

import com.swms.station.domain.persistence.entity.WorkStation;
import com.swms.station.domain.persistence.repository.WorkStationRepository;
import com.swms.station.domain.service.WorkStationService;
import com.swms.station.remote.RemoteWorkStationService;
import com.swms.wms.api.basic.dto.WorkStationDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkStationServiceImpl implements WorkStationService {

    @Autowired
    private WorkStationRepository workStationRepository;

    @Autowired
    private RemoteWorkStationService remoteWorkStationService;

    public synchronized WorkStation initWorkStation(Long workStationId) {

        WorkStationDTO workStationDTO = remoteWorkStationService.queryWorkStation(workStationId);
        WorkStation workStation = new WorkStation();
        BeanUtils.copyProperties(workStationDTO, workStation);

        workStationRepository.deleteById(workStationId);
        workStationRepository.save(workStation);

        return workStation;
    }

    public WorkStation getWorkStation(Long workStationId) {
        return workStationRepository.findById(workStationId).orElse(null);
    }

    public void save(WorkStation workStation) {
        workStationRepository.save(workStation);
    }

    public void delete(WorkStation workStation) {
        workStationRepository.delete(workStation);
    }
}
