package com.swms.inbound.domain.service.impl;

import static com.swms.utils.exception.code_enum.InboundErrorDescEnum.INBOUND_CST_ORDER_NO_REPEATED;

import com.swms.inbound.domain.entity.InboundPlanOrder;
import com.swms.inbound.domain.repository.InboundPlanOrderRepository;
import com.swms.inbound.domain.service.InboundPlanOrderService;
import com.swms.mdm.api.main.data.IOwnerMainDataApi;
import com.swms.mdm.api.main.data.ISkuMainDataApi;
import com.swms.mdm.api.main.data.IWarehouseMainDataApi;
import com.swms.mdm.api.main.data.dto.OwnerMainDataDTO;
import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.mdm.api.main.data.dto.WarehouseMainDataDTO;
import com.swms.utils.exception.WmsException;
import com.swms.utils.exception.code_enum.MainDataErrorDescEnum;
import com.swms.wms.api.inbound.dto.InboundPlanOrderDetailDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InboundPlanOrderServiceImpl implements InboundPlanOrderService {

    @DubboReference
    private IWarehouseMainDataApi iWarehouseApi;

    @DubboReference
    private IOwnerMainDataApi iOwnerApi;

    @DubboReference
    private ISkuMainDataApi iSkuApi;

    @Autowired
    private InboundPlanOrderRepository inboundPlanOrderRepository;

    @Override
    public void validateInboundPlanOrder(InboundPlanOrder inboundPlanOrder) {
        WarehouseMainDataDTO warehouse = iWarehouseApi.getWarehouse(inboundPlanOrder.getWarehouseCode());
        if (warehouse == null) {
            throw WmsException.throwWmsException(MainDataErrorDescEnum.WAREHOUSE_CODE_NOT_EXIST, inboundPlanOrder.getWarehouseCode());
        }

        OwnerMainDataDTO owner = iOwnerApi.getOwner(inboundPlanOrder.getOwnerCode());
        if (owner == null) {
            throw WmsException.throwWmsException(MainDataErrorDescEnum.OWNER_CODE_NOT_EXIST, inboundPlanOrder.getOwnerCode());
        }

        Set<String> skuCodes = inboundPlanOrder.getInboundPlanOrderDetails().stream()
            .map(InboundPlanOrderDetailDTO::getSkuCode).collect(Collectors.toSet());
        Set<SkuMainDataDTO> existCodes = iSkuApi.getSkuMainData(skuCodes).stream()
            .filter(v -> StringUtils.equals(v.getOwnerCode(), inboundPlanOrder.getOwnerCode())).collect(Collectors.toSet());
        if (skuCodes.size() != existCodes.size()) {
            throw WmsException.throwWmsException(MainDataErrorDescEnum.SOME_SKU_CODE_NOT_EXIST);
        }
    }

    @Override
    public void validateRepeatCustomerOrderNo(InboundPlanOrder inboundPlanOrder) {
        List<InboundPlanOrder> inboundPlanOrders = inboundPlanOrderRepository.findByCustomerOrderNo(inboundPlanOrder.getCustomerOrderNo());
        if (CollectionUtils.isNotEmpty(inboundPlanOrders)) {
            throw WmsException.throwWmsException(INBOUND_CST_ORDER_NO_REPEATED, inboundPlanOrder.getCustomerOrderNo());
        }
    }
}
