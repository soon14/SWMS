package com.swms.mdm.query.main.data.mapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swms.mdm.query.main.data.parameter.WarehouseSearchParam;
import com.swms.mdm.query.main.data.vo.WarehouseMainDataVO;
import org.apache.ibatis.annotations.Param;

public interface WarehouseMainDataMapper {
    IPage<WarehouseMainDataVO> search(IPage<?> page, @Param("param") WarehouseSearchParam warehouseSearchParam);
}
