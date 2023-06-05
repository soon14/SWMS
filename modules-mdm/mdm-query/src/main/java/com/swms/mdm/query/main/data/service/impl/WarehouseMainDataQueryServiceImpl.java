package com.swms.mdm.query.main.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swms.mdm.query.main.data.mapper.WarehouseMainDataMapper;
import com.swms.mdm.query.main.data.parameter.WarehouseSearchParam;
import com.swms.mdm.query.main.data.service.WarehouseMainDataQueryService;
import com.swms.mdm.query.main.data.vo.WarehouseMainDataVO;
import com.swms.utils.utils.PaginationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseMainDataQueryServiceImpl implements WarehouseMainDataQueryService {

    @Autowired
    private WarehouseMainDataMapper warehouseMainDataMapper;

    @Override
    public IPage<WarehouseMainDataVO> search(WarehouseSearchParam warehouseSearchParam) {
        IPage<?> page = new Page<>(PaginationContext.getPageNum(), PaginationContext.getPageSize());
        return warehouseMainDataMapper.search(page, warehouseSearchParam);
    }
}
