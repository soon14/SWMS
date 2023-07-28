package com.swms.wms.api.basic.dto;

import com.swms.utils.validate.IValidate;
import com.swms.wms.api.basic.constants.ContainerTypeEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateContainerDTO implements IValidate {

    @NotEmpty
    private String warehouseCode;

    @NotEmpty
    private ContainerTypeEnum containerType;

    @NotEmpty
    private String containerSpecCode;

    @NotEmpty
    private String containerCodePrefix;

    @NotNull
    @Min(1)
    private Integer startIndex;

    @NotNull
    @Min(1)
    private Integer indexNumber;

    @NotNull
    @Min(1)
    private Integer createNumber;

    @Override
    public boolean validate() {
        return startIndex + createNumber <= getEndIndex();
    }

    public Integer getEndIndex() {
        return (int) Math.pow(10d, indexNumber - 1d);
    }
}
