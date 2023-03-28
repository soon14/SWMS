package com.swms.wms.warehouse.work_station.domain.service;

import com.swms.wms.api.task.dto.BindContainerDTO;
import com.swms.wms.api.warehouse.constants.PutWallSlotStatusEnum;
import com.swms.wms.api.warehouse.dto.AssignOrdersDTO;
import com.swms.wms.api.warehouse.dto.PutWallSlotDTO;
import com.swms.wms.warehouse.work_station.domain.entity.PutWall;
import com.swms.wms.warehouse.work_station.domain.repository.IPutWallRepository;
import com.swms.wms.warehouse.work_station.domain.repository.IPutWallSlotRepository;
import org.apache.commons.collections4.CollectionUtils;
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

    public List<PutWallSlotDTO> getPutWallSlotsByStationCode(String stationCode) {
        return iPutWallSlotRepository.findAllPutWallSlotsByStationCode(stationCode);
    }

    public void assignOrders(List<AssignOrdersDTO> assignOrdersDTOS) {
        if (CollectionUtils.isEmpty(assignOrdersDTOS)) {
            return;
        }

        iPutWallRepository.assignOrders(assignOrdersDTOS);
    }

    public void appendOrders(List<AssignOrdersDTO> assignOrdersDTOS) {
        if (CollectionUtils.isEmpty(assignOrdersDTOS)) {
            return;
        }
        //TODO

//        for (AssignOrdersDTO assignOrdersDTO : assignOrdersDTOS) {
//            PutWallSlot putWallSlot = iPutWallSlotRepository.findByStationCodeAndSlotCode(assignOrdersDTO.getStationCode(), assignOrdersDTO.getPutWallSlotCode());
//            if (CollectionUtils.isNotEmpty(putWallSlot.getOrders())) {
//                throw new WmsException(PUT_WALL_SLOT_ORDERS_EXIST);
//            }
//        }
//
//        iPutWallRepository.assignOrders(assignOrdersDTOS);
    }

    public void bindContainer(BindContainerDTO bindContainerDTO) {
        iPutWallSlotRepository.updateTransferContainerAndStatus(bindContainerDTO, PutWallSlotStatusEnum.BOUND);
    }
}
