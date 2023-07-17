package com.swms.wms.basic.work_station.infrastructure.repository.impl;

import com.swms.wms.api.basic.dto.PutWallDTO;
import com.swms.wms.basic.work_station.domain.entity.PutWall;
import com.swms.wms.basic.work_station.domain.repository.PutWallRepository;
import com.swms.wms.basic.work_station.infrastructure.persistence.mapper.PutWallPORepository;
import com.swms.wms.basic.work_station.infrastructure.persistence.mapper.PutWallSlotPORepository;
import com.swms.wms.basic.work_station.infrastructure.persistence.po.PutWallSlotPO;
import com.swms.wms.basic.work_station.infrastructure.persistence.transfer.PutWallPOTransfer;
import com.swms.wms.basic.work_station.infrastructure.persistence.transfer.PutWallSlotPOTransfer;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PutWallRepositoryImpl implements PutWallRepository {

    @Autowired
    private PutWallPORepository putWallPORepository;

    @Autowired
    private PutWallSlotPORepository putWallSlotPORepository;

    @Autowired
    private PutWallPOTransfer putWallPOTransfer;

    @Autowired
    private PutWallSlotPOTransfer putWallSlotPOTransfer;

    @Override
    public void save(PutWall putWall) {
        putWallPORepository.save(putWallPOTransfer.toPO(putWall));
        if (CollectionUtils.isNotEmpty(putWall.getPutWallSlots())) {
            putWallSlotPORepository.saveAll(putWallSlotPOTransfer.toPOS(putWall.getPutWallSlots()));
        }
    }

    @Override
    public List<PutWallDTO.PutWallSlot> getPutWallSlotsByWorkStationId(Long workStationId) {
        List<PutWallSlotPO> putWallSlotPOS = putWallSlotPORepository.findByWorkStationId(workStationId);
        return putWallSlotPOTransfer.toPutWallSlots(putWallSlotPOS);
    }

    @Override
    public PutWall findById(Long putWallId) {
        return putWallPOTransfer.toDO(putWallPORepository.findById(putWallId).orElseThrow());
    }

    @Override
    public List<PutWallDTO.PutWallSlot> findByPutWallSlotCodeIn(List<String> putWallSlotCodes, @NotNull Long workStationId) {
        return putWallSlotPOTransfer.toPutWallSlots(putWallSlotPORepository
            .findByPutWallSlotCodeInAndWorkStationId(putWallSlotCodes, workStationId));
    }

    @Override
    public void saveAll(List<PutWallDTO.PutWallSlot> putWallSlots) {
        putWallSlotPORepository.saveAll(putWallSlotPOTransfer.toPOS(putWallSlots));
    }

    @Override
    public PutWallDTO.PutWallSlot findByPutWallSlotCode(String putWallSlotCode, @NotNull Long workStationId) {
        return putWallSlotPOTransfer.toPutWallSlot(putWallSlotPORepository
            .findByPutWallSlotCodeAndWorkStationId(putWallSlotCode, workStationId));
    }

    @Override
    public void save(PutWallDTO.PutWallSlot putWallSlot) {
        putWallSlotPORepository.save(putWallSlotPOTransfer.toPO(putWallSlot));
    }

}
