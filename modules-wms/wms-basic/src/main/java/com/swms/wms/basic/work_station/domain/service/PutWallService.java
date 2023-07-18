package com.swms.wms.basic.work_station.domain.service;

import com.swms.wms.basic.work_station.domain.entity.PutWall;

public interface PutWallService {
    boolean checkDisablePutWall(PutWall putWall);

    boolean checkUpdatePutWall(PutWall putWall);

}
