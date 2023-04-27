package com.swms.wms.api.basic.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReleasePutWallSlotsDTO {

    @NotEmpty
    private List<String> putWallSlotCodes;
}
