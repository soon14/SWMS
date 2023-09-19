package com.swms.outbound.domain.aggregate;

import com.google.common.collect.Lists;
import com.swms.mdm.api.config.dto.BatchAttributeConfigDTO;
import com.swms.outbound.application.event.OutboundPlanOrderSubscribe;
import com.swms.outbound.domain.entity.OutboundOrderPlanPreAllocatedRecord;
import com.swms.outbound.domain.entity.OutboundPlanOrder;
import com.swms.outbound.domain.entity.OutboundPlanOrderDetail;
import com.swms.outbound.domain.repository.OutboundPreAllocatedRecordRepository;
import com.swms.outbound.domain.repository.OutboundPlanOrderRepository;
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
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class OutboundPlanOrderPreAllocatedAggregate {

    @Autowired
    private OutboundPlanOrderRepository outboundPlanOrderRepository;
    @Autowired
    private OutboundPreAllocatedRecordRepository preAllocatedRecordRepository;
    @Autowired
    private IStockApi iStockApi;

    @Transactional(rollbackFor = Exception.class)
    public void preAllocate(OutboundPlanOrder outboundPlanOrder, OutboundPlanOrderSubscribe.PreAllocateCache preAllocateCache) {

        List<OutboundOrderPlanPreAllocatedRecord> planPreAllocatedRecords = Lists.newArrayList();
        outboundPlanOrder.getDetails().forEach(detail -> {

            List<SkuBatchAttributeDTO> skuBatchAttributeDTOS = match(preAllocateCache.getSkuBatchAttributeMap().get(detail.getSkuId()),
                preAllocateCache.getSkuBatchAttributeConfigMap().get(preAllocateCache.getSkuCategoryMap().get(detail.getSkuId())),
                detail.getBatchAttributes());

            List<SkuBatchStockDTO> skuBatchStocks = filterSkuBatchStock(skuBatchAttributeDTOS, preAllocateCache.getSkuBatchStocks());

            planPreAllocatedRecords.addAll(preAllocate(detail, skuBatchStocks));
        });

        List<SkuBatchStockLockDTO> skuBatchStockLockDTOS = planPreAllocatedRecords.stream().map(preAllocatedRecord -> {
            SkuBatchStockLockDTO skuBatchStockLockDTO = new SkuBatchStockLockDTO();
            skuBatchStockLockDTO.setSkuBatchStockId(preAllocatedRecord.getSkuBatchStockId());
            skuBatchStockLockDTO.setLockQty(preAllocatedRecord.getQtyPreAllocated());
            skuBatchStockLockDTO.setLockType(StockLockTypeEnum.OUTBOUND);
            skuBatchStockLockDTO.setOrderDetailId(preAllocatedRecord.getOutboundPlanOrderDetailId());
            return skuBatchStockLockDTO;
        }).toList();
        iStockApi.lockSkuBatchStock(skuBatchStockLockDTOS);

        preAllocatedRecordRepository.saveAll(planPreAllocatedRecords);

        outboundPlanOrder.preAllocateDone();
        outboundPlanOrderRepository.saveOutboundPlanOrder(outboundPlanOrder);
    }

    private List<SkuBatchStockDTO> filterSkuBatchStock(List<SkuBatchAttributeDTO> skuBatchAttributeDTOS, List<SkuBatchStockDTO> skuBatchStocks) {

        Set<Long> skuBatchAttributeIds = skuBatchAttributeDTOS.stream().map(SkuBatchAttributeDTO::getId).collect(Collectors.toSet());
        return skuBatchStocks.stream().filter(v -> skuBatchAttributeIds.contains(v.getSkuBatchAttributeId())).toList();
    }

    private List<SkuBatchAttributeDTO> match(List<SkuBatchAttributeDTO> skuBatchAttributeDTOS,
                                             BatchAttributeConfigDTO batchAttributeConfigDTO, TreeMap<String, Object> batchAttributes) {

        if (CollectionUtils.isEmpty(skuBatchAttributeDTOS)) {
            return Collections.emptyList();
        }

        if (batchAttributeConfigDTO == null) {
            return skuBatchAttributeDTOS;
        }
        return skuBatchAttributeDTOS.stream().filter(v -> v.match(batchAttributeConfigDTO, batchAttributes)).toList();
    }

    private List<OutboundOrderPlanPreAllocatedRecord> preAllocate(OutboundPlanOrderDetail detail, List<SkuBatchStockDTO> skuBatchStocks) {

        List<OutboundOrderPlanPreAllocatedRecord> preAllocatedRecords = Lists.newArrayList();

        int qtyRequired = detail.getQtyRequired();

        for (SkuBatchStockDTO skuBatchStockDTO : skuBatchStocks.stream().filter(v -> v.getAvailableQty() > 0).toList()) {
            if (qtyRequired < 1) {
                break;
            }
            int preAllocated = Math.min(skuBatchStockDTO.getAvailableQty(), qtyRequired);
            qtyRequired -= preAllocated;
            skuBatchStockDTO.setAvailableQty(skuBatchStockDTO.getAvailableQty() - qtyRequired);

            OutboundOrderPlanPreAllocatedRecord preAllocatedRecord = new OutboundOrderPlanPreAllocatedRecord()
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
