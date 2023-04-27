package com.swms.wms.basic.work_station.application;

import com.swms.wms.api.basic.IWorkStationApi;
import com.swms.wms.api.basic.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.basic.dto.WorkStationDTO;
import com.swms.wms.basic.work_station.domain.entity.WorkStation;
import com.swms.wms.basic.work_station.domain.repository.WorkStationRepository;
import com.swms.wms.basic.work_station.domain.transfer.WorkStationTransfer;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class WorkStationApiImpl implements IWorkStationApi {

    @Autowired
    private WorkStationRepository workStationRepository;

    @Autowired
    private WorkStationTransfer workStationTransfer;

    @Override
    public void save(WorkStationDTO workStationDTO) {
        workStationRepository.save(workStationTransfer.toDO(workStationDTO));
    }

    @Override
    public void update(WorkStationDTO workStationDTO) {
        workStationRepository.save(workStationTransfer.toDO(workStationDTO));
    }

    @Override
    public void enable(String stationCode) {
        WorkStation workStation = workStationRepository.findByStationCode(stationCode);
        workStation.enable();
    }

    @Override
    public void disable(String stationCode) {
        WorkStation workStation = workStationRepository.findByStationCode(stationCode);
        workStation.disable();
    }

    @Override
    public void online(String stationCode, WorkStationOperationTypeEnum operationType) {
        WorkStation workStation = workStationRepository.findByStationCode(stationCode);
        workStation.online(operationType);
        workStationRepository.save(workStation);
    }

    @Override
    public void offline(String stationCode) {
        WorkStation workStation = workStationRepository.findByStationCode(stationCode);
        workStation.offline();
        workStationRepository.save(workStation);
    }

    @Override
    public void pause(String stationCode) {
        WorkStation workStation = workStationRepository.findByStationCode(stationCode);
        workStation.pause();
        workStationRepository.save(workStation);
    }

    @Override
    public void resume(String stationCode) {
        WorkStation workStation = workStationRepository.findByStationCode(stationCode);
        workStation.resume();
        workStationRepository.save(workStation);
    }

    @Override
    public WorkStationDTO queryWorkStation(String stationCode) {
        return workStationTransfer.toDTO(workStationRepository.findByStationCode(stationCode));
    }

}
