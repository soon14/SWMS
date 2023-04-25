package com.swms.wms.api.basic.dto;

import com.swms.wms.api.basic.constants.ContainerStatusEnum;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ContainerDTO {

    private Long id;

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

    @Data
    public static class ContainerSlot {

        // containerCode + containerSlotCode
        private String containerSlotCode;
        private String containerSlotSpecCode;

        private BigDecimal occupationRatio;

        // every container slot has a unique location code.
        private String locationCode;

        private List<ContainerSlot> children;

        public void setContainerSlotCode(String containerCode) {
            this.containerSlotCode = containerCode + "-" + this.containerSlotSpecCode;
        }
    }
}
