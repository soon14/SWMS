package com.swms.wms.api.basic.dto;

import com.google.common.collect.Lists;
import com.swms.utils.validate.IValidate;
import com.swms.wms.api.basic.constants.PutWallSlotStatusEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PutWallDTO implements IValidate {

    private Long id;

    @NotEmpty
    private String stationCode;
    @NotEmpty
    private String putWallCode;
    @NotEmpty
    private String putWallName;
    @NotEmpty
    private String containerSpecCode;
    @NotEmpty
    private List<PutWallSlot> putWallSlots;

    @Override
    public boolean validate() {
        return putWallSlots.stream().map(PutWallSlot::getPutWallSlotCode).distinct().toList().size() == putWallSlots.size();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PutWallSlot {

        private Long id;
        @NotEmpty
        private String stationCode;
        @NotEmpty
        private String putWallCode;
        @NotEmpty
        private String putWallSlotCode;

        @NotNull
        @Min(value = 1)
        private Integer level;
        @Min(value = 1)
        @NotNull
        private Integer bay;
        private boolean enable;

        private List<Long> orderIds;
        private PutWallSlotStatusEnum putWallSlotStatus;
        private String transferContainerCode;

        private Long version;

        public void assignOrders(List<Long> orderIds) {

            if (CollectionUtils.isNotEmpty(this.orderIds)) {
                throw new RuntimeException("PutWallSlot is not empty");
            }

            if (this.putWallSlotStatus != PutWallSlotStatusEnum.IDLE) {
                throw new RuntimeException("PutWallSlot is not IDLE");
            }

            this.orderIds = orderIds;
            this.putWallSlotStatus = PutWallSlotStatusEnum.WAITING_BINDING;
        }

        public void appendOrders(List<Long> orderIds) {

            if (CollectionUtils.isEmpty(this.orderIds)) {
                throw new RuntimeException("PutWallSlot is empty");
            }

            if (this.putWallSlotStatus == PutWallSlotStatusEnum.IDLE) {
                throw new RuntimeException("PutWallSlot is IDLE, cannot append orders");
            }

            this.orderIds.addAll(orderIds);
        }

        public void release() {
            if (CollectionUtils.isEmpty(this.orderIds)) {
                throw new RuntimeException("PutWallSlot is empty");
            }

            if (this.putWallSlotStatus != PutWallSlotStatusEnum.WAITING_SEAL) {
                throw new RuntimeException("PutWallSlot is not WAITING_SEAL, cannot release");
            }

            this.orderIds = Lists.newArrayList();
            this.putWallSlotStatus = PutWallSlotStatusEnum.IDLE;
        }

        public void bindContainer(String containerCode) {
            if (this.putWallSlotStatus != PutWallSlotStatusEnum.WAITING_BINDING) {
                throw new RuntimeException("PutWallSlot is not WAITING_BINDING, cannot bind container");
            }

            this.transferContainerCode = containerCode;
            this.putWallSlotStatus = PutWallSlotStatusEnum.BOUND;
        }
    }

    private Long version;
}
