package com.swms.station.business.model;

import com.swms.wms.api.warehouse.constants.WorkLocationTypeEnum;
import com.swms.wms.api.warehouse.dto.ContainerLayoutDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * ArrivedContainer wouldn't pass to WMS system , it will be restored to redis and memory .
 * if Station system crashes then it will be got from redis when the system is restarted.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArrivedContainer {

    private ContainerLayoutDTO containerLayout;

    private String containerCode;
    private String containerType;
    private String face;
    private Integer rotationAngle;
    private String locationCode;

    private String workLocationCode;
    private WorkLocationTypeEnum workLocationType;
//    private List<Slot> slots;

    // a set of locations that the container can be put into.
    // it is a logical definition.
    // like robotCode is the groupCode of robot, cache shelf code is the groupCode of cache shelf.
    private String groupCode;

    private String robotCode;
    private String robotType;
    private Integer level;
    private Integer bay;

    private boolean empty;

    /**
     * 0: unprocessed ,1: processing , 2: processed
     */
    private Integer processStatus;

    /**
     * when container arrived , it's contains other attributes. like height, weight, temperature, etc.
     */
    private Map<String, Object> containerAttributes;
}
