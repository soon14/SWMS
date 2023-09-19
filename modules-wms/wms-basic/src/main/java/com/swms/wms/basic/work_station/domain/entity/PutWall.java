package com.swms.wms.basic.work_station.domain.entity;

import com.google.common.collect.Lists;
import com.swms.common.utils.exception.WmsException;
import com.swms.wms.api.basic.constants.PutWallSlotStatusEnum;
import com.swms.wms.api.basic.dto.PutWallDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Definition: SomeWhere that operators put items.
 */
@Data
@NoArgsConstructor
public class PutWall {

    private Long id;
    private Long workStationId;
    private String putWallCode;
    private String putWallName;
    private String containerSpecCode;
    private Long version;
    private List<PutWallDTO.PutWallSlot> putWallSlots;

    public PutWall(Long workStationId, String putWallCode, String putWallName, String containerSpecCode, @NotNull List<PutWallDTO.PutWallSlot> putWallSlots) {
        this.workStationId = workStationId;
        this.putWallCode = putWallCode;
        this.putWallName = putWallName;
        this.putWallSlots = putWallSlots;
        this.containerSpecCode = containerSpecCode;

        this.putWallSlots.forEach(v -> v.initPutWallSlot(this.putWallCode, this.workStationId));
    }

    private boolean deleted;

    private boolean enable;

    public void enable() {
        this.enable = true;
    }

    public void disable() {
        this.enable = false;
    }

    public void delete() {
        this.deleted = true;
    }

    public void bindContainer(String containerCode, String putWallSlotCode) {

        putWallSlots.stream().filter(v -> StringUtils.equals(v.getPutWallSlotCode(), putWallSlotCode)).forEach(v -> {

            if (v.getPutWallSlotStatus() != PutWallSlotStatusEnum.WAITING_BINDING) {
                throw new WmsException("PutWallSlot is not WAITING_BINDING, cannot bind container");
            }
            v.setTransferContainerCode(containerCode);
            v.setPutWallSlotStatus(PutWallSlotStatusEnum.BOUND);
        });
    }

    public void assignOrder(String putWallSlotCode, Long orderId) {

        putWallSlots.stream().filter(v -> StringUtils.equals(v.getPutWallSlotCode(), putWallSlotCode)).forEach(v -> {

            if (orderId == null) {
                throw new WmsException("assigned orderId can not be empty");
            }

            if (v.getPutWallSlotStatus() != PutWallSlotStatusEnum.IDLE) {
                throw new WmsException("put wall slot status is not IDLE,  can't assign order");
            }

            if (v.getOrderIds() == null) {
                v.setOrderIds(Lists.newArrayList(orderId));
            } else {
                v.getOrderIds().add(orderId);
            }
            v.setPutWallSlotStatus(PutWallSlotStatusEnum.WAITING_BINDING);
        });
    }
}
