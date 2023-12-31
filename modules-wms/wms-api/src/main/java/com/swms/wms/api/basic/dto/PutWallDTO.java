package com.swms.wms.api.basic.dto;

import com.google.common.collect.Lists;
import com.swms.common.utils.exception.WmsException;
import com.swms.common.utils.validate.IValidate;
import com.swms.wms.api.basic.constants.PutWallSlotStatusEnum;
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

    @NotNull
    private Long workStationId;
    @NotEmpty
    private String putWallCode;
    @NotEmpty
    private String putWallName;
    @NotEmpty
    private String containerSpecCode;
    @NotEmpty
    private List<PutWallSlot> putWallSlots;

    private Long version;

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
        private Long workStationId;
        private String putWallCode;
        private String putWallSlotCode;

        // it's define the put wall LEFT or RIGHT or MIDDLE
        private String face;

        private String containerSlotSpecCode;

        private boolean enable;

        private List<Long> orderIds;
        private PutWallSlotStatusEnum putWallSlotStatus;
        private String transferContainerCode;

        public void release() {
            if (CollectionUtils.isEmpty(this.orderIds)) {
                throw new WmsException("PutWallSlot is empty");
            }

            if (this.putWallSlotStatus != PutWallSlotStatusEnum.WAITING_SEAL) {
                throw new WmsException("PutWallSlot is not WAITING_SEAL, cannot release");
            }

            this.orderIds = Lists.newArrayList();
            this.putWallSlotStatus = PutWallSlotStatusEnum.IDLE;
        }

        public void initPutWallSlot(String putWallCode, Long workStationId) {
            this.workStationId = workStationId;
            this.putWallCode = putWallCode;

            this.putWallSlotCode = this.containerSlotSpecCode;
        }
    }
}
