package com.swms.wms.inbound.application;

import com.swms.distribute.lock.DistributeLock;
import com.swms.wms.inbound.domain.entity.ReceiveOrder;
import com.swms.wms.inbound.domain.repository.ReceiveOrderRepository;
import com.swms.wms.inbound.domain.service.ReceiveOrderService;
import com.swms.wms.inbound.domain.transfer.ReceiveOrderTransfer;
import com.swms.wms.api.inbound.IReceiveOrderApi;
import com.swms.wms.api.inbound.dto.ReceiveOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ReceiveOrderApiImpl implements IReceiveOrderApi {

    @Autowired
    private ReceiveOrderTransfer receiveOrderTransfer;

    @Autowired
    private ReceiveOrderService receiveOrderService;

    @Autowired
    private DistributeLock distributeLock;

    @Autowired
    private ReceiveOrderRepository receiveOrderRepository;

    @Override
    public void createReceiveOrder(ReceiveOrderDTO receiveOrderDTO) {

        ReceiveOrder receiveOrder = receiveOrderTransfer.toDO(receiveOrderDTO);
        receiveOrderService.validateReceiveOrder(receiveOrder);

        receiveOrderRepository.saveOrderAndDetail(receiveOrder);
    }
}
