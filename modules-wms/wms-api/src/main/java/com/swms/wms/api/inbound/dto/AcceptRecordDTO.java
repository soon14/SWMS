package com.swms.wms.api.inbound.dto;

import com.swms.common.utils.validate.IValidate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.SortedMap;

@Data
public class AcceptRecordDTO implements IValidate {

    private Long inboundPlanOrderId;
    private Long inboundPlanOrderDetailId;
    private String boxNo;

    @NotEmpty
    @Size(max = 64)
    private String warehouseCode;

    @NotEmpty
    @Size(max = 64)
    private String skuCode;
    private SortedMap<String, Object> batchAttributes;

    @NotEmpty
    @Min(1)
    private Integer acceptQty;

    @Size(max = 64)
    private String packBoxNo;

    @NotEmpty
    @Size(max = 64)
    private String targetContainerCode;
    @NotEmpty
    @Size(max = 64)
    private String targetContainerSpecCode;
    @NotEmpty
    @Size(max = 64)
    private String targetContainerSlotCode;

    @NotNull
    private Long stationId;

    @Override
    public boolean validate() {
        return (inboundPlanOrderId != null && inboundPlanOrderDetailId != null) || StringUtils.isNotEmpty(boxNo);
    }
}
