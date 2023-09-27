package com.swms.wms.inbound.application.check;

import com.swms.common.utils.exception.WmsException;
import com.swms.common.utils.exception.code_enum.MainDataErrorDescEnum;
import com.swms.mdm.api.main.data.IOwnerMainDataApi;
import com.swms.mdm.api.main.data.ISkuMainDataApi;
import com.swms.mdm.api.main.data.IWarehouseMainDataApi;
import com.swms.mdm.api.main.data.dto.OwnerMainDataDTO;
import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.mdm.api.main.data.dto.WarehouseMainDataDTO;
import com.swms.wms.api.inbound.constants.InboundOrderTypeEnum;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 抽象入库检查基础
 *
 * @author krystal
 * @date 2023/08/22
 */
@Setter
public abstract class AbstractInboundCheckBase implements IInboundCheck {

    @DubboReference
    protected IWarehouseMainDataApi iWarehouseApi;

    @DubboReference
    protected IOwnerMainDataApi iOwnerApi;

    @DubboReference
    protected ISkuMainDataApi iSkuApi;


    @Override
    public void checkWarehouseAndOwner(Collection<?> iInboundOrders) {
        final Set<String> warehouseCodes = iInboundOrders.stream()
            .map(k -> ((IInboundOrder<?>) k))
            .map(IInboundOrder::getWarehouseCode)
            .collect(Collectors.toSet());

        final Set<String> ownerCodes = iInboundOrders.stream()
            .map(k -> ((IInboundOrder<?>) k))
            .map(IInboundOrder::getOwnerCode)
            .collect(Collectors.toSet());


        final Set<String> dataWarehouseCodes = iWarehouseApi.getWarehouses(warehouseCodes)
            .stream()
            .map(WarehouseMainDataDTO::getWarehouseCode)
            .collect(Collectors.toSet());

        warehouseCodes.removeIf(dataWarehouseCodes::contains);

        if (CollectionUtils.isNotEmpty(warehouseCodes)) {
            throw WmsException.throwWmsException(MainDataErrorDescEnum.WAREHOUSE_CODE_NOT_EXIST, warehouseCodes);
        }

        final Set<String> dataOwnerCodes = iOwnerApi.getOwners(ownerCodes)
            .stream()
            .map(OwnerMainDataDTO::getOwnerCode)
            .collect(Collectors.toSet());

        ownerCodes.removeIf(dataOwnerCodes::contains);
        if (CollectionUtils.isNotEmpty(ownerCodes)) {
            throw WmsException.throwWmsException(MainDataErrorDescEnum.OWNER_CODE_NOT_EXIST, ownerCodes);
        }
    }

    @Override
    public Set<SkuMainDataDTO> checkSkuCodes(Collection<?> iInboundOrder) {

        final Set<String> skuCodes = iInboundOrder.stream()
            .map(k -> ((IInboundOrder<?>) k))
            .map(IInboundOrder::getDetails)
            .flatMap(Collection::stream)
            .map(k -> ((IInboundOrderDetail) k))
            .map(IInboundOrderDetail::getSkuCode)
            .collect(Collectors.toSet());

        final Set<String> ownerCodes = iInboundOrder.stream()
            .map(k -> ((IInboundOrder<?>) k))
            .map(IInboundOrder::getOwnerCode)
            .collect(Collectors.toSet());

        Set<SkuMainDataDTO> skuMainDataDTOS = iSkuApi.getSkuMainData(skuCodes)
            .stream()
            .filter(v -> ownerCodes.contains(v.getOwnerCode()))
            .collect(Collectors.toSet());

        final Set<String> dataSkuCodes = skuMainDataDTOS.stream()
            .map(SkuMainDataDTO::getSkuCode)
            .collect(Collectors.toSet());

        skuCodes.removeIf(dataSkuCodes::contains);

        if (CollectionUtils.isNotEmpty(skuCodes)) {
            throw WmsException.throwWmsException(MainDataErrorDescEnum.SOME_SKU_CODE_NOT_EXIST, skuCodes);
        }
        return skuMainDataDTOS;
    }


    protected void checkRepeatCustomerOrderNoBase(Collection<?> iInboundOrder) {
        if (CollectionUtils.isNotEmpty(iInboundOrder)) {
            this.getInboundCheckProcessor(((IInboundOrder<?>) iInboundOrder.iterator().next()).getInboundOrderType())
                .ifPresent(iInboundCheck -> iInboundCheck.checkRepeatCustomerOrderNo(iInboundOrder));
        }
    }

    protected void checkOrderExistsBase(Collection<?> iInboundOrder) {
        if (CollectionUtils.isNotEmpty(iInboundOrder)) {
            this.getInboundCheckProcessor(((IInboundOrder<?>) iInboundOrder.iterator().next()).getInboundOrderType())
                .ifPresent(iInboundCheck -> iInboundCheck.checkOrderExists(iInboundOrder));
        }
    }

    protected void checkRepeatBoxNoBase(Collection<?> iInboundOrder) {
        if (CollectionUtils.isNotEmpty(iInboundOrder)) {
            this.getInboundCheckProcessor(((IInboundOrder<?>) iInboundOrder.iterator().next()).getInboundOrderType())
                .ifPresent(iInboundCheck -> iInboundCheck.checkRepeatBoxNo(iInboundOrder));
        }
    }

    private Optional<IInboundCheck> getInboundCheckProcessor(InboundOrderTypeEnum iInboundOrder) {
        return InboundCheckFactory.getInboundCheckProcessor(iInboundOrder);
    }

    /**
     * 入库订单类型
     *
     * @return {@link InboundOrderTypeEnum}
     */
    protected abstract InboundOrderTypeEnum getInboundType();

}
