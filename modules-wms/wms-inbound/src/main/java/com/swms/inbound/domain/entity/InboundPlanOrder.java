package com.swms.inbound.domain.entity;


import static com.swms.common.utils.exception.code_enum.InboundErrorDescEnum.INBOUND_STATUS_ERROR;

import com.google.common.collect.Sets;
import com.swms.common.utils.exception.WmsException;
import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.wms.api.inbound.constants.InboundPlanOrderStatusEnum;
import com.swms.wms.api.inbound.constants.StorageTypeEnum;
import com.swms.wms.api.inbound.dto.InboundPlanOrderDetailDTO;
import com.swms.wms.api.inbound.event.AcceptEvent;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    public void initial() {
        Set<String> skuSet = Sets.newHashSet();
        for (InboundPlanOrderDetailDTO v : inboundPlanOrderDetails) {
            skuSet.add(v.getSkuCode());
            int box = StringUtils.isNotEmpty(v.getBoxNo()) ? 1 : 0;
            this.totalBox = (this.totalBox == null ? 0 : this.totalBox) + box;
            this.totalQty = (this.totalQty == null ? 0 : this.totalQty) + v.getQtyRestocked();
        }
        this.skuKindNum = skuSet.size();
    }

    public void accept(AcceptEvent event) {
        this.inboundPlanOrderDetails.forEach(v -> event.getAcceptDetails().forEach(acceptDetail -> {
            if (Objects.equals(v.getId(), acceptDetail.getInboundPlanOrderDetailId())) {
                v.setQtyAccepted(v.getQtyAccepted() + acceptDetail.getQtyAccepted());
            }
        }));

        boolean result = this.inboundPlanOrderDetails.stream().allMatch(v -> Objects.equals(v.getQtyRestocked(), v.getQtyAccepted()));
        if (result) {
            this.inboundPlanOrderStatus = InboundPlanOrderStatusEnum.ACCEPTED;
        } else {
            this.inboundPlanOrderStatus = InboundPlanOrderStatusEnum.ACCEPTING;
        }
    }

    public void initSkuId(Set<SkuMainDataDTO> skuMainDataDTOS) {
        Map<String, SkuMainDataDTO> skuMap = skuMainDataDTOS.stream().collect(Collectors.toMap(SkuMainDataDTO::getSkuCode, v -> v));
        this.inboundPlanOrderDetails.forEach(v -> v.setSkuId(skuMap.get(v.getSkuCode()).getId()));
    }
}
