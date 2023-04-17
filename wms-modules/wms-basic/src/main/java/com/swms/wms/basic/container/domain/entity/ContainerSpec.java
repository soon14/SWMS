package com.swms.wms.basic.container.domain.entity;

import com.swms.wms.api.basic.constants.ContainerTypeEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
public class ContainerSpec {

    private Long id;

    // unique identifier
    private String containerSpecCode;

    private Integer length;
    private Integer width;
    private Integer height;
    private Long volume;

    private Integer containerSlotNum;

    private String description;

    private ContainerTypeEnum containerType;

    private List<ContainerSlotSpec> containerSlotSpecs;

    public List<ContainerSlotSpec> getContainerSlotSpecsByFace(String face) {
        return containerSlotSpecs.stream().filter(v -> StringUtils.equals(v.getFace(), face)).toList();
    }

    @Data
    public static class ContainerSlotSpec {

        private Long id;

        private String containerSpecCode;

        // unique identifier
        private String containerSlotSpecCode;

        private String face;

        private Integer length;
        private Integer width;
        private Integer height;
        private Long volume;

        private Integer level;
        private Integer bay;

        private Long parentId;
    }
}
