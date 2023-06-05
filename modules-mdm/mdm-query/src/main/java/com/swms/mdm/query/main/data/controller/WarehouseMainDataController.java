package com.swms.mdm.query.main.data.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swms.mdm.query.main.data.parameter.WarehouseSearchParam;
import com.swms.mdm.query.main.data.service.WarehouseMainDataQueryService;
import com.swms.mdm.query.main.data.vo.WarehouseMainDataVO;
import com.swms.utils.http.Response;
import com.swms.utils.utils.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("warehouseMainData")
public class WarehouseMainDataController {

    @Autowired
    private WarehouseMainDataQueryService queryService;

    @PostMapping(value = "search")
    public Object search(@RequestBody WarehouseSearchParam parameter) {
        IPage<WarehouseMainDataVO> queryResults = queryService.search(parameter);
        return Response.builder().data(PageHelper.covertIPage(queryResults)).build();
    }
}
