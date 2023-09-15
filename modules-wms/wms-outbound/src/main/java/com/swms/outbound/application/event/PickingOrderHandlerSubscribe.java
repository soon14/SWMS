package com.swms.outbound.application.event;

import static com.swms.common.utils.constants.RedisConstants.NEW_PICKING_ORDER_IDS;

import com.google.common.eventbus.Subscribe;
import com.swms.common.utils.utils.RedisUtils;
import com.swms.outbound.domain.entity.PickingOrder;
import com.swms.outbound.domain.repository.PickingOrderRepository;
import com.swms.wms.api.basic.IWorkStationApi;
import com.swms.wms.api.basic.dto.WorkStationDTO;
import com.swms.wms.api.outbound.event.NewPickingOrdersEvent;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class PickingOrderHandlerSubscribe {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private PickingOrderRepository pickingOrderRepository;

    @Autowired
    private IWorkStationApi workStationApi;

    @Subscribe
    public void onEvent(@Valid NewPickingOrdersEvent event) {

        String redisKey = NEW_PICKING_ORDER_IDS + event.getWarehouseCode();

        redisUtils.pushAll(redisKey, event.getPickingOrderNos());
        List<String> allPickingOrderNos = redisUtils.getList(redisKey);

        List<PickingOrder> pickingOrders = pickingOrderRepository.findByPickingOrderNos(allPickingOrderNos);
        List<WorkStationDTO> workStationDTOS = workStationApi.getByWarehouseCode(event.getWarehouseCode());


    }
}
