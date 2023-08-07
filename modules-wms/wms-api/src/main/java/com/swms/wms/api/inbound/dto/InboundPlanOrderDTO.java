package com.swms.wms.api.inbound.dto;

import com.swms.common.utils.validate.IValidate;
import com.swms.common.utils.validate.ValidObject;
import com.swms.wms.api.inbound.constants.InboundPlanOrderStatusEnum;
import com.swms.wms.api.inbound.constants.StorageTypeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@ValidObject()
public class InboundPlanOrderDTO implements IValidate {

    private Long id;

    @Size(max = 64)
    private String orderNo;
    @NotEmpty
    @Size(max = 64)
    private String customerOrderNo;
    @Size(max = 64)
    private String lpnCode;

    @NotEmpty
    @Size(max = 64)
    private String warehouseCode;
    @NotEmpty
    @Size(max = 64)
    private String ownerCode;

    @NotEmpty
    @Size(max = 64)
    private String inboundOrderType;

    @NotEmpty
    private StorageTypeEnum storageType;
    private boolean abnormal;

    @Size(max = 128)
    private String sender;
    @Size(max = 128)
    private String carrier;
    @Size(max = 128)
    private String shippingMethod;
    @Size(max = 128)
    private String trackingNumber;
    private Long estimatedArrivalDate;
    @Size(max = 255)
    private String remark;

    private InboundPlanOrderStatusEnum inboundPlanOrderStatus;

    private Map<String, Object> extendFields;

    private Long version;

    @NotEmpty
    private List<InboundPlanOrderDetailDTO> inboundPlanOrderDetails;

    @Override
    public boolean validate() {

        Map<String, List<InboundPlanOrderDetailDTO>> uniqueDetailMap = inboundPlanOrderDetails.stream()
            .collect(Collectors.groupingBy(this::uniqueDetail));

        return uniqueDetailMap.entrySet().stream().allMatch(entry -> entry.getValue().size() == 1);
    }

    private String uniqueDetail(InboundPlanOrderDetailDTO detail) {
        return (detail.getBoxNo() == null ? "" : detail.getBoxNo()) + "$$" + detail.getSkuCode()
            + "$$" + (detail.getBatchAttributes() == null ? "" : detail.getBatchAttributes().toString());
    }
}
