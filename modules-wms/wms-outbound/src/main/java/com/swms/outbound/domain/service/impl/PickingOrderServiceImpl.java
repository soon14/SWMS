package com.swms.outbound.domain.service.impl;

import com.swms.common.utils.id.OrderNoGenerator;
import com.swms.extend.outbound.IOutboundWaveSplitPlugin;
import com.swms.extend.outbound.IPickingOrderAllocateStockPlugin;
import com.swms.extend.outbound.IPickingOrderAssignPlugin;
import com.swms.outbound.domain.entity.OutboundPreAllocatedRecord;
import com.swms.outbound.domain.entity.OutboundWave;
import com.swms.outbound.domain.entity.PickingOrder;
import com.swms.outbound.domain.entity.PickingOrderDetail;
import com.swms.outbound.domain.repository.OutboundPreAllocatedRecordRepository;
import com.swms.outbound.domain.service.PickingOrderService;
import com.swms.outbound.domain.transfer.OutboundWaveTransfer;
import com.swms.outbound.domain.transfer.PickingOrderTransfer;
import com.swms.plugin.sdk.utils.PluginUtils;
import com.swms.wms.api.algo.IPickingOrderAlgoApi;
import com.swms.wms.api.outbound.dto.PickingOrderAssignedResult;
import com.swms.wms.api.outbound.dto.PickingOrderDTO;
import com.swms.wms.api.outbound.dto.PickingOrderHandlerContext;
import com.swms.wms.api.stock.IStockApi;
import com.swms.wms.api.stock.dto.ContainerStockDTO;
import com.swms.wms.api.task.dto.OperationTaskDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PickingOrderServiceImpl implements PickingOrderService {

    @Autowired
    private OutboundPreAllocatedRecordRepository outboundPreAllocatedRecordRepository;

    @Autowired
    private PluginUtils pluginUtils;

    @Autowired
    private OutboundWaveTransfer outboundWaveTransfer;

    @Autowired
    private PickingOrderTransfer pickingOrderTransfer;

    @Autowired
    private IStockApi stockApi;

    @Autowired
    private IPickingOrderAlgoApi pickingOrderAlgoApi;

    @Override
    public List<PickingOrder> spiltWave(OutboundWave outboundWave) {

        List<IOutboundWaveSplitPlugin> outboundWaveSplitPlugins = pluginUtils.getExtractObject(IOutboundWaveSplitPlugin.class);
        if (CollectionUtils.isNotEmpty(outboundWaveSplitPlugins)) {
            List<PickingOrderDTO> pickingOrderDTOS = outboundWaveSplitPlugins.iterator().next()
                .doOperation(outboundWaveTransfer.toDTO(outboundWave));
            return pickingOrderTransfer.toDOs(pickingOrderDTOS);
        }

        return outboundWave.getOutboundPlanOrderIds().stream().map(outboundPlanOrderId -> {

            List<OutboundPreAllocatedRecord> records = outboundPreAllocatedRecordRepository
                .findByOutboundPlanOrderId(outboundPlanOrderId);
            List<PickingOrderDetail> pickingOrderDetails = records.stream().map(preAllocatedRecord ->
                new PickingOrderDetail().setSkuId(preAllocatedRecord.getSkuId())
                    .setOutboundOrderPlanId(outboundPlanOrderId)
                    .setBatchAttributes(preAllocatedRecord.getBatchAttributes())
                    .setQtyRequired(preAllocatedRecord.getQtyPreAllocated())
                    .setSkuBatchStockId(preAllocatedRecord.getSkuBatchStockId())
                    .setOutboundOrderPlanDetailId(preAllocatedRecord.getOutboundPlanOrderDetailId())).toList();

            return new PickingOrder()
                .setWarehouseCode(outboundWave.getWarehouseCode())
                .setPickingOrderNo(OrderNoGenerator.generationPickingOrderNo())
                .setWaveNo(outboundWave.getWaveNo())
                .setDetails(pickingOrderDetails);
        }).toList();
    }

    @Override
    public List<PickingOrderAssignedResult> assignOrders(PickingOrderHandlerContext pickingOrderHandlerContext) {
        List<IPickingOrderAssignPlugin> pickingOrderAssignPlugins = pluginUtils.getExtractObject(IPickingOrderAssignPlugin.class);

        List<Long> skuBatchIds = pickingOrderHandlerContext.getPickingOrders().stream().flatMap(v ->
            v.getDetails().stream().map(PickingOrderDTO.PickingOrderDetailDTO::getSkuBatchStockId)).toList();
        List<ContainerStockDTO> containerStockDTOS = stockApi.getContainerStockBySkuBatchStockIds(skuBatchIds);
        pickingOrderHandlerContext.setContainerStocks(containerStockDTOS);
        if (CollectionUtils.isNotEmpty(pickingOrderAssignPlugins)) {
            return pickingOrderAssignPlugins.iterator().next().doOperation(pickingOrderHandlerContext);
        }

        return pickingOrderAlgoApi.assignOrders(pickingOrderHandlerContext);
    }

    @Override
    public List<OperationTaskDTO> allocateStocks(PickingOrderHandlerContext pickingOrderHandlerContext) {
        List<IPickingOrderAllocateStockPlugin> pickingOrderAllocateStockPlugins = pluginUtils
            .getExtractObject(IPickingOrderAllocateStockPlugin.class);
        if (CollectionUtils.isNotEmpty(pickingOrderAllocateStockPlugins)) {
            return pickingOrderAllocateStockPlugins.iterator().next().doOperation(pickingOrderHandlerContext);
        }

        return pickingOrderAlgoApi.allocateStocks(pickingOrderHandlerContext);
    }

}
