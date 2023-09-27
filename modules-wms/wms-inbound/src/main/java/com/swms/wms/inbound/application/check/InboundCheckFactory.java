package com.swms.wms.inbound.application.check;

import com.swms.wms.api.inbound.constants.InboundOrderTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class InboundCheckFactory  {

    private static final Map<InboundOrderTypeEnum, AbstractInboundCheckBase> iInboundCheckProcessorMap = new EnumMap<>(InboundOrderTypeEnum.class);

    @Autowired
    private InboundCheckFactory(List<AbstractInboundCheckBase> inboundChecks) {
        for (AbstractInboundCheckBase inboundCheck : inboundChecks) {
            iInboundCheckProcessorMap.put(inboundCheck.getInboundType(), inboundCheck);
        }
    }

    public static Optional<IInboundCheck> getInboundCheckProcessor(InboundOrderTypeEnum inboundOrderTypeEnum) {
        if (inboundOrderTypeEnum == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(iInboundCheckProcessorMap.get(inboundOrderTypeEnum));
    }
}
