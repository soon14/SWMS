package com.swms.wms.outbound.domain.entity;

import com.google.common.collect.Sets;
import com.swms.common.utils.id.OrderNoGenerator;
import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.wms.api.outbound.constants.OutboundPlanOrderStatusEnum;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class OutboundPlanOrder {

    private Long id;

    private String warehouseCode;
    private String ownerCode;

    private String waveNo;
    private String customerOrderNo;
    private String customerOrderType;
    private String currierCode;
    private String waybillNo;
    private String origPlatformCode;

    private Long expiredTime;
    private Integer priority = 0;

    private String orderNo;

    private Integer skuKindNum;
    private Integer totalQty;

    private OutboundPlanOrderStatusEnum outboundPlanOrderStatus;

    private boolean shortOutbound;

    private boolean abnormal;
    private String abnormalReason;

    private Map<String, String> reservedFields;

    private List<OutboundPlanOrderDetail> details;

    private Long version;

    public void initialize() {
        Set<String> skuSet = Sets.newHashSet();
        for (OutboundPlanOrderDetail v : details) {
            skuSet.add(v.getSkuCode());
            this.totalQty = (this.totalQty == null ? 0 : this.totalQty) + v.getQtyRequired();
        }
        this.skuKindNum = skuSet.size();
        this.orderNo = OrderNoGenerator.generationOutboundPlanOrderNo();
    }

    public void initSkuId(Set<SkuMainDataDTO> skuMainDataDTOS) {
        Map<String, SkuMainDataDTO> skuMap = skuMainDataDTOS.stream()
            .collect(Collectors.toMap(SkuMainDataDTO::getSkuCode, v -> v));
        this.details.forEach(v -> v.setSkuId(skuMap.get(v.getSkuCode()).getId()));
    }

    public boolean preAllocate(List<OutboundPreAllocatedRecord> planPreAllocatedRecords) {
        Integer totalPreAllocated = planPreAllocatedRecords
            .stream().map(OutboundPreAllocatedRecord::getQtyPreAllocated).reduce(Integer::sum).orElse(0);
        Integer totalRequired = details.stream().map(OutboundPlanOrderDetail::getQtyRequired).reduce(Integer::sum).orElse(0);
        if (totalRequired.equals(totalPreAllocated)) {
            this.outboundPlanOrderStatus = OutboundPlanOrderStatusEnum.ASSIGNED;
            return true;
        }

        if (shortOutbound) {
            this.outboundPlanOrderStatus = OutboundPlanOrderStatusEnum.SHORT_WAITING;
            return true;
        }

        shortComplete();
        return false;
    }

    private void shortComplete() {
        this.outboundPlanOrderStatus = OutboundPlanOrderStatusEnum.PICKED;
        this.abnormal = true;
        this.abnormalReason = "short complete";
    }

    public void picking(Integer operatedQty, Long outboundOrderDetailId) {
        details.stream().filter(v -> v.getId().equals(outboundOrderDetailId))
            .forEach(detail -> detail.setQtyActual(detail.getQtyActual() + operatedQty));

        if (this.outboundPlanOrderStatus == OutboundPlanOrderStatusEnum.ASSIGNED) {
            this.outboundPlanOrderStatus = OutboundPlanOrderStatusEnum.PICKING;
        }
    }
}
