package com.swms.inbound.domain.entity;

import com.swms.inbound.domain.repository.InboundPlanOrderDetailRepository;
import com.swms.inbound.domain.repository.InboundPlanOrderRepository;
import com.swms.inbound.domain.transfer.InboundPlanOrderDetailTransfer;
import com.swms.inbound.domain.transfer.InboundPlanOrderTransfer;
import com.swms.wms.api.inbound.constants.InboundPlanOrderStatusEnum;
import com.swms.wms.api.inbound.dto.InboundPlanOrderDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.TreeMap;

@Data
@RequiredArgsConstructor
public class InboundPlanOrder {

    private Long id;

    private String orderNo;
    private String customerOrderNo;
    private String lpnCode;

    private String warehouseCode;
    private String warehouseName;

    /**
     * redundancy fields , according to InboundPlanOrderDetail ownerCode and ownerName
     */
    private String ownerCode;
    private String ownerName;

    private String inboundOrderType;

    private boolean abnormal;

    private InboundPlanOrderStatusEnum inboundPlanOrderStatus = InboundPlanOrderStatusEnum.NEW;

    private Long estimatedArrivalTime;

    private Integer skuKindNum;
    private Long totalQty;
    private Integer totalBox;

    private TreeMap<String, Object> extendFields;

    private InboundPlanOrderRepository inboundPlanOrderRepository;
    private InboundPlanOrderDetailRepository inboundPlanOrderDetailRepository;
    private InboundPlanOrderTransfer inboundPlanOrderTransfer;
    private InboundPlanOrderDetailTransfer inboundPlanOrderDetailTransfer;

    public void createInboundPlanOrder(InboundPlanOrderDTO inboundPlanOrderDTO) {
        InboundPlanOrder inboundPlanOrder = inboundPlanOrderTransfer.toInboundPlanOrder(inboundPlanOrderDTO);
        inboundPlanOrderRepository.save(inboundPlanOrder);

        List<InboundPlanOrderDetail> inboundPlanOrderDetails = inboundPlanOrderDetailTransfer
            .toInboundPlanOrderDetails(inboundPlanOrderDTO.getInboundPlanOrderDetails());
        inboundPlanOrderDetailRepository.saveAll(inboundPlanOrderDetails);
    }

    public int cancelInboundPlanOrder(Long inboundPlanOrderId) {
        return inboundPlanOrderRepository.updateStatusWithOriginalStatus(inboundPlanOrderId, InboundPlanOrderStatusEnum.CANCEL, InboundPlanOrderStatusEnum.NEW);
    }
}
