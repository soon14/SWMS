package com.swms.wms.api.outbound.dto;

import com.swms.wms.api.outbound.constants.PickingOrderStatusEnum;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Data
public class PickingOrderDTO {

    private Long id;

    private String warehouseCode;

    private String waveNo;

    private String pickingOrderNo;

    private PickingOrderStatusEnum pickingOrderStatus;

    /**
     * one picking order can be assigned to multiple station slot
     * <p>
     * Key is the station id
     * Value is the put wall slot code
     */
    private Map<Long, String> assignedStationSlot;

    private List<PickingOrderDetailDTO> details;

    @Data
    public static class PickingOrderDetailDTO {

        private Long id;

        private Long outboundOrderPlanId;
        private Long outboundOrderPlanDetailId;
        private Long skuId;
        private TreeMap<String, Object> batchAttributes;

        private Long skuBatchStockId;

        private Integer qtyRequired;
        private Integer qtyActual;

    }
}
