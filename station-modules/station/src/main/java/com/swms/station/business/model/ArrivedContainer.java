package com.swms.station.business.model;

import com.swms.wms.api.basic.constants.WorkLocationTypeEnum;
import com.swms.wms.api.basic.dto.ContainerLayoutDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    /**
     * base information
     */
    private String containerCode;
    private String face;
    private Integer rotationAngle;
    // container forward face
    private String forwardFace;

    /**
     * location
     */
    private String locationCode;
    private String workLocationCode;
    private WorkLocationTypeEnum workLocationType;
    // a set of locations that the container can be put into.
    // it is a logical definition.
    // like robotCode is the groupCode of robot, cache shelf code is the groupCode of cache shelf.
    private String groupCode;
    private String robotCode;
    private String robotType;
    private Integer level;
    private Integer bay;

    /**
     * status
     */
    private boolean empty;
    /**
     * 0: unprocessed ,1: processing , 2: processed
     */
    private Integer processStatus;

    /**
     * when container arrived , it's contains other attributes. like height, weight, temperature, etc.
     */
    private Map<String, Object> containerAttributes;

    /**
     * layout
     */
    private ContainerLayoutDTO containerLayout;

    /**
     * use configuration to get the rotation angle,identify two scan type. e.g. manual scan and equipment scan .
     * equipment scan used to container arrived and manual scan used to operator scan container.
     */
    public void setRotationAngle() {
        //TODO
    }
}
