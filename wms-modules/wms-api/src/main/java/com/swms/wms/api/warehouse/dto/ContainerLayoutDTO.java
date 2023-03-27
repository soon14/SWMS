package com.swms.wms.api.warehouse.dto;

import com.swms.wms.api.warehouse.constants.ContainerTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContainerLayoutDTO {
    private String containerCode;

    private Integer length;
    private Integer width;
    private Integer height;
    private Long volume;

    private Integer containerSlotNum;
    private String description;

    private ContainerTypeEnum containerType;

    private List<ContainerSlotLayoutDTO> containerSlotLayoutDTOS;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ContainerSlotLayoutDTO {

        private String containerSlotCode;
        private String face;
        private Integer level;
        private Integer bay;

        private Long id;
        private Long parentId;

        private List<ContainerSlotLayoutDTO> children;
    }
}
