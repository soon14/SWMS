package com.swms.wms.api.inbound.dto;

import com.swms.common.utils.validate.IValidate;
import com.swms.wms.api.inbound.constants.AcceptMethodEnum;
import com.swms.wms.api.inbound.constants.AcceptTypeEnum;
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

    @NotNull
    @Min(1)
    private Integer qtyAccepted;

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

    @NotNull
    private AcceptMethodEnum acceptMethod;
    @NotNull
    private AcceptTypeEnum acceptType;

    @Override
    public boolean validate() {
        return (inboundPlanOrderId != null && inboundPlanOrderDetailId != null) || StringUtils.isNotEmpty(boxNo);
    }
}
