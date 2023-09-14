package com.swms.outbound.domain.service;

import com.swms.mdm.api.main.data.dto.SkuMainDataDTO;
import com.swms.outbound.application.event.OutboundPlanOrderSubscribe;
import com.swms.outbound.domain.aggregate.OutboundWaveAggregate;
import com.swms.outbound.domain.entity.OutboundOrderPlanPreAllocatedRecord;
import com.swms.outbound.domain.entity.OutboundPlanOrder;
import com.swms.outbound.domain.validator.ValidateResult;
import com.swms.wms.api.outbound.dto.OutboundPlanOrderDTO;

import java.util.List;
import java.util.Set;

public interface OutboundPlanOrderService {

    void beforeDoCreation(OutboundPlanOrderDTO outboundPlanOrderDTO);

    ValidateResult<Set<SkuMainDataDTO>> validate(OutboundPlanOrder outboundPlanOrder);

    void afterDoCreation(OutboundPlanOrder outboundPlanOrder);

    List<OutboundWaveAggregate> wavePicking();

    void syncValidate(OutboundPlanOrder outboundPlanOrder);

}
