package com.swms.inbound.domain.entity;

import static com.swms.common.utils.exception.code_enum.InboundErrorDescEnum.ACCEPT_ORDER_HAD_AUDIT;

import com.swms.common.utils.exception.WmsException;
import com.swms.wms.api.inbound.constants.AcceptMethodEnum;
import com.swms.wms.api.inbound.constants.AcceptOrderStatusEnum;
import com.swms.wms.api.inbound.constants.AcceptTypeEnum;
import com.swms.wms.api.inbound.dto.AcceptOrderDetailDTO;
import lombok.Data;

import java.util.List;

@Data
public class AcceptOrder {

    private Long id;

    private String orderNo;

    private Long inboundPlanOrderId;

    private String warehouseCode;
    private String ownerCode;

    private AcceptMethodEnum acceptMethod;
    private AcceptTypeEnum acceptType;

    private boolean putAway;

    private Integer totalQty;

    private String remark;

    private AcceptOrderStatusEnum acceptOrderStatus;

    private List<AcceptOrderDetailDTO> acceptOrderDetails;

    private Long version;

    public void initial() {
        this.totalQty = acceptOrderDetails.stream().map(AcceptOrderDetailDTO::getQtyAccepted).reduce(Integer::sum).orElse(0);
    }

    public void addAcceptQty(Integer acceptQty) {
        this.totalQty += acceptQty;
    }

    public void audit() {
        if (acceptOrderStatus != AcceptOrderStatusEnum.NEW) {
            throw WmsException.throwWmsException(ACCEPT_ORDER_HAD_AUDIT, this.orderNo);
        }
        this.acceptOrderStatus = AcceptOrderStatusEnum.AUDITED;
    }
}
