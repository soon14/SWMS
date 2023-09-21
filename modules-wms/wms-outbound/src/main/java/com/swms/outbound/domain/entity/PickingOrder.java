package com.swms.outbound.domain.entity;

import com.swms.wms.api.outbound.constants.PickingOrderStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.MapUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@Accessors(chain = true)
public class PickingOrder {

    private Long id;

    private String warehouseCode;

    private String waveNo;

    private String pickingOrderNo;

    private PickingOrderStatusEnum pickingOrderStatus;

    private List<PickingOrderDetail> details;

    /**
     * one picking order can be assigned to multiple station slot
     * <p>
     * Key is the station id
     * Value is the put wall slot code
     */
    private Map<Long, String> assignedStationSlot;

    public void dispatch(Map<Long, String> assignedStationSlot) {
        if (MapUtils.isEmpty(assignedStationSlot)) {
            throw new IllegalStateException("assigned station slot cannot be empty");
        }
        this.assignedStationSlot = assignedStationSlot;
        this.pickingOrderStatus = PickingOrderStatusEnum.DISPATCHED;
    }

    public void picking(Integer operatedQty, Long detailId) {
        details.stream().filter(v -> v.getId().equals(detailId))
            .forEach(detail -> detail.setQtyActual(detail.getQtyActual() + operatedQty));

        if (details.stream().allMatch(v -> Objects.equals(v.getQtyRequired(), v.getQtyActual()))) {
            this.pickingOrderStatus = PickingOrderStatusEnum.PICKED;
        } else {
            this.pickingOrderStatus = PickingOrderStatusEnum.PICKING;
        }
    }
}
