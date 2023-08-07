package com.swms.wms.api.inbound;

import com.swms.wms.api.inbound.dto.AcceptRecordDTO;
import jakarta.validation.Valid;

public interface IAcceptOrderApi {

    void accept(@Valid AcceptRecordDTO acceptRecord);
}
