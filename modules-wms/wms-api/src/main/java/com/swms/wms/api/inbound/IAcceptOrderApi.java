package com.swms.wms.api.inbound;

import com.swms.wms.api.inbound.dto.AcceptRecordDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface IAcceptOrderApi {

    void accept(@Valid AcceptRecordDTO acceptRecord);

    void audit(@NotNull Long acceptOrderId);
}
