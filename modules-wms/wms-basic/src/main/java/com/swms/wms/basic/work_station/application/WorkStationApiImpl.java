package com.swms.wms.basic.work_station.application;

import com.swms.wms.api.basic.IWorkStationApi;
import com.swms.wms.api.basic.constants.WorkStationOperationTypeEnum;
import com.swms.wms.api.basic.dto.WorkStationDTO;
import com.swms.wms.basic.work_station.domain.entity.WorkStation;
import com.swms.wms.basic.work_station.domain.repository.WorkStationRepository;
import com.swms.wms.basic.work_station.domain.transfer.WorkStationTransfer;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
    public void enable(Long id) {
        WorkStation workStation = workStationRepository.findById(id);
        workStation.enable();
        workStationRepository.save(workStation);
    }

    @Override
    public void disable(Long id) {
        WorkStation workStation = workStationRepository.findById(id);
        workStation.disable();
        workStationRepository.save(workStation);
    }

    @Override
    public void delete(Long id) {
        WorkStation workStation = workStationRepository.findById(id);
        workStation.delete();
        workStationRepository.save(workStation);
    }

    @Override
    public void online(Long id, WorkStationOperationTypeEnum operationType) {
        WorkStation workStation = workStationRepository.findById(id);
        workStation.online(operationType);
        workStationRepository.save(workStation);
    }

    @Override
    public void offline(Long id) {
        WorkStation workStation = workStationRepository.findById(id);
        workStation.offline();
        workStationRepository.save(workStation);
    }

    @Override
    public void pause(Long id) {
        WorkStation workStation = workStationRepository.findById(id);
        workStation.pause();
        workStationRepository.save(workStation);
    }

    @Override
    public void resume(Long id) {
        WorkStation workStation = workStationRepository.findById(id);
        workStation.resume();
        workStationRepository.save(workStation);
    }

    @Override
    public WorkStationDTO queryWorkStation(Long id) {
        return workStationTransfer.toDTO(workStationRepository.findById(id));
    }

}
