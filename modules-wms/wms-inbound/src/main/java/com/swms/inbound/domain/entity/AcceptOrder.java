package com.swms.inbound.domain.entity;

import com.swms.wms.api.inbound.constants.AcceptMethodEnum;
import com.swms.wms.api.inbound.constants.AcceptOrderStatusEnum;
import com.swms.wms.api.inbound.constants.AcceptTypeEnum;
import com.swms.wms.api.inbound.dto.AcceptOrderDetailDTO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

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

    private Long totalQty;
    private Integer totalBox;

    private String remark;

    private AcceptOrderStatusEnum acceptOrderStatus;

    private List<AcceptOrderDetailDTO> acceptOrderDetails;

    private Long version;

    public void initial() {
        for (AcceptOrderDetailDTO v : acceptOrderDetails) {
            int box = StringUtils.isNotEmpty(v.getBoxNo()) ? 1 : 0;
            this.totalBox = this.totalBox == null ? 0 : this.totalBox + box;
            this.totalQty = this.totalQty == null ? 0 : this.totalQty + v.getQtyAccepted();
        }
    }
}
