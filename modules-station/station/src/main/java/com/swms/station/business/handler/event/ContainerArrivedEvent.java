package com.swms.station.business.handler.event;

import com.swms.wms.api.basic.constants.WorkLocationTypeEnum;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContainerArrivedEvent {

    @NotEmpty
    private List<ContainerDetail> containerDetails;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContainerDetail {

        @NotEmpty
        private String containerCode;

        // container arrived with which face
        private String face;
        private String stationCode;
        private String robotCode;
        private String robotType;
        private Integer level;
        private Integer bay;

        @NotEmpty
        private String locationCode;

        // container forward face
        private String forwardFace;

        @NotEmpty
        private String workLocationCode;
        private WorkLocationTypeEnum workLocationType;

        @NotEmpty
        private String groupCode;

        private Map<String, Object> containerAttributes;
    }
}
