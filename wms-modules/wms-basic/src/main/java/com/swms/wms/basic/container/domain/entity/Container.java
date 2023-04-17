package com.swms.wms.basic.container.domain.entity;

import com.swms.wms.api.basic.constants.ContainerStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
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

    private List<ContainerSlot> containerSlots;

    public boolean canReceive() {
        return containerStatus == ContainerStatusEnum.CREATED
            || containerStatus == ContainerStatusEnum.OUT_SIDE;
    }

    public boolean canPutAway() {
        return containerStatus == ContainerStatusEnum.WAIT_PUT_AWAY;
    }

    @Data
    public static class ContainerSlot {

        private Long id;

        private String containerSlotCode;
        private String containerSlotSpecCode;

        private Long parentId;
        private String containerCode;

        private BigDecimal occupationRatio;

        // every container slot has a unique location code.
        private String locationCode;
    }
}
