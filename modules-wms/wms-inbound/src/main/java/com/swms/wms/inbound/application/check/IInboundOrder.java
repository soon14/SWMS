package com.swms.wms.inbound.application.check;

import com.swms.wms.api.inbound.constants.InboundOrderTypeEnum;

import java.util.List;

public interface IInboundOrder<T> {

    String getWarehouseCode();

    String getOwnerCode();

    List<T> getDetails();

    String getCustomerOrderNo();

    InboundOrderTypeEnum getInboundOrderType();

}
