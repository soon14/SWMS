package com.swms.wms.basic.warehouse;

import com.swms.wms.BaseTest;
import com.swms.wms.api.basic.constants.LocationTypeEnum;
import com.swms.wms.basic.warehouse.controller.LocationController;
import com.swms.wms.basic.warehouse.controller.parameter.AisleLocationVO;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class LocationApiTest extends BaseTest {

    @Autowired
    private LocationController locationController;

    @Test
    void testCreateLocations() {

        AisleLocationVO locationVO = new AisleLocationVO();
        locationVO.setWarehouseCode("123");
        locationVO.setLocationType(LocationTypeEnum.RACK);
        locationVO.setWarehouseAreaId(466669475480080384L);

        AisleLocationVO.LocationDesc locationDesc = AisleLocationVO.LocationDesc.builder()
            .aisleCode("01").shelfLine("A").shelfNumber(2).singleEntrance(true)
            .warehouseLogicId(1L).containerSpecCode("12213").build();
        locationVO.setLocationDescList(Lists.newArrayList(locationDesc));

        locationController.create(locationVO);
    }
}
