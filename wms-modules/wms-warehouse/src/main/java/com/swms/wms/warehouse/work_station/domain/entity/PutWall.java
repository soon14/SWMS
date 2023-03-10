package com.swms.wms.warehouse.work_station.domain.entity;

import com.swms.wms.warehouse.work_station.infrastructure.repository.IPutWallRepository;
import com.swms.wms.warehouse.work_station.infrastructure.repository.IPutWallSlotRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Definition: SomeWhere that operators put items.
 */
@Data
public class PutWall {

    private Long id;
    private String stationCode;
    private String putWallCode;
    private String putWallName;
    private List<PutWallSlot> putWallSlots;

}
