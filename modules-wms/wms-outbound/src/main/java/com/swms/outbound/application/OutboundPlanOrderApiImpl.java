package com.swms.outbound.application;

import com.swms.common.utils.constants.RedisConstants;
import com.swms.distribute.lock.DistributeLock;
import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.outbound.domain.entity.OutboundPlanOrder;
import com.swms.outbound.domain.repository.OutboundPlanOrderRepository;
import com.swms.outbound.domain.service.OutboundPlanOrderService;
import com.swms.outbound.domain.transfer.OutboundPlanOrderTransfer;
import com.swms.outbound.domain.validator.IValidator;
import com.swms.outbound.domain.validator.ValidateResult;
import com.swms.wms.api.outbound.IOutboundPlanOrderApi;
import com.swms.wms.api.outbound.dto.OutboundPlanOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Service
@Validated
public class OutboundPlanOrderApiImpl implements IOutboundPlanOrderApi {

    @Autowired
    private OutboundPlanOrderRepository outboundPlanOrderRepository;

    @Autowired
    private OutboundPlanOrderService outboundPlanOrderService;

    @Autowired
    private OutboundPlanOrderTransfer outboundPlanOrderTransfer;

    @Autowired
    private DistributeLock distributeLock;

    @Override
    public void createOutboundPlanOrder(OutboundPlanOrderDTO outboundPlanOrderDTO) {

        outboundPlanOrderService.beforeDoCreation(outboundPlanOrderDTO);

        OutboundPlanOrder outboundPlanOrder = outboundPlanOrderTransfer.toDO(outboundPlanOrderDTO);
        outboundPlanOrder.initialize();

        ValidateResult<Set<SkuMainDataDTO>> result = outboundPlanOrderService.validate(outboundPlanOrder);
        outboundPlanOrder.initSkuId(result.getResult(IValidator.ValidatorName.SKU));

        distributeLock.acquireLockIfThrows(RedisConstants.OUTBOUND_PLAN_ORDER_ADD_LOCK + outboundPlanOrder.getCustomerOrderNo());

        OutboundPlanOrder savedOrder;
        try {
            outboundPlanOrderService.syncValidate(outboundPlanOrder);
            savedOrder = outboundPlanOrderRepository.saveOutboundPlanOrder(outboundPlanOrder);
        } finally {
            distributeLock.releaseLock(RedisConstants.OUTBOUND_PLAN_ORDER_ADD_LOCK + outboundPlanOrder.getCustomerOrderNo());
        }

        outboundPlanOrderService.afterDoCreation(savedOrder);
    }

}
