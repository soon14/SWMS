package com.swms.wms.inbound.domain.service.impl;

import static com.swms.common.utils.exception.code_enum.InboundErrorDescEnum.INBOUND_NOT_ALLOWED_MULTI_ARRIVALS;
import static com.swms.common.utils.exception.code_enum.InboundErrorDescEnum.INBOUND_OVER_ACCEPT_ERROR;

import com.google.common.collect.Lists;
import com.swms.common.utils.exception.WmsException;
import com.swms.wms.inbound.domain.entity.AcceptOrder;
import com.swms.wms.inbound.domain.entity.AcceptOrderDetail;
import com.swms.wms.inbound.domain.entity.InboundPlanOrder;
import com.swms.wms.inbound.domain.entity.InboundPlanOrderDetail;
import com.swms.wms.inbound.domain.repository.InboundPlanOrderRepository;
import com.swms.wms.inbound.domain.service.AcceptOrderService;
import com.swms.mdm.api.config.IParameterConfigApi;
import com.swms.mdm.api.config.constants.ParameterCodeEnum;
import com.swms.wms.api.inbound.constants.AcceptOrderStatusEnum;
import com.swms.wms.api.inbound.dto.AcceptRecordDTO;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Setter
public class AcceptOrderServiceImpl implements AcceptOrderService {

    @Autowired
    private InboundPlanOrderRepository inboundPlanOrderRepository;

    @DubboReference
    private IParameterConfigApi parameterConfigApi;

    @Override
    public InboundPlanOrder findAcceptInboundPlanOrder(AcceptRecordDTO acceptRecord) {
        InboundPlanOrder inboundPlanOrder = null;
        InboundPlanOrderDetail inboundPlanOrderDetail = null;

        if (acceptRecord.getInboundPlanOrderId() != null) {
            inboundPlanOrder = inboundPlanOrderRepository.findById(acceptRecord.getInboundPlanOrderId());

            inboundPlanOrderDetail = inboundPlanOrder.getDetails()
                .stream()
                .filter(v -> Objects.equals(v.getId(), acceptRecord.getInboundPlanOrderDetailId()))
                .findFirst().orElseThrow();
        } else if (StringUtils.isNotEmpty(acceptRecord.getBoxNo())) {
            inboundPlanOrder = inboundPlanOrderRepository.findByBoxNo(acceptRecord.getBoxNo(), acceptRecord.getWarehouseCode());

            inboundPlanOrderDetail = inboundPlanOrder.getDetails()
                .stream()
                .filter(v -> Objects.equals(v.getBoxNo(), acceptRecord.getBoxNo())
                    && Objects.equals(v.getSkuCode(), acceptRecord.getSkuCode()))
                .findFirst().orElseThrow();
        }

        assert inboundPlanOrder != null;
        inboundPlanOrder.setDetails(Lists.newArrayList(inboundPlanOrderDetail));

        return inboundPlanOrder;
    }

    @Override
    public void validateOverAccept(AcceptRecordDTO acceptRecord, List<AcceptOrder> acceptOrders,
                                   InboundPlanOrderDetail inboundPlanOrderDetail, InboundPlanOrder inboundPlanOrder) {

        List<AcceptOrderDetail> acceptOrderDetailDTOS = acceptOrders.stream()
            .flatMap(v -> v.getDetails().stream())
            .filter(v -> Objects.equals(v.getInboundPlanOrderDetailId(), inboundPlanOrderDetail.getId()))
            .toList();

        boolean allowOverAccept = parameterConfigApi.getBooleanParameter(ParameterCodeEnum.INBOUND_OVER_ACCEPT,
            inboundPlanOrder.getOwnerCode(), inboundPlanOrder.getInboundOrderType());
        if (allowOverAccept) {
            return;
        }

        Integer acceptedQty = acceptOrderDetailDTOS
            .stream().map(AcceptOrderDetail::getQtyAccepted).reduce(Integer::sum).orElse(0);

        if (acceptRecord.getQtyAccepted() + acceptedQty > inboundPlanOrderDetail.getQtyRestocked()) {
            throw WmsException.throwWmsException(INBOUND_OVER_ACCEPT_ERROR, acceptRecord.getSkuCode());
        }
    }

    @Override
    public void validateMultipleArrivals(List<AcceptOrder> acceptOrders, InboundPlanOrder inboundPlanOrder) {
        boolean allowMultipleArrivals = parameterConfigApi.getBooleanParameter(ParameterCodeEnum.INBOUND_ALLOW_MULTIPLE_ARRIVALS,
            inboundPlanOrder.getOwnerCode(), inboundPlanOrder.getInboundOrderType());

        if (allowMultipleArrivals) {
            return;
        }

        if (CollectionUtils.isNotEmpty(acceptOrders)
            && acceptOrders.stream().anyMatch(v -> v.getAcceptOrderStatus() == AcceptOrderStatusEnum.AUDITED)) {
            throw WmsException.throwWmsException(INBOUND_NOT_ALLOWED_MULTI_ARRIVALS, inboundPlanOrder.getOrderNo());
        }
    }
}
