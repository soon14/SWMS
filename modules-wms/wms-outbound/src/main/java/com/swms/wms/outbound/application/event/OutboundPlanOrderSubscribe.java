package com.swms.wms.outbound.application.event;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.eventbus.Subscribe;
import com.swms.domain.event.DomainEventPublisher;
import com.swms.mdm.api.config.dto.BatchAttributeConfigDTO;
import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.wms.outbound.domain.aggregate.OutboundPlanOrderPreAllocatedAggregate;
import com.swms.wms.outbound.domain.entity.OutboundPlanOrder;
import com.swms.wms.outbound.domain.entity.OutboundPlanOrderDetail;
import com.swms.wms.outbound.domain.repository.OutboundPlanOrderRepository;
import com.swms.wms.outbound.infrastructure.remote.BatchAttributeConfigFacade;
import com.swms.wms.outbound.infrastructure.remote.SkuMainDataFacade;
import com.swms.wms.api.outbound.constants.OutboundPlanOrderStatusEnum;
import com.swms.wms.api.outbound.event.NewOutboundPlanOrderEvent;
import com.swms.wms.api.outbound.event.OrderPickingEvent;
import com.swms.wms.api.outbound.event.OutboundPlanOrderAssignedEvent;
import com.swms.wms.api.stock.ISkuBatchAttributeApi;
import com.swms.wms.api.stock.IStockApi;
import com.swms.wms.api.stock.dto.SkuBatchAttributeDTO;
import com.swms.wms.api.stock.dto.SkuBatchStockDTO;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class OutboundPlanOrderSubscribe {

    @Autowired
    private OutboundPlanOrderPreAllocatedAggregate outboundPlanOrderPreAllocatedAggregate;

    @Autowired
    private OutboundPlanOrderRepository outboundPlanOrderRepository;

    @Autowired
    private ISkuBatchAttributeApi skuBatchAttributeApi;

    @Autowired
    private SkuMainDataFacade skuMainDataApi;

    @Autowired
    private BatchAttributeConfigFacade skuBatchAttributeConfigApi;

    @Autowired
    private IStockApi stockApi;

    @Autowired
    private DomainEventPublisher publisher;

    @Subscribe
    public void onCreateEvent(@Valid NewOutboundPlanOrderEvent event) {

        PreAllocateCache preAllocateCache = new PreAllocateCache();

        OutboundPlanOrder outboundPlanOrder = outboundPlanOrderRepository.findByOrderNo(event.getOrderNo());
        if (outboundPlanOrder.getOutboundPlanOrderStatus() != OutboundPlanOrderStatusEnum.NEW) {
            log.error("outbound status must be NEW when preparing allocate stocks");
            return;
        }

        List<Long> skuIds = outboundPlanOrder.getDetails()
            .stream().map(OutboundPlanOrderDetail::getSkuId).toList();
        Map<Long, List<SkuBatchAttributeDTO>> skuBatchAttributeMap = skuBatchAttributeApi.getBySkuIds(skuIds)
            .stream().collect(Collectors.groupingBy(SkuBatchAttributeDTO::getSkuId));

        List<SkuMainDataDTO> skuMainDataDTOS = skuMainDataApi.getByIds(skuIds);
        Map<Long, String> skuCategoryMap = skuMainDataDTOS.stream().collect(Collectors.toMap(SkuMainDataDTO::getId,
            v -> Optional.ofNullable(v.getSkuAttribute().getSkuFirstCategory()).orElse(StringUtils.EMPTY)));

        Map<String, BatchAttributeConfigDTO> skuBatchAttributeConfigMap = Maps.newHashMap();
        Sets.newHashSet(skuCategoryMap.values()).forEach(skuFirstCategory -> {
            BatchAttributeConfigDTO batchAttributeConfigDTO = skuBatchAttributeConfigApi
                .getByOwnerAndSkuFirstCategory(outboundPlanOrder.getOwnerCode(), skuFirstCategory);
            skuBatchAttributeConfigMap.put(skuFirstCategory, batchAttributeConfigDTO);
        });

        List<Long> skuBatchAttributeIds = skuBatchAttributeMap.values()
            .stream().flatMap(Collection::stream).map(SkuBatchAttributeDTO::getId).toList();
        List<SkuBatchStockDTO> skuBatchStockDTOS = stockApi.getBySkuBatchAttributeIds(skuBatchAttributeIds)
            .stream().filter(v -> StringUtils.equals(v.getWarehouseCode(), outboundPlanOrder.getWarehouseCode()))
            .toList();

        preAllocateCache.setSkuCategoryMap(skuCategoryMap);
        preAllocateCache.setSkuBatchAttributeMap(skuBatchAttributeMap);
        preAllocateCache.setSkuBatchAttributeConfigMap(skuBatchAttributeConfigMap);
        preAllocateCache.setSkuBatchStocks(skuBatchStockDTOS);

        boolean preAllocateResult = outboundPlanOrderPreAllocatedAggregate.preAllocate(outboundPlanOrder, preAllocateCache);

        if (preAllocateResult) {
            publisher.sendAsyncEvent(new OutboundPlanOrderAssignedEvent()
                .setOutboundPlanOrderId(outboundPlanOrder.getId())
                .setWarehouseCode(outboundPlanOrder.getWarehouseCode()));
        } else {
            //TODO prepare allocate stocks error, order complete and callback
        }

    }

    @Subscribe
    public void onPickingEvent(@Valid OrderPickingEvent event) {

        List<OrderPickingEvent.PickingDetail> pickingDetails = event.getPickingDetails();
        List<Long> outboundPlanOrderIds = pickingDetails.stream().map(OrderPickingEvent.PickingDetail::getOutboundOrderId).toList();
        List<Long> outboundPlanOrderDetailIds = pickingDetails.stream().map(OrderPickingEvent.PickingDetail::getOutboundOrderDetailId).toList();
        List<OutboundPlanOrder> outboundPlanOrders = outboundPlanOrderRepository.findAllByIds(outboundPlanOrderIds);
        outboundPlanOrders.forEach(outboundPlanOrder ->
            outboundPlanOrder.getDetails().removeIf(v -> !outboundPlanOrderDetailIds.contains(v.getId())));

        Map<Long, OutboundPlanOrder> outboundPlanOrderMap = outboundPlanOrders.stream().collect(Collectors.toMap(OutboundPlanOrder::getId, v -> v));
        pickingDetails.forEach(pickingDetail -> {
            OutboundPlanOrder outboundPlanOrder = outboundPlanOrderMap.get(pickingDetail.getOutboundOrderId());
            outboundPlanOrder.picking(pickingDetail.getOperatedQty(), pickingDetail.getOutboundOrderDetailId());
        });

        outboundPlanOrderRepository.saveOrderAndDetails(outboundPlanOrders);

        //TODO callback if order completed
    }

    @Data
    @Accessors(chain = true)
    public static class PreAllocateCache {
        private Map<Long, String> skuCategoryMap;
        private Map<String, BatchAttributeConfigDTO> skuBatchAttributeConfigMap;
        private Map<Long, List<SkuBatchAttributeDTO>> skuBatchAttributeMap;
        private List<SkuBatchStockDTO> skuBatchStocks;
    }
}
