package com.swms.wms.inbound.application.check;

import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;

import java.util.Collection;
import java.util.Set;

/**
 * iinbound检查
 *
 * @author krystal
 * @date 2023/08/22
 */
public interface IInboundCheck {
    /**
     * 检查仓库和所有者
     *
     * @param iInboundOrder 入库参数
     */
    void checkWarehouseAndOwner(Collection<?> iInboundOrder);

    /**
     * 检查sku码
     *
     * @param iInboundOrder 入库参数
     *
     * @return {@link Set}<{@link SkuMainDataDTO}>
     */
    Set<SkuMainDataDTO> checkSkuCodes(Collection<?> iInboundOrder);

    /**
     * 检查客户订单没有重复
     *
     * @param iInboundOrder 入库参数
     */
    void checkRepeatCustomerOrderNo(Collection<?> iInboundOrder);

    /**
     * 检查订单是否存在
     *
     * @param iInboundOrder 入库参数
     */
    void checkOrderExists(Collection<?> iInboundOrder);


    /**
     * 检查重复箱号
     *
     * @param iInboundOrder 入库参数
     */
    void checkRepeatBoxNo(Collection<?> iInboundOrder);

}
