package com.swms.inbound.domain.aggregate;

import com.google.common.base.Preconditions;
import com.swms.inbound.domain.entity.AcceptOrder;
import com.swms.inbound.domain.entity.InboundPlanOrder;
import com.swms.inbound.domain.repository.AcceptOrderRepository;
import com.swms.inbound.domain.repository.InboundPlanOrderRepository;
import com.swms.inbound.domain.repository.PutAwayTaskRepository;
import com.swms.inbound.domain.repository.ReceiveOrderRepository;
import com.swms.inbound.domain.transfer.AcceptOrderTransfer;
import com.swms.inbound.domain.transfer.InboundPlanOrderTransfer;
import com.swms.inbound.domain.transfer.PutAwayTaskTransfer;
import com.swms.inbound.domain.transfer.ReceiveOrderTransfer;
import com.swms.wms.api.inbound.dto.AcceptRecordDTO;
import com.swms.wms.api.inbound.dto.InboundPlanOrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InboundProcessor {

    private InboundPlanOrderRepository inboundPlanOrderRepository;
    private InboundPlanOrderTransfer inboundPlanOrderTransfer;

    private ReceiveOrderRepository receiveOrderRepository;
    private ReceiveOrderTransfer receiveOrderTransfer;

    private AcceptOrderRepository acceptOrderRepository;
    private AcceptOrderTransfer acceptOrderTransfer;

    private PutAwayTaskRepository putAwayTaskRepository;
    private PutAwayTaskTransfer putAwayTaskTransfer;


    public void createInboundPlanOrder(InboundPlanOrderDTO inboundPlanOrderDTO) {
        inboundPlanOrderRepository.save(inboundPlanOrderTransfer.toInboundPlanOrder(inboundPlanOrderDTO));
    }

    public void cancelInboundPlanOrder(Long inboundPlanOrderId) {
        InboundPlanOrder inboundPlanOrder = inboundPlanOrderRepository.findById(inboundPlanOrderId);
        Preconditions.checkState(inboundPlanOrder != null, "cancelled inbound plan order error, because order is not exist.");
        inboundPlanOrder.cancel();
        inboundPlanOrderRepository.save(inboundPlanOrder);
    }

    public void beginReceiving(Long inboundPlanOrderId) {
        InboundPlanOrder inboundPlanOrder = inboundPlanOrderRepository.findById(inboundPlanOrderId);
        inboundPlanOrder.beginReceiving();
        inboundPlanOrderRepository.save(inboundPlanOrder);
    }

    public void pushToReceive(Long inboundPlanOrderId) {
        InboundPlanOrder inboundPlanOrder = inboundPlanOrderRepository.findById(inboundPlanOrderId);
        inboundPlanOrder.endReceiving();
        inboundPlanOrderRepository.save(inboundPlanOrder);

        receiveOrderRepository.save(receiveOrderTransfer.toReceiveOrder(inboundPlanOrder));
    }

    public void pushToAccept(Long inboundPlanOrderId) {
        InboundPlanOrder inboundPlanOrder = inboundPlanOrderRepository.findById(inboundPlanOrderId);
        inboundPlanOrder.endAccept();
        inboundPlanOrderRepository.save(inboundPlanOrder);

        acceptOrderRepository.save(acceptOrderTransfer.toAcceptOrder(inboundPlanOrder));
    }

    public void beginAccepting(Long acceptOrderId) {
        AcceptOrder acceptOrder = acceptOrderRepository.findById(acceptOrderId);
        acceptOrder.beginAccepting();
        acceptOrderRepository.save(acceptOrder);
    }

    public void accept(AcceptRecordDTO acceptRecord) {
        AcceptOrder acceptOrder = acceptOrderRepository.findById(acceptRecord.getAcceptOrderId());
        AcceptOrder.AcceptOrderDetail acceptOrderDetail = acceptOrder.
            accept(acceptRecord.getAcceptQty(), acceptRecord.getAcceptOrderDetailId(), acceptRecord.getBatchAttributes());
        acceptOrderRepository.save(acceptOrder);

        acceptOrderRepository.saveDetail(acceptOrderDetail);
    }

    public void completeAccepting(Long acceptOrderId) {
        AcceptOrder acceptOrder = acceptOrderRepository.findById(acceptOrderId);
        acceptOrder.completeAccepting();
        acceptOrderRepository.save(acceptOrder);

        putAwayTaskRepository.save(putAwayTaskTransfer.toPutAwayTask(acceptOrder));
    }

}
