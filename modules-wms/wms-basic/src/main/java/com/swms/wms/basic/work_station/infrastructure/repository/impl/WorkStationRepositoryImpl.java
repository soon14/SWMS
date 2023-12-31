package com.swms.wms.basic.work_station.infrastructure.repository.impl;

import com.swms.wms.basic.work_station.domain.entity.WorkStation;
import com.swms.wms.basic.work_station.domain.repository.WorkStationRepository;
import com.swms.wms.basic.work_station.infrastructure.persistence.mapper.WorkStationPORepository;
import com.swms.wms.basic.work_station.infrastructure.persistence.po.WorkStationPO;
import com.swms.wms.basic.work_station.infrastructure.persistence.transfer.WorkStationPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkStationRepositoryImpl implements WorkStationRepository {

    @Autowired
    private WorkStationPORepository workStationPORepository;

    @Autowired
    private WorkStationPOTransfer workStationPOTransfer;

    @Override
    public WorkStation findById(Long id) {
        return workStationPOTransfer.toDO(workStationPORepository.findById(id).orElseThrow());
    }

    @Override
    public List<WorkStation> findByWarehouseCode(String warehouseCode) {
        List<WorkStationPO> workStationPOS = workStationPORepository.findByWarehouseCode(warehouseCode);
        return workStationPOTransfer.toDOs(workStationPOS);
    }

    @Override
    public void save(WorkStation workStation) {
        workStationPORepository.save(workStationPOTransfer.toPO(workStation));
    }
}
