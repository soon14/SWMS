package com.swms.inbound.application;

import com.google.common.collect.Lists;
import com.swms.common.utils.constants.RedisConstants;
import com.swms.distribute.lock.DistributeLock;
import com.swms.domain.event.DomainEvent;
import com.swms.domain.event.DomainEventPublisher;
import com.swms.inbound.domain.entity.AcceptOrder;
import com.swms.inbound.domain.entity.InboundPlanOrder;
import com.swms.inbound.domain.repository.AcceptOrderRepository;
import com.swms.inbound.domain.service.AcceptOrderService;
import com.swms.inbound.domain.transfer.AcceptOrderTransfer;
import com.swms.wms.api.inbound.IAcceptOrderApi;
import com.swms.wms.api.inbound.constants.AcceptOrderStatusEnum;
import com.swms.wms.api.inbound.dto.AcceptOrderDetailDTO;
import com.swms.wms.api.inbound.dto.AcceptRecordDTO;
import com.swms.wms.api.inbound.dto.InboundPlanOrderDetailDTO;
import com.swms.wms.api.inbound.event.AcceptEvent;
import com.swms.wms.api.stock.ISkuBatchAttributeApi;
import com.swms.wms.api.stock.dto.SkuBatchAttributeDTO;
import com.swms.wms.api.stock.dto.StockCreateDTO;
import com.swms.wms.api.task.event.StockCreateEvent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Service
public class AcceptOrderApiImpl implements IAcceptOrderApi {

    @Autowired
    private AcceptOrderTransfer acceptOrderTransfer;

    @Autowired
    private AcceptOrderService acceptOrderService;

    @Autowired
    private DistributeLock distributeLock;

    @Autowired
    private AcceptOrderRepository acceptOrderRepository;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Autowired
    private ISkuBatchAttributeApi skuBatchAttributeApi;

    @Override
    public void accept(AcceptRecordDTO acceptRecord) {

        InboundPlanOrder inboundPlanOrder = acceptOrderService.findAcceptInboundPlanOrder(acceptRecord);
        InboundPlanOrderDetailDTO inboundPlanOrderDetailDTO = inboundPlanOrder.getInboundPlanOrderDetails().iterator().next();


        AcceptOrderDetailDTO acceptOrderDetailDTO = acceptOrderTransfer.toDetailDO(inboundPlanOrderDetailDTO, acceptRecord);
        AcceptOrder acceptOrder = acceptOrderTransfer.toDO(inboundPlanOrder, acceptRecord, Lists.newArrayList(acceptOrderDetailDTO));

        acceptOrder.initial();

        distributeLock.acquireLockIfThrows(RedisConstants.INBOUND_ACCEPT_OPERATE_LOCK + inboundPlanOrder.getId(), 3000L);

        try {
            List<AcceptOrder> acceptOrders = acceptOrderRepository.findByInboundPlanOrderId(inboundPlanOrder.getId());
            AcceptOrder existsAcceptOrder = acceptOrders.stream()
                .filter(v -> v.getAcceptOrderStatus() == AcceptOrderStatusEnum.NEW).findFirst().orElse(null);

            acceptOrderService.validateOverAccept(acceptRecord, acceptOrders, inboundPlanOrderDetailDTO);

            if (existsAcceptOrder != null) {
                existsAcceptOrder.setAcceptOrderDetails(acceptOrder.getAcceptOrderDetails());
                existsAcceptOrder.addAcceptQty(acceptRecord.getQtyAccepted());
                acceptOrderRepository.saveOrderAndDetail(existsAcceptOrder);
            } else {
                acceptOrderRepository.saveOrderAndDetail(acceptOrder);
            }

            // get or create batch attributes
            SkuBatchAttributeDTO skuBatchAttribute = skuBatchAttributeApi
                .getOrCreateSkuBatchAttribute(inboundPlanOrderDetailDTO.getSkuId(), acceptRecord.getBatchAttributes());

            // notify create stock
            StockCreateDTO stockCreateDTO = StockCreateDTO.builder()
                .boxStock(StringUtils.isNotEmpty(inboundPlanOrderDetailDTO.getBoxNo()))
                .orderNo(inboundPlanOrder.getOrderNo())
                .skuBatchAttributeId(skuBatchAttribute.getId())
                .skuId(skuBatchAttribute.getSkuId())
                .boxNo(inboundPlanOrderDetailDTO.getBoxNo())
                .sourceContainerCode(inboundPlanOrder.getLpnCode())
                .sourceContainerSlotCode("A")
                .targetContainerCode(acceptRecord.getTargetContainerCode())
                .targetContainerSlotCode(acceptRecord.getTargetContainerSlotCode())
                .transferQty(acceptRecord.getQtyAccepted())
                .warehouseCode(acceptRecord.getWarehouseCode())
                .warehouseAreaId(1L)
                .build();

            domainEventPublisher.sendAsyncEvent(StockCreateEvent.builder().stockCreateDTO(stockCreateDTO).build());

        } finally {
            distributeLock.releaseLock(RedisConstants.INBOUND_ACCEPT_OPERATE_LOCK + inboundPlanOrder.getId());
        }

    }

    @Override
    public void audit(Long acceptOrderId) {
        AcceptOrder acceptOrder = acceptOrderRepository.findById(acceptOrderId);
        acceptOrder.audit();
        acceptOrderRepository.saveOrder(acceptOrder);

        //send domain event to notify inbound plan order change accept qty
        List<AcceptEvent.AcceptDetail> acceptDetails = acceptOrder.getAcceptOrderDetails().stream().map(v ->
            AcceptEvent.AcceptDetail.builder().inboundPlanOrderDetailId(v.getInboundPlanOrderDetailId())
                .qtyAccepted(v.getQtyAccepted())
                .build()).toList();
        domainEventPublisher.sendAsyncEvent(AcceptEvent.builder()
            .inboundPlanOrderId(acceptOrder.getInboundPlanOrderId())
            .acceptDetails(acceptDetails).build());
    }
}
