package com.swms.wms.stock.controller;

import com.swms.utils.http.Response;
import com.swms.wms.api.stock.IStockApi;
import com.swms.wms.stock.controller.parameter.FreezeStockVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("containerStock")
@Validated
public class ContainerStockController {

    @Autowired
    private IStockApi iStockApi;

    @PostMapping("freezeContainerStock")
    public Object freezeContainerStock(@RequestBody @Valid FreezeStockVO freezeStockVO) {
        iStockApi.freezeContainerStock(freezeStockVO.getId(), freezeStockVO.getQty());
        return Response.success();
    }

    @PostMapping("unFreezeContainerStock")
    public Object unFreezeContainerStock(@RequestBody @Valid FreezeStockVO freezeStockVO) {
        iStockApi.unFreezeContainerStock(freezeStockVO.getId(), freezeStockVO.getQty());
        return Response.success();
    }
}
