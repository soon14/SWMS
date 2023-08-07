package com.swms.inbound.domain.entity;


import static com.swms.common.utils.exception.code_enum.InboundErrorDescEnum.INBOUND_STATUS_ERROR;

import com.google.common.collect.Sets;
import com.swms.common.utils.exception.WmsException;
import com.swms.wms.api.inbound.constants.InboundPlanOrderStatusEnum;
import com.swms.wms.api.inbound.constants.StorageTypeEnum;
import com.swms.wms.api.inbound.dto.InboundPlanOrderDetailDTO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class InboundPlanOrder {

    private Long id;

    private String orderNo;
    private String customerOrderNo;
    private String lpnCode;

    private String warehouseCode;
    private String ownerCode;

    private String inboundOrderType;

    private StorageTypeEnum storageType;
    private boolean abnormal;

    private String sender;
    private String carrier;
    private String shippingMethod;
    private String trackingNumber;
    private Long estimatedArrivalDate;
    private String remark;

    private Integer skuKindNum;
    private Integer totalQty;
    private Integer totalBox;

    private InboundPlanOrderStatusEnum inboundPlanOrderStatus = InboundPlanOrderStatusEnum.NEW;

    private Map<String, Object> extendFields;

    private List<InboundPlanOrderDetailDTO> inboundPlanOrderDetails;

    private Long version;

    public void cancel() {
        if (inboundPlanOrderStatus != InboundPlanOrderStatusEnum.NEW) {
            throw new WmsException(INBOUND_STATUS_ERROR);
        }
        this.inboundPlanOrderStatus = InboundPlanOrderStatusEnum.CANCEL;
    }

    public void beginReceiving() {
        if (inboundPlanOrderStatus != InboundPlanOrderStatusEnum.NEW) {
            throw new WmsException(INBOUND_STATUS_ERROR);
        }
        this.inboundPlanOrderStatus = InboundPlanOrderStatusEnum.RECEIVING;
    }

    public void endReceiving() {
        if (inboundPlanOrderStatus != InboundPlanOrderStatusEnum.NEW
            && inboundPlanOrderStatus != InboundPlanOrderStatusEnum.RECEIVING) {
            throw new WmsException(INBOUND_STATUS_ERROR);
        }
        this.inboundPlanOrderStatus = InboundPlanOrderStatusEnum.RECEIVED;
    }

    public void endAccept() {
        if (inboundPlanOrderStatus != InboundPlanOrderStatusEnum.NEW
            && inboundPlanOrderStatus != InboundPlanOrderStatusEnum.RECEIVING
            && inboundPlanOrderStatus != InboundPlanOrderStatusEnum.RECEIVED) {
            throw new WmsException(INBOUND_STATUS_ERROR);
        }
        this.inboundPlanOrderStatus = InboundPlanOrderStatusEnum.RECEIVED;
    }

    public void initial() {
        Set<String> skuSet = Sets.newHashSet();
        for (InboundPlanOrderDetailDTO v : inboundPlanOrderDetails) {
            skuSet.add(v.getSkuCode());
            int box = StringUtils.isNotEmpty(v.getBoxNo()) ? 1 : 0;
            this.totalBox = this.totalBox == null ? 0 : this.totalBox + box;
            this.totalQty = this.totalQty == null ? 0 : this.totalQty + v.getQtyRestocked();
        }
        this.skuKindNum = skuSet.size();
    }

}
