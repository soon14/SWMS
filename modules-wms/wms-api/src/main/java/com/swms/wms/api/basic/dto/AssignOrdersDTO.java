package com.swms.wms.api.basic.dto;

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
public class AssignOrdersDTO {

    @NotNull
    private Long workStationId;

    @NotEmpty
    private List<AssignDetail> assignDetails;

    @Data
    public static class AssignDetail {
        @NotEmpty
        private List<Long> orderIds;

        @NotEmpty
        private String putWallSlotCode;
    }
}
