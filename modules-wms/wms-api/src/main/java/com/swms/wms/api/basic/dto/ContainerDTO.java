package com.swms.wms.api.basic.dto;

import com.swms.wms.api.basic.constants.ContainerStatusEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContainerDTO {

    private Long id;

    @NotEmpty
    private String containerCode;

    @NotEmpty
    private String containerSpecCode;

    @NotEmpty
    private String warehouseCode;
    private String warehouseAreaCode;
    private String warehouseLogicCode;
    private String locationCode;
    private String locationType;

    private BigDecimal occupationRatio;

    private boolean emptyContainer;
    private boolean locked;
    private boolean opened;

    @NotNull
    @Min(1)
    private Integer containerSlotNum;

    @NotNull
    @Min(0)
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
            if (CollectionUtils.isNotEmpty(children)) {
                children.forEach(v -> v.setContainerSlotCode(containerCode));
            }
        }
    }
}
