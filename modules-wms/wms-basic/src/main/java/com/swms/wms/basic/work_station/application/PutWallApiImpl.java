package com.swms.wms.basic.work_station.application;

import com.swms.utils.exception.WmsException;
import com.swms.utils.validate.ValidationSequence;
import com.swms.wms.api.basic.IContainerSpecApi;
import com.swms.wms.api.basic.IPutWallApi;
import com.swms.wms.api.basic.dto.AssignOrdersDTO;
import com.swms.wms.api.basic.dto.ContainerSpecDTO;
import com.swms.wms.api.basic.dto.CreatePutWallDTO;
import com.swms.wms.api.basic.dto.PutWallDTO;
import com.swms.wms.api.basic.dto.ReleasePutWallSlotsDTO;
import com.swms.wms.api.task.dto.BindContainerDTO;
import com.swms.wms.basic.work_station.domain.entity.PutWall;
import com.swms.wms.basic.work_station.domain.repository.PutWallRepository;
import com.swms.wms.basic.work_station.domain.service.PutWallService;
import com.swms.wms.basic.work_station.domain.transfer.PutWallTransfer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Validated(value = ValidationSequence.class)
@Service
public class PutWallApiImpl implements IPutWallApi {

    @Autowired
    private PutWallRepository putWallRepository;

    @Autowired
    private PutWallTransfer putWallTransfer;

    @Autowired
    private IContainerSpecApi iContainerSpecApi;

    @Autowired
    private PutWallService putWallService;

    @Override
    public void save(PutWallDTO putWallDTO) {
        putWallRepository.save(putWallTransfer.toDO(putWallDTO));
    }

    @Override
    public void update(PutWallDTO putWallDTO) {
        putWallRepository.save(putWallTransfer.toDO(putWallDTO));
    }

    @Override
    public void enable(Long putWallId) {
        PutWall putWall = putWallRepository.findById(putWallId);
        putWall.enable();
        putWallRepository.save(putWall);
    }

    @Override
    public void disable(Long putWallId) {
        PutWall putWall = putWallRepository.findById(putWallId);

        if (!putWallService.checkDisablePutWall(putWall)) {
            throw new WmsException("Could not disable");
        }

        putWall.disable();
        putWallRepository.save(putWall);
    }

    @Override
    public void delete(Long putWallId) {
        PutWall putWall = putWallRepository.findById(putWallId);
        putWall.delete();
        putWallRepository.save(putWall);
    }

    @Override
    public void assignOrders(List<AssignOrdersDTO> assignOrdersDTOS) {

        List<PutWallDTO.PutWallSlot> putWallSlots = assignOrdersDTOS.stream().flatMap(v -> {
            List<String> putWallSlotCodes = v.getAssignDetails().stream()
                .map(AssignOrdersDTO.AssignDetail::getPutWallSlotCode).toList();
            return putWallRepository.findByPutWallSlotCodeIn(putWallSlotCodes, v.getWorkStationId()).stream();
        }).toList();

        Map<Long, AssignOrdersDTO> assignOrdersDTOMap = assignOrdersDTOS.stream()
            .collect(Collectors.toMap(AssignOrdersDTO::getWorkStationId, v -> v));

        putWallSlots.forEach(putWallSlot -> {
            AssignOrdersDTO assignOrdersDTO = assignOrdersDTOMap.get(putWallSlot.getWorkStationId());
            AssignOrdersDTO.AssignDetail assignDetail = assignOrdersDTO.getAssignDetails().stream()
                .filter(v -> StringUtils.equals(v.getPutWallSlotCode(), putWallSlot.getPutWallSlotCode())).findFirst().orElseThrow();
            putWallSlot.assignOrders(assignDetail.getOrderIds());
        });
        putWallRepository.saveAll(putWallSlots);
    }

    @Override
    public void appendOrders(List<AssignOrdersDTO> assignOrdersDTOS) {

        List<PutWallDTO.PutWallSlot> putWallSlots = assignOrdersDTOS.stream().flatMap(v -> {
            List<String> putWallSlotCodes = v.getAssignDetails().stream()
                .map(AssignOrdersDTO.AssignDetail::getPutWallSlotCode).toList();
            return putWallRepository.findByPutWallSlotCodeIn(putWallSlotCodes, v.getWorkStationId()).stream();
        }).toList();

        Map<Long, AssignOrdersDTO> assignOrdersDTOMap = assignOrdersDTOS.stream()
            .collect(Collectors.toMap(AssignOrdersDTO::getWorkStationId, v -> v));

        putWallSlots.forEach(putWallSlot -> {
            AssignOrdersDTO assignOrdersDTO = assignOrdersDTOMap.get(putWallSlot.getWorkStationId());
            AssignOrdersDTO.AssignDetail assignDetail = assignOrdersDTO.getAssignDetails().stream()
                .filter(v -> StringUtils.equals(v.getPutWallSlotCode(), putWallSlot.getPutWallSlotCode())).findFirst().orElseThrow();
            putWallSlot.assignOrders(assignDetail.getOrderIds());
        });
        putWallRepository.saveAll(putWallSlots);
    }

    @Override
    public void releasePutWallSlots(List<ReleasePutWallSlotsDTO> releasePutWallSlotsDTOS) {

        List<PutWallDTO.PutWallSlot> putWallSlots = releasePutWallSlotsDTOS.stream()
            .flatMap(v -> putWallRepository.findByPutWallSlotCodeIn(v.getPutWallSlotCodes(), v.getWorkStationId()).stream())
            .toList();

        putWallSlots.forEach(PutWallDTO.PutWallSlot::release);
        putWallRepository.saveAll(putWallSlots);
    }

    @Override
    public void bindContainer(BindContainerDTO bindContainerDTO) {
        PutWallDTO.PutWallSlot putWallSlot = putWallRepository.findByPutWallSlotCode(bindContainerDTO.getPutWallSlotCode()
            , bindContainerDTO.getWorkStationId());
        putWallSlot.bindContainer(bindContainerDTO.getContainerCode());
        putWallRepository.save(putWallSlot);
    }

    @Override
    public void create(CreatePutWallDTO createPutWallDTO) {
        ContainerSpecDTO containerSpecDTO = iContainerSpecApi
            .getContainerSpecDTO(createPutWallDTO.getContainerSpecCode(), createPutWallDTO.getWarehouseCode());

        List<PutWallDTO.PutWallSlot> putWallSlots = putWallTransfer.toPutWallSlots(containerSpecDTO.getContainerSlotSpecs());
        PutWall putWall = new PutWall(createPutWallDTO.getWorkStationId(), createPutWallDTO.getPutWallCode(),
            createPutWallDTO.getPutWallName(), createPutWallDTO.getContainerSpecCode(), putWallSlots);
        putWallRepository.save(putWall);
    }

    @Override
    public void update(CreatePutWallDTO createPutWallDTO) {
        PutWall putWall = putWallRepository.findById(createPutWallDTO.getId());
        if (!putWallService.checkUpdatePutWall(putWall)) {
            throw new WmsException("Could not update");
        }

        //remove all putWallSlot
        putWallRepository.deletePutWallSlots(putWall.getPutWallSlots());

        ContainerSpecDTO containerSpecDTO = iContainerSpecApi
            .getContainerSpecDTO(createPutWallDTO.getContainerSpecCode(), createPutWallDTO.getWarehouseCode());
        List<PutWallDTO.PutWallSlot> putWallSlots = putWallTransfer.toPutWallSlots(containerSpecDTO.getContainerSlotSpecs());
        putWallSlots.forEach(v -> v.initPutWallSlot(putWall.getPutWallCode(), putWall.getWorkStationId()));

        putWall.setPutWallSlots(putWallSlots);
        putWallTransfer.updateTarget(createPutWallDTO, putWall);

        //create new PutWall
        putWallRepository.save(putWall);
    }
}
