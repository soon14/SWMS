package com.swms.wms.outbound.domain.service.impl;

import com.google.common.collect.Lists;
import com.swms.wms.outbound.domain.entity.OutboundPlanOrder;
import com.swms.wms.outbound.domain.service.OutboundWaveService;
import com.swms.wms.outbound.domain.transfer.OutboundPlanOrderTransfer;
import com.swms.plugin.extend.wms.outbound.IOutboundWavePickingPlugin;
import com.swms.plugin.sdk.utils.PluginUtils;
import com.swms.wms.api.outbound.dto.OutboundPlanOrderDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutboundWaveServiceImpl implements OutboundWaveService {

    @Autowired
    private PluginUtils pluginUtils;

    @Autowired
    private OutboundPlanOrderTransfer outboundPlanOrderTransfer;

    @Override
    public List<List<OutboundPlanOrder>> wavePickings(List<OutboundPlanOrder> outboundPlanOrders) {

        List<IOutboundWavePickingPlugin> outboundWavePickingPlugins = pluginUtils.getExtractObject(IOutboundWavePickingPlugin.class);

        if (CollectionUtils.isNotEmpty(outboundWavePickingPlugins)) {
            List<List<OutboundPlanOrderDTO>> outboundWaveDTOS = outboundWavePickingPlugins.iterator().next()
                .doOperation(outboundPlanOrderTransfer.toDTOs(outboundPlanOrders));
            return outboundPlanOrderTransfer.toDOList(outboundWaveDTOS);
        }

        List<List<OutboundPlanOrder>> orders = Lists.newArrayList();
        orders.add(outboundPlanOrders);
        return orders;
    }
}
