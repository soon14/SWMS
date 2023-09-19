package com.swms.wms.api.basic.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class AssignOrdersDTO {

    @NotEmpty
    private List<AssignDetail> assignDetails;

    @Data
    @Accessors(chain = true)
    public static class AssignDetail {

        @NotNull
        private Long workStationId;

        @NotEmpty
        private String putWallSlotCode;

        @NotEmpty
        private String putWallCode;

        @NotEmpty
        private Long orderId;

    }
}
