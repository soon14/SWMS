package com.swms.wms.api.basic.dto;

import static com.swms.utils.exception.code_enum.BasicErrorDescEnum.CONTAINER_SPECIFIC_SLOT_CODE_REPEAT;

import com.google.common.collect.Lists;
import com.swms.utils.exception.WmsException;
import com.swms.utils.validate.IValidate;
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
    private String containerSpecCode;

    @NotNull
    @Min(1)
    private Integer length;
    @NotNull
    @Min(1)
    private Integer width;
    @NotNull
    @Min(1)
    private Integer height;
    @NotNull
    @Min(1)
    private Long volume;

    @NotNull
    @Min(1)
    private Integer containerSlotNum;

    private String description;

    @NotNull
    private ContainerTypeEnum containerType;

    @NotEmpty
    private List<ContainerSlotSpec> containerSlotSpecs;

    private Long version;

    @Override
    public boolean validate() {
        List<String> allSlotSpecCodes = containerSlotSpecs.stream()
            .flatMap(containerSlotSpec ->
                containerSlotSpec.getAllContainerSlotSpecCodes(containerSlotSpec.getChildren()).stream()).toList();
        if (allSlotSpecCodes.size() != allSlotSpecCodes.stream().distinct().count()) {
            throw WmsException.throwWmsException(CONTAINER_SPECIFIC_SLOT_CODE_REPEAT);
        }
        return true;
    }

    @Data
    public static class ContainerSlotSpec {

        @NotEmpty
        private String containerSlotSpecCode;

        @NotEmpty
        private String face;

        @NotNull
        @Min(1)
        private Integer length;
        @NotNull
        @Min(1)
        private Integer width;
        @NotNull
        @Min(1)
        private Integer height;
        @NotNull
        @Min(1)
        private Long volume;

        @NotNull
        @Min(1)
        private Integer level;
        @NotNull
        @Min(1)
        private Integer bay;

        @NotEmpty
        private List<ContainerSlotSpec> children;

        public List<String> getAllContainerSlotSpecCodes(List<ContainerSlotSpec> children) {

            if (CollectionUtils.isEmpty(children)) {
                return Lists.newArrayList(containerSlotSpecCode);
            }

            List<String> allSlotSpecCodes = Lists.newArrayList(containerSlotSpecCode);
            allSlotSpecCodes.addAll(children.stream()
                .flatMap(v -> v.getAllContainerSlotSpecCodes(v.getChildren()).stream())
                .toList());
            return allSlotSpecCodes;
        }
    }
}
