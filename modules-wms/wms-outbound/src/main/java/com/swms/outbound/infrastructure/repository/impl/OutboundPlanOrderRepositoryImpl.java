package com.swms.outbound.infrastructure.repository.impl;

import com.swms.outbound.domain.entity.OutboundPlanOrder;
import com.swms.outbound.domain.entity.OutboundPlanOrderDetail;
import com.swms.outbound.domain.repository.OutboundPlanOrderRepository;
import com.swms.outbound.infrastructure.persistence.mapper.OutboundPlanOrderDetailPORepository;
import com.swms.outbound.infrastructure.persistence.mapper.OutboundPlanOrderPORepository;
import com.swms.outbound.infrastructure.persistence.po.OutboundPlanOrderDetailPO;
import com.swms.outbound.infrastructure.persistence.po.OutboundPlanOrderPO;
import com.swms.outbound.infrastructure.persistence.transfer.OutboundPlanOrderPOTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OutboundPlanOrderRepositoryImpl implements OutboundPlanOrderRepository {

    @Autowired
    private OutboundPlanOrderPORepository outboundPlanOrderPORepository;

    @Autowired
    private OutboundPlanOrderDetailPORepository outboundPlanOrderDetailPORepository;

    @Autowired
    private OutboundPlanOrderPOTransfer outboundPlanOrderPOTransfer;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OutboundPlanOrder saveOutboundPlanOrder(OutboundPlanOrder outboundPlanOrder) {
        OutboundPlanOrderPO outboundPlanOrderPO = outboundPlanOrderPORepository.save(outboundPlanOrderPOTransfer.toPO(outboundPlanOrder));

        List<OutboundPlanOrderDetailPO> outboundPlanOrderDetailPOS = outboundPlanOrderPOTransfer.toDetailPOS(outboundPlanOrder.getDetails());
        outboundPlanOrderDetailPOS.forEach(v -> v.setOutboundPlanOrderId(outboundPlanOrderPO.getId()));

        List<OutboundPlanOrderDetailPO> details = outboundPlanOrderDetailPORepository.saveAll(outboundPlanOrderDetailPOS);

        return outboundPlanOrderPOTransfer.toDO(outboundPlanOrderPO, details);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAll(List<OutboundPlanOrder> outboundPlanOrders) {
        outboundPlanOrderPORepository.saveAll(outboundPlanOrderPOTransfer.toPOS(outboundPlanOrders));
    }

    @Override
    public OutboundPlanOrder findByOrderNo(String orderNo) {
        OutboundPlanOrderPO outboundPlanOrderPO = outboundPlanOrderPORepository.findByOrderNo(orderNo);
        List<OutboundPlanOrderDetailPO> details = outboundPlanOrderDetailPORepository
            .findAllByOutboundPlanOrderId(outboundPlanOrderPO.getId());
        return outboundPlanOrderPOTransfer.toDO(outboundPlanOrderPO, details);
    }

    @Override
    public OutboundPlanOrder findById(Long orderId) {
        return null;
    }

    @Override
    public List<OutboundPlanOrder> findAllByIds(List<Long> orderIds) {

        List<OutboundPlanOrderPO> outboundPlanOrderPOS = outboundPlanOrderPORepository.findAllById(orderIds);
        return outboundPlanOrderPOS.stream().map(outboundPlanOrderPO -> {
            List<OutboundPlanOrderDetailPO> detailPOS = outboundPlanOrderDetailPORepository
                .findAllByOutboundPlanOrderId(outboundPlanOrderPO.getId());
            return outboundPlanOrderPOTransfer.toDO(outboundPlanOrderPO, detailPOS);
        }).toList();
    }

    @Override
    public List<OutboundPlanOrder> findByCustomerOrderNo(String customerOrderNo) {
        List<OutboundPlanOrderPO> outboundPlanOrderPOS = outboundPlanOrderPORepository.findAllByCustomerOrderNo(customerOrderNo);
        return outboundPlanOrderPOTransfer.toDOs((outboundPlanOrderPOS));
    }

    @Override
    public void saveOrderAndDetails(List<OutboundPlanOrder> outboundPlanOrders) {
        outboundPlanOrderPORepository.saveAll(outboundPlanOrderPOTransfer.toPOS(outboundPlanOrders));
        List<OutboundPlanOrderDetail> outboundPlanOrderDetails = outboundPlanOrders.stream().flatMap(v -> v.getDetails().stream()).toList();
        outboundPlanOrderDetailPORepository.saveAll(outboundPlanOrderPOTransfer.toDetailPOS(outboundPlanOrderDetails));
    }
}
