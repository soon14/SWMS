package com.swms.inbound.domain.entity;

import com.swms.wms.api.inbound.constants.AcceptOrderStatusEnum;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Data
public class PutAwayTask {

    private Long id;

    private String taskNo;

    private String warehouseCode;
    private String warehouseName;

    /**
     * redundancy fields , according to PutAwayTask ownerCode and ownerName
     */
    private String ownerCode;
    private String ownerName;

    private String putAwayTaskType;

    private boolean abnormal;

    private Long totalQty;
    private Integer totalBox;

    private Map<String, Object> extendFields;

    private AcceptOrderStatusEnum acceptOrderStatus;

    private List<PutAwayTaskDetail> putAwayTaskDetails;

    @Data
    public static class PutAwayTaskDetail {
        private Long id;

        private Long inboundPlanOrderDetailId;
        private Long receiveOrderDetailId;
        private Long acceptOrderDetailId;

        private String containerCode;
        private String containerSpecCode;
        private String containerSlotCode;

        private String boxNo;

        private String targetContainerCode;
        private String targetContainerSpecCode;
        private String targetContainerSlotCode;

        private Integer qtyAccepted = 0;
        private Integer qtyPutAway = 0;
        private Integer qtyAbnormal = 0;

        private String abnormalReason;

        private String skuCode;
        private String packageCode;
        private SortedMap<String, Object> batchAttributes = new TreeMap<>();
        private String skuName;
        private String ownerCode;
        private String ownerName;

        private String stationCode;

        private Map<String, Object> extendFields = new TreeMap<>();
    }
}
