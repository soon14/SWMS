package com.swms.inbound.application;

import com.google.common.collect.Lists;
import com.swms.common.utils.constants.RedisConstants;
import com.swms.distribute.lock.DistributeLock;
import com.swms.inbound.domain.entity.AcceptOrder;
import com.swms.inbound.domain.entity.InboundPlanOrder;
import com.swms.inbound.domain.repository.AcceptOrderRepository;
import com.swms.inbound.domain.service.AcceptOrderService;
import com.swms.inbound.domain.transfer.AcceptOrderTransfer;
import com.swms.wms.api.inbound.IAcceptOrderApi;
import com.swms.wms.api.inbound.constants.AcceptOrderStatusEnum;
import com.swms.wms.api.inbound.dto.AcceptRecordDTO;
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

    @Override
    public void accept(AcceptRecordDTO acceptRecord) {

        InboundPlanOrder inboundPlanOrder = acceptOrderService.findAcceptInboundPlanOrder(acceptRecord);

        AcceptOrder acceptOrder = acceptOrderTransfer.toDO(inboundPlanOrder, inboundPlanOrder.getInboundPlanOrderDetails(),
            Lists.newArrayList(acceptRecord));

        acceptOrder.initial();

        distributeLock.acquireLockIfThrows(RedisConstants.INBOUND_ACCEPT_OPERATE_LOCK + inboundPlanOrder.getId(), 3000L);

        try {
            List<AcceptOrder> acceptOrders = acceptOrderRepository.findByInboundPlanOrderId(inboundPlanOrder.getId());
            AcceptOrder existsAcceptOrder = acceptOrders.stream()
                .filter(v -> v.getAcceptOrderStatus() == AcceptOrderStatusEnum.NEW).findFirst().orElse(null);

            acceptOrderService.validateOverAccept(acceptRecord, acceptOrders,
                inboundPlanOrder.getInboundPlanOrderDetails().iterator().next());

            if (existsAcceptOrder != null) {
                existsAcceptOrder.setAcceptOrderDetails(acceptOrder.getAcceptOrderDetails());
                existsAcceptOrder.initial();
                acceptOrderRepository.saveOrderAndDetail(existsAcceptOrder);
            } else {
                acceptOrderRepository.saveOrderAndDetail(acceptOrder);
            }

        } finally {
            distributeLock.releaseLock(RedisConstants.INBOUND_PLAN_ORDER_ADD_LOCK + inboundPlanOrder.getCustomerOrderNo());
        }

    }
}
