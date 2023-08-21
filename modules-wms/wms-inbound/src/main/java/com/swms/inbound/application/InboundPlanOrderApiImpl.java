package com.swms.inbound.application;

import com.swms.common.utils.constants.RedisConstants;
import com.swms.common.utils.exception.WmsException;
import com.swms.common.utils.exception.code_enum.CommonErrorDescEnum;
import com.swms.distribute.lock.DistributeLock;
import com.swms.inbound.domain.entity.InboundPlanOrder;
import com.swms.inbound.domain.repository.InboundPlanOrderRepository;
import com.swms.inbound.domain.service.InboundPlanOrderService;
import com.swms.inbound.domain.transfer.InboundPlanOrderTransfer;
import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.wms.api.inbound.IInboundPlanOrderApi;
import com.swms.wms.api.inbound.dto.InboundPlanOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Service
@Validated
public class InboundPlanOrderApiImpl implements IInboundPlanOrderApi {

    @Autowired
    private InboundPlanOrderService inboundPlanOrderService;

    @Autowired
    private InboundPlanOrderTransfer inboundPlanOrderTransfer;

    @Autowired
    private InboundPlanOrderRepository inboundPlanOrderRepository;

    @Autowired
    private DistributeLock distributeLock;

    @Override
    public void createInboundPlanOrder(InboundPlanOrderDTO inboundPlanOrderDTO) {
        InboundPlanOrder inboundPlanOrder = inboundPlanOrderTransfer.toInboundPlanOrder(inboundPlanOrderDTO);
        inboundPlanOrder.initial();

        Set<SkuMainDataDTO> skuMainDataDTOS = inboundPlanOrderService.validateInboundPlanOrder(inboundPlanOrder);
        inboundPlanOrder.initSkuId(skuMainDataDTOS);

        distributeLock.acquireLockIfThrows(RedisConstants.INBOUND_PLAN_ORDER_ADD_LOCK + inboundPlanOrder.getCustomerOrderNo(), 3000L);

        try {
            inboundPlanOrderService.validateRepeatCustomerOrderNo(inboundPlanOrder);
            inboundPlanOrderService.validateRepeatBoxNo(inboundPlanOrder);
            inboundPlanOrderRepository.saveOrderAndDetail(inboundPlanOrder);
        } finally {
            distributeLock.releaseLock(RedisConstants.INBOUND_PLAN_ORDER_ADD_LOCK + inboundPlanOrder.getCustomerOrderNo());
        }

    }
}
