package com.swms.wms.basic.container.domain.entity;

import static com.swms.utils.exception.code_enum.BasicErrorDescEnum.CONTAINER_SPECIFIC_CANNOT_CHANGE;

import com.swms.utils.exception.WmsException;
import com.swms.wms.api.basic.constants.ContainerStatusEnum;
import com.swms.wms.api.basic.dto.ContainerDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;

@Data
@Validated
public class Container {

    private Long id;

    //unique identifier
    private String containerCode;

    private String containerSpecCode;

    private String warehouseCode;
    private String warehouseAreaCode;
    private String warehouseLogicCode;
    private String locationCode;
    private String locationType;

    private BigDecimal occupationRatio;

    private boolean empty;
    private boolean lock;

    /**
     * container is or not open
     */
    private boolean open;

    private Integer emptySlotNum;

    private ContainerStatusEnum containerStatus;

    private Long version;

    private List<ContainerDTO.ContainerSlot> containerSlots;

    public Container(String containerCode, String containerSpecCode, @NotNull List<ContainerDTO.ContainerSlot> containerSlots) {
        this.containerSlots = containerSlots;
        this.containerCode = containerCode;
        this.containerSpecCode = containerSpecCode;
        this.containerStatus = ContainerStatusEnum.CREATED;
        this.empty = true;
        this.emptySlotNum = containerSlots.size();
        this.occupationRatio = BigDecimal.ZERO;

        this.containerSlots.forEach(v -> v.setContainerSlotCode(this.containerCode));
    }

    public boolean canReceive() {
        return containerStatus == ContainerStatusEnum.CREATED
            || containerStatus == ContainerStatusEnum.OUT_SIDE;
    }

    public boolean canPutAway() {
        return containerStatus == ContainerStatusEnum.WAIT_PUT_AWAY;
    }

    public void changeContainerSpec(String containerSpecCode, List<ContainerDTO.ContainerSlot> toContainerSlots) {
        if (containerStatus != ContainerStatusEnum.OUT_SIDE && containerStatus != ContainerStatusEnum.CREATED) {
            throw new WmsException(CONTAINER_SPECIFIC_CANNOT_CHANGE);
        }

        this.containerSpecCode = containerSpecCode;
        this.containerSlots = toContainerSlots;
        this.containerSlots.forEach(v -> v.setContainerSlotCode(this.containerCode));
    }

}
