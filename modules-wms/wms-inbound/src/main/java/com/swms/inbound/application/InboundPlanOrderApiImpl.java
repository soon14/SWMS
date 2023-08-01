package com.swms.inbound.application;

import com.swms.inbound.domain.entity.InboundPlanOrder;
import com.swms.inbound.domain.repository.InboundPlanOrderRepository;
import com.swms.inbound.domain.service.InboundPlanOrderService;
import com.swms.inbound.domain.transfer.InboundPlanOrderTransfer;
import com.swms.utils.constants.RedisConstants;
import com.swms.utils.exception.WmsException;
import com.swms.utils.exception.code_enum.CommonErrorDescEnum;
import com.swms.utils.lock.DistributeLock;
import com.swms.wms.api.inbound.IInboundPlanOrderApi;
import com.swms.wms.api.inbound.dto.AcceptRecordDTO;
import com.swms.wms.api.inbound.dto.InboundPlanOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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

        inboundPlanOrderService.validateInboundPlanOrder(inboundPlanOrder);

        boolean lock = distributeLock.acquireLock(RedisConstants.INBOUND_PLAN_ORDER_ADD_LOCK + inboundPlanOrder.getCustomerOrderNo(), 3000L);
        if (!lock) {
            throw WmsException.throwWmsException(CommonErrorDescEnum.REPEAT_REQUEST);
        }

        try {
            inboundPlanOrderService.validateRepeatCustomerOrderNo(inboundPlanOrder);
            inboundPlanOrderRepository.saveOrderAndDetail(inboundPlanOrder);
        } finally {
            distributeLock.releaseLock(RedisConstants.INBOUND_PLAN_ORDER_ADD_LOCK + inboundPlanOrder.getCustomerOrderNo());
        }

    }
}
