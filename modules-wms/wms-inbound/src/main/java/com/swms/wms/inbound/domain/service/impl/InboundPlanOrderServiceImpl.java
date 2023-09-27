package com.swms.wms.inbound.domain.service.impl;

import static com.swms.common.utils.exception.code_enum.InboundErrorDescEnum.INBOUND_BOX_NO_EXIST;
import static com.swms.common.utils.exception.code_enum.InboundErrorDescEnum.INBOUND_CST_ORDER_NO_REPEATED;

import com.swms.common.utils.exception.WmsException;
import com.swms.wms.inbound.application.check.AbstractInboundCheckBase;
import com.swms.wms.inbound.application.check.IInboundOrder;
import com.swms.wms.inbound.application.check.IInboundOrderDetail;
import com.swms.wms.inbound.domain.entity.InboundPlanOrder;
import com.swms.wms.inbound.domain.repository.InboundPlanOrderRepository;
import com.swms.wms.inbound.domain.service.InboundPlanOrderService;
import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.wms.api.inbound.constants.InboundOrderTypeEnum;
import jakarta.annotation.Resource;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Setter
public class InboundPlanOrderServiceImpl extends AbstractInboundCheckBase implements InboundPlanOrderService {

    @Resource
    private InboundPlanOrderRepository inboundPlanOrderRepository;

    @Override
    public Set<SkuMainDataDTO> validateInboundPlanOrder(List<InboundPlanOrder> inboundPlanOrder) {
        super.checkWarehouseAndOwner(inboundPlanOrder);
        checkRepeatCustomerOrderNo(inboundPlanOrder);
        checkRepeatBoxNo(inboundPlanOrder);
        return super.checkSkuCodes(inboundPlanOrder);
    }


    @Override
    public void checkRepeatBoxNo(Collection<?> inboundPlanOrder) {
        final Map<String, Set<String>> warhouseCodesAndBoxNoMap = inboundPlanOrder.stream()
            .map(k -> ((IInboundOrder<?>) k))
            .collect(Collectors.groupingBy(IInboundOrder::getWarehouseCode, Collectors.mapping(k -> ((IInboundOrder<?>) k).getDetails()
                .stream()
                .map(m -> ((IInboundOrderDetail) m))
                .map(IInboundOrderDetail::getBoxNo)
                .collect(Collectors.toSet()), Collectors.flatMapping(Collection::stream, Collectors.toSet()))));

        warhouseCodesAndBoxNoMap.forEach((warehouseCode, boxNos) -> {
            boolean boxExists = inboundPlanOrderRepository.existByBoxNos(boxNos, warehouseCode);
            if (boxExists) {
                throw WmsException.throwWmsException(INBOUND_BOX_NO_EXIST);
            }
        });
    }

    @Override
    public void checkRepeatCustomerOrderNo(Collection<?> iInboundOrder) {
        final Set<String> customerOrderNos = iInboundOrder.stream()
            .map(k -> ((IInboundOrder<?>) k))
            .map(IInboundOrder::getCustomerOrderNo)
            .collect(Collectors.toSet());

        List<InboundPlanOrder> inboundPlanOrders = inboundPlanOrderRepository.findInboundPlanOrderByCustomerOrderNos(customerOrderNos);

        final Set<String> dataCustomerOrderNos = inboundPlanOrders.stream()
            .map(InboundPlanOrder::getCustomerOrderNo)
            .collect(Collectors.toSet());

        customerOrderNos.removeIf(dataCustomerOrderNos::contains);

        if (CollectionUtils.isNotEmpty(customerOrderNos)) {
            throw WmsException.throwWmsException(INBOUND_CST_ORDER_NO_REPEATED, customerOrderNos);
        }
    }

    @Override
    public InboundOrderTypeEnum getInboundType() {
        return InboundOrderTypeEnum.PLAN_ORDER;
    }

    @Override
    public void checkOrderExists(Collection<?> iInboundOrder) {
        //do nothing
    }
}
