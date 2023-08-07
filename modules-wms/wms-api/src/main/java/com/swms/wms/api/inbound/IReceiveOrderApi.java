package com.swms.wms.api.inbound;

import com.swms.wms.api.inbound.dto.ReceiveOrderDTO;
import jakarta.validation.Valid;

public interface IReceiveOrderApi {

    void createReceiveOrder(@Valid ReceiveOrderDTO receiveOrder);
}
