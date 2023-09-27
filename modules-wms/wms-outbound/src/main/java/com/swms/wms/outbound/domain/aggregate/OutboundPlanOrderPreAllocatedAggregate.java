package com.swms.wms.outbound.domain.aggregate;

import com.google.common.collect.Lists;
import com.swms.mdm.api.config.dto.BatchAttributeConfigDTO;
import com.swms.wms.outbound.application.event.OutboundPlanOrderSubscribe;
import com.swms.wms.outbound.domain.entity.OutboundPlanOrder;
import com.swms.wms.outbound.domain.entity.OutboundPlanOrderDetail;
import com.swms.wms.outbound.domain.entity.OutboundPreAllocatedRecord;
import com.swms.wms.outbound.domain.repository.OutboundPlanOrderRepository;
import com.swms.wms.outbound.domain.repository.OutboundPreAllocatedRecordRepository;
import com.swms.wms.api.stock.IStockApi;
import com.swms.wms.api.stock.constants.StockLockTypeEnum;
import com.swms.wms.api.stock.dto.SkuBatchAttributeDTO;
import com.swms.wms.api.stock.dto.SkuBatchStockDTO;
import com.swms.wms.api.stock.dto.SkuBatchStockLockDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OutboundPlanOrderPreAllocatedAggregate {

    @Autowired
    private OutboundPlanOrderRepository outboundPlanOrderRepository;
    @Autowired
    private OutboundPreAllocatedRecordRepository preAllocatedRecordRepository;
    @Autowired
    private IStockApi stockApi;

    @Transactional(rollbackFor = Exception.class)
    public boolean preAllocate(OutboundPlanOrder outboundPlanOrder, OutboundPlanOrderSubscribe.PreAllocateCache preAllocateCache) {

        List<OutboundPreAllocatedRecord> planPreAllocatedRecords = Lists.newArrayList();
        outboundPlanOrder.getDetails().forEach(detail -> {

            List<SkuBatchAttributeDTO> skuBatchAttributeDTOS = match(preAllocateCache.getSkuBatchAttributeMap().get(detail.getSkuId()),
                preAllocateCache.getSkuBatchAttributeConfigMap().get(preAllocateCache.getSkuCategoryMap().get(detail.getSkuId())),
                detail.getBatchAttributes());

            List<SkuBatchStockDTO> skuBatchStocks = filterSkuBatchStock(skuBatchAttributeDTOS, preAllocateCache.getSkuBatchStocks());

            planPreAllocatedRecords.addAll(preAllocate(detail, skuBatchStocks));
        });

        boolean preAllocateResult = outboundPlanOrder.preAllocate(planPreAllocatedRecords);
        outboundPlanOrderRepository.saveOutboundPlanOrder(outboundPlanOrder);

        if (!preAllocateResult) {
            return false;
        }

        List<SkuBatchStockLockDTO> skuBatchStockLockDTOS = planPreAllocatedRecords.stream().map(preAllocatedRecord -> {
            SkuBatchStockLockDTO skuBatchStockLockDTO = new SkuBatchStockLockDTO();
            skuBatchStockLockDTO.setSkuBatchStockId(preAllocatedRecord.getSkuBatchStockId());
            skuBatchStockLockDTO.setLockQty(preAllocatedRecord.getQtyPreAllocated());
            skuBatchStockLockDTO.setLockType(StockLockTypeEnum.OUTBOUND);
            skuBatchStockLockDTO.setOrderDetailId(preAllocatedRecord.getOutboundPlanOrderDetailId());
            return skuBatchStockLockDTO;
        }).toList();
        stockApi.lockSkuBatchStock(skuBatchStockLockDTOS);

        preAllocatedRecordRepository.saveAll(planPreAllocatedRecords);
        return true;
    }

    private List<SkuBatchStockDTO> filterSkuBatchStock(List<SkuBatchAttributeDTO> skuBatchAttributeDTOS, List<SkuBatchStockDTO> skuBatchStocks) {

        Set<Long> skuBatchAttributeIds = skuBatchAttributeDTOS.stream().map(SkuBatchAttributeDTO::getId).collect(Collectors.toSet());
        return skuBatchStocks.stream().filter(v -> skuBatchAttributeIds.contains(v.getSkuBatchAttributeId())).toList();
    }

    private List<SkuBatchAttributeDTO> match(List<SkuBatchAttributeDTO> skuBatchAttributeDTOS,
                                             BatchAttributeConfigDTO batchAttributeConfigDTO, Map<String, Object> batchAttributes) {

        if (CollectionUtils.isEmpty(skuBatchAttributeDTOS)) {
            return Collections.emptyList();
        }

        if (batchAttributeConfigDTO == null) {
            return skuBatchAttributeDTOS;
        }
        return skuBatchAttributeDTOS.stream().filter(v -> v.match(batchAttributeConfigDTO, batchAttributes)).toList();
    }

    private List<OutboundPreAllocatedRecord> preAllocate(OutboundPlanOrderDetail detail, List<SkuBatchStockDTO> skuBatchStocks) {

        List<OutboundPreAllocatedRecord> preAllocatedRecords = Lists.newArrayList();

        int qtyRequired = detail.getQtyRequired();

        for (SkuBatchStockDTO skuBatchStockDTO : skuBatchStocks.stream().filter(v -> v.getAvailableQty() > 0).toList()) {
            if (qtyRequired < 1) {
                break;
            }
            int preAllocated = Math.min(skuBatchStockDTO.getAvailableQty(), qtyRequired);
            qtyRequired -= preAllocated;
            skuBatchStockDTO.setAvailableQty(skuBatchStockDTO.getAvailableQty() - qtyRequired);

            OutboundPreAllocatedRecord preAllocatedRecord = new OutboundPreAllocatedRecord()
                .setSkuBatchStockId(skuBatchStockDTO.getId())
                .setSkuId(skuBatchStockDTO.getSkuId())
                .setBatchAttributes(detail.getBatchAttributes())
                .setOutboundPlanOrderId(detail.getOutboundPlanOrderId())
                .setOutboundPlanOrderDetailId(detail.getId())
                .setQtyPreAllocated(preAllocated);

            preAllocatedRecords.add(preAllocatedRecord);
        }
        return preAllocatedRecords;
    }

}
