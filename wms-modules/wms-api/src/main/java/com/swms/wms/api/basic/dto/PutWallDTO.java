package com.swms.wms.api.basic.dto;

import com.swms.utils.validate.IValidate;
import com.swms.wms.api.basic.constants.PutWallSlotStatusEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    }

    private Long version;
}
