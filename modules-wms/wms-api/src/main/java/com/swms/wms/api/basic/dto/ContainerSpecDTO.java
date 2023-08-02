package com.swms.wms.api.basic.dto;


import static com.swms.common.utils.exception.code_enum.BasicErrorDescEnum.CONTAINER_SPECIFIC_SLOT_CODE_REPEAT;
import static com.swms.common.utils.exception.code_enum.BasicErrorDescEnum.CONTAINER_SPECIFIC_SLOT_LEVEL_BAY_REPEAT;

import com.google.common.collect.Lists;
import com.swms.common.utils.exception.WmsException;
import com.swms.common.utils.validate.IValidate;
import com.swms.wms.api.basic.constants.ContainerTypeEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

@Data
public class ContainerSpecDTO implements IValidate {

    private Long id;

    @NotEmpty
    private String warehouseCode;

    @NotEmpty
    private String containerSpecCode;

    @NotEmpty
    private String containerSpecName;

    @Min(1)
    private Integer length;
    @Min(1)
    private Integer width;
    @Min(1)
    private Integer height;
    @Min(1)
    private Long volume;

    private String description;

    @NotNull
    private ContainerTypeEnum containerType;

    @NotEmpty
    private List<ContainerSlotSpec> containerSlotSpecs;

    private Long version;

    public Integer getContainerSlotNum() {
        return containerSlotSpecs == null ? 0 : containerSlotSpecs.size();
    }

    @Override
    public boolean validate() {
        List<String> allSlotSpecCodes = containerSlotSpecs.stream()
            .flatMap(containerSlotSpec ->
                containerSlotSpec.getAllContainerSlotSpecCodes(containerSlotSpec.getChildren()).stream()).toList();
        if (allSlotSpecCodes.size() != allSlotSpecCodes.stream().distinct().count()) {
            throw WmsException.throwWmsException(CONTAINER_SPECIFIC_SLOT_CODE_REPEAT);
        }


        List<String> allLevelBay = containerSlotSpecs.stream()
            .flatMap(containerSlotSpec ->
                containerSlotSpec.getAllLevelBay(containerSlotSpec.getChildren()).stream()).toList();

        if (allLevelBay.size() != allLevelBay.stream().distinct().count()) {
            throw WmsException.throwWmsException(CONTAINER_SPECIFIC_SLOT_LEVEL_BAY_REPEAT);
        }

        return true;
    }

    @Data
    public static class ContainerSlotSpec {

        @NotEmpty
        private String containerSlotSpecCode;

        private String face;

        @Min(1)
        private Integer length;
        @Min(1)
        private Integer width;
        @Min(1)
        private Integer height;
        @Min(1)
        private Long volume;

        @Min(1)
        @NotNull
        private Integer level;
        @Min(1)
        @NotNull
        private Integer bay;

        @Min(1)
        @NotNull
        private Integer locLevel;
        @Min(1)
        @NotNull
        private Integer locBay;

        @NotEmpty
        private List<ContainerSlotSpec> children;

        public List<String> getAllContainerSlotSpecCodes(List<ContainerSlotSpec> children) {

            List<String> allSlotSpecCodes = Lists.newArrayList(containerSlotSpecCode);
            if (CollectionUtils.isEmpty(children)) {
                return allSlotSpecCodes;
            }

            allSlotSpecCodes.addAll(children.stream()
                .flatMap(v -> v.getAllContainerSlotSpecCodes(v.getChildren()).stream())
                .toList());
            return allSlotSpecCodes;
        }

        public List<String> getAllLevelBay(List<ContainerSlotSpec> children) {

            List<String> allLevelBay = Lists.newArrayList(locLevel + "-" + locBay);

            if (CollectionUtils.isEmpty(children)) {
                return allLevelBay;
            }

            allLevelBay.addAll(children.stream().flatMap(v -> v.getAllLevelBay(v.getChildren()).stream()).toList());
            return allLevelBay;
        }
    }
}
