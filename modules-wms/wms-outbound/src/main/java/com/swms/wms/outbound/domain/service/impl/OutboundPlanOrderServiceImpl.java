package com.swms.wms.outbound.domain.service.impl;

import com.google.common.collect.Lists;
import com.swms.domain.event.DomainEventPublisher;
import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.wms.outbound.domain.entity.OutboundPlanOrder;
import com.swms.wms.outbound.domain.entity.OutboundPlanOrderDetail;
import com.swms.wms.outbound.domain.service.OutboundPlanOrderService;
import com.swms.wms.outbound.domain.transfer.OutboundPlanOrderTransfer;
import com.swms.wms.outbound.domain.validator.IValidator;
import com.swms.wms.outbound.domain.validator.ValidateResult;
import com.swms.wms.outbound.domain.validator.ValidatorFactory;
import com.swms.wms.outbound.domain.validator.impl.OwnerValidator;
import com.swms.wms.outbound.domain.validator.impl.SkuValidator;
import com.swms.wms.outbound.domain.validator.impl.WarehouseValidator;
import com.swms.plugin.extend.wms.outbound.IOutboundPlanOrderCreatePlugin;
import com.swms.plugin.sdk.extensions.OperationContext;
import com.swms.plugin.sdk.utils.PluginUtils;
import com.swms.wms.api.outbound.dto.OutboundPlanOrderDTO;
import com.swms.wms.api.outbound.event.NewOutboundPlanOrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class OutboundPlanOrderServiceImpl implements OutboundPlanOrderService {

    @Autowired
    private OutboundPlanOrderTransfer outboundPlanOrderTransfer;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Autowired
    private PluginUtils pluginUtils;

    @Override
    public void beforeDoCreation(OutboundPlanOrderDTO outboundPlanOrderDTO) {
        List<IOutboundPlanOrderCreatePlugin> outboundPlanOrderCreatePlugins = pluginUtils.getExtractObject(IOutboundPlanOrderCreatePlugin.class);
        outboundPlanOrderCreatePlugins.forEach(v -> v.beforeDoOperation(new OperationContext<>(outboundPlanOrderDTO)));
    }

    @Override
    public ValidateResult<Set<SkuMainDataDTO>> validate(OutboundPlanOrder outboundPlanOrder) {

        WarehouseValidator warehouseValidator = (WarehouseValidator) ValidatorFactory.getValidator(IValidator.ValidatorName.WAREHOUSE);
        warehouseValidator.validate(Lists.newArrayList(outboundPlanOrder.getWarehouseCode()));

        OwnerValidator ownerValidator = (OwnerValidator) ValidatorFactory.getValidator(IValidator.ValidatorName.OWNER);
        ownerValidator.validate(Lists.newArrayList(outboundPlanOrder.getOwnerCode()));

        SkuValidator skuValidator = (SkuValidator) ValidatorFactory.getValidator(IValidator.ValidatorName.SKU);
        List<String> skuCodes = outboundPlanOrder.getDetails().stream().map(OutboundPlanOrderDetail::getSkuCode).toList();
        Set<SkuMainDataDTO> skuMainDataDTOS = skuValidator.validate(new SkuValidator.SkuValidatorObject(skuCodes, outboundPlanOrder.getOwnerCode()));

        ValidateResult<Set<SkuMainDataDTO>> result = new ValidateResult<>();
        result.setResult(IValidator.ValidatorName.SKU, skuMainDataDTOS);
        return result;
    }

    @Override
    public void afterDoCreation(OutboundPlanOrder outboundPlanOrder) {
        domainEventPublisher.sendAsyncEvent(new NewOutboundPlanOrderEvent(outboundPlanOrder.getOrderNo()));

        List<IOutboundPlanOrderCreatePlugin> outboundPlanOrderCreatePlugins = pluginUtils.getExtractObject(IOutboundPlanOrderCreatePlugin.class);
        outboundPlanOrderCreatePlugins.forEach(v -> v.afterDoOperation(new OperationContext<>(outboundPlanOrderTransfer.toDTO(outboundPlanOrder))));
    }

    @Override
    public void syncValidate(OutboundPlanOrder outboundPlanOrder) {

    }


}
