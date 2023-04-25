package com.swms.wms.api.basic.dto;

import com.google.common.collect.Lists;
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

    @Override
    public boolean validate() {
        List<String> allSlotSpecCodes = containerSlotSpecs.stream()
            .flatMap(containerSlotSpec -> containerSlotSpec.getAllContainerSlotSpecCodes().stream()).toList();
        return allSlotSpecCodes.size() == allSlotSpecCodes.stream().distinct().count();
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

        public List<String> getAllContainerSlotSpecCodes() {
            List<String> allContainerSlotSpecCodes = Lists.newArrayList(containerSlotSpecCode);
            if (CollectionUtils.isEmpty(children)) {
                children.stream().map(ContainerSlotSpec::getAllContainerSlotSpecCodes)
                    .forEach(allContainerSlotSpecCodes::addAll);
            }
            return allContainerSlotSpecCodes;
        }
    }
}
