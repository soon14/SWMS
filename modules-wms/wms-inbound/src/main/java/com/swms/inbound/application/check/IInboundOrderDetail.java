package com.swms.inbound.application.check;

import java.util.Map;

public interface IInboundOrderDetail {

    Long getId();

    Long getSkuId();

    String getSkuCode();

    String getBoxNo();

    String getSkuName();

    Map<String, Object> getBatchAttributes();

    Map<String, Object> getExtendFields();
}
