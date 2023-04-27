package com.swms.wms.basic.work_station.application;

import com.swms.utils.validate.ValidationSequence;
import com.swms.wms.api.basic.IPutWallApi;
import com.swms.wms.api.basic.dto.AssignOrdersDTO;
import com.swms.wms.api.basic.dto.PutWallDTO;
import com.swms.wms.api.basic.dto.ReleasePutWallSlotsDTO;
import com.swms.wms.api.task.dto.BindContainerDTO;
import com.swms.wms.basic.work_station.domain.repository.PutWallRepository;
import com.swms.wms.basic.work_station.domain.transfer.PutWallTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated(value = ValidationSequence.class)
@Service
public class PutWallApiImpl implements IPutWallApi {

    @Autowired
    private PutWallRepository putWallRepository;

    @Autowired
    private PutWallTransfer putWallTransfer;

    @Override
    public void save(PutWallDTO putWallDTO) {
        putWallRepository.save(putWallTransfer.toDO(putWallDTO));
    }

    @Override
    public void update(PutWallDTO putWallDTO) {
        putWallRepository.save(putWallTransfer.toDO(putWallDTO));
    }


    @Override
    public List<PutWallDTO.PutWallSlot> getPutWallSlots(String stationCode) {
        return putWallRepository.getPutWallSlotsByStationCode(stationCode);
    }

    @Override
    public void assignOrders(List<AssignOrdersDTO> assignOrdersDTOS) {
    }

    @Override
    public void appendOrders(List<AssignOrdersDTO> assignOrdersDTOS) {
    }

    @Override
    public void releasePutWallSlots(List<ReleasePutWallSlotsDTO> releasePutWallSlotsDTOS) {

    }

    @Override
    public void bindContainer(BindContainerDTO bindContainerDTO) {
    }
}
