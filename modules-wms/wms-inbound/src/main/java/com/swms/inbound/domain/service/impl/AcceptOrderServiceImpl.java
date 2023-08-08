package com.swms.inbound.domain.service.impl;

import static com.swms.common.utils.exception.code_enum.InboundErrorDescEnum.INBOUND_OVER_ACCEPT_ERROR;

import com.google.common.collect.Lists;
import com.swms.common.utils.exception.WmsException;
import com.swms.inbound.domain.entity.AcceptOrder;
import com.swms.inbound.domain.entity.InboundPlanOrder;
import com.swms.inbound.domain.repository.InboundPlanOrderRepository;
import com.swms.inbound.domain.service.AcceptOrderService;
import com.swms.wms.api.inbound.dto.AcceptOrderDetailDTO;
import com.swms.wms.api.inbound.dto.AcceptRecordDTO;
import com.swms.wms.api.inbound.dto.InboundPlanOrderDetailDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AcceptOrderServiceImpl implements AcceptOrderService {

    @Autowired
    private InboundPlanOrderRepository inboundPlanOrderRepository;

    @Override
    public InboundPlanOrder findAcceptInboundPlanOrder(AcceptRecordDTO acceptRecord) {
        InboundPlanOrder inboundPlanOrder = null;
        InboundPlanOrderDetailDTO inboundPlanOrderDetailDTO = null;

        if (acceptRecord.getInboundPlanOrderId() != null) {
            inboundPlanOrder = inboundPlanOrderRepository.findById(acceptRecord.getInboundPlanOrderId());

            inboundPlanOrderDetailDTO = inboundPlanOrder.getInboundPlanOrderDetails()
                .stream()
                .filter(v -> Objects.equals(v.getInboundPlanOrderId(), acceptRecord.getInboundPlanOrderDetailId()))
                .findFirst().orElseThrow();
        }

        if (StringUtils.isNotEmpty(acceptRecord.getBoxNo())) {
            inboundPlanOrder = inboundPlanOrderRepository.findByBoxNo(acceptRecord.getBoxNo(), acceptRecord.getWarehouseCode());

            inboundPlanOrderDetailDTO = inboundPlanOrder.getInboundPlanOrderDetails()
                .stream()
                .filter(v -> Objects.equals(v.getBoxNo(), acceptRecord.getBoxNo())
                    && Objects.equals(v.getSkuCode(), acceptRecord.getSkuCode()))
                .findFirst().orElseThrow();
        }

        assert inboundPlanOrder != null;
        inboundPlanOrder.setInboundPlanOrderDetails(Lists.newArrayList(inboundPlanOrderDetailDTO));

        return inboundPlanOrder;
    }

    @Override
    public void validateOverAccept(AcceptRecordDTO acceptRecord, List<AcceptOrder> acceptOrders,
                                   InboundPlanOrderDetailDTO inboundPlanOrderDetailDTO) {

        Integer acceptedQty = acceptOrders.stream().flatMap(v -> v.getAcceptOrderDetails().stream())
            .filter(v -> Objects.equals(v.getInboundPlanOrderDetailId(), inboundPlanOrderDetailDTO.getId()))
            .map(AcceptOrderDetailDTO::getQtyAccepted).reduce(Integer::sum).orElse(0);

        if (acceptRecord.getQtyAccepted() + acceptedQty > inboundPlanOrderDetailDTO.getQtyRestocked()) {
            throw WmsException.throwWmsException(INBOUND_OVER_ACCEPT_ERROR, acceptRecord.getSkuCode());
        }
    }
}
