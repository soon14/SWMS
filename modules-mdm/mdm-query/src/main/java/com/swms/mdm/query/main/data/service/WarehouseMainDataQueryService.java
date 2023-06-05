package com.swms.mdm.query.main.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swms.mdm.query.main.data.parameter.WarehouseSearchParam;
import com.swms.mdm.query.main.data.vo.WarehouseMainDataVO;

public interface WarehouseMainDataQueryService {

    IPage<WarehouseMainDataVO> search(WarehouseSearchParam warehouseSearchParam);
}
