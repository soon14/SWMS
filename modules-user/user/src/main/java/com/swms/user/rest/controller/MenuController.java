package com.swms.user.rest.controller;

import com.swms.user.rest.common.BaseResource;
import com.swms.user.rest.param.menu.MenuAddParam;
import com.swms.user.rest.param.menu.MenuUpdateStatusParam;
import com.swms.user.rest.param.menu.MenuUpdatedParam;
import com.swms.user.service.MenuService;
import com.swms.common.utils.http.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单接口
 *
 * @author sws
 * @version 1.0
 */
@RestController
@RequestMapping(BaseResource.API + "menu")
@Api(tags = "菜单接口")
@AllArgsConstructor
@Slf4j
public class MenuController extends BaseResource {

    private final MenuService menuService;

    @PostMapping("/add")
    @ApiOperation(value = "添加菜单")
    public Object add(@RequestBody @Valid MenuAddParam param) {
        menuService.addMenu(param);
        return Response.builder().build();
    }

    @PostMapping("/update")
    @ApiOperation("修改菜单")
    public Object update(@RequestBody @Valid MenuUpdatedParam param) {
        menuService.updateMenu(param);
        return Response.builder().build();
    }

    @PostMapping("/updateStatus")
    @ApiOperation("修改菜单状态")
    public Object updateStatus(@RequestBody @Valid MenuUpdateStatusParam param) {
        menuService.updateStatus(param.getMenuId(), param.getEnable());
        return Response.builder().build();
    }


    @DeleteMapping("/{id}")
    @ApiOperation("删除菜单")
    public Object delete(@PathVariable @Valid Long id) {
        menuService.removeMenuById(id);
        return Response.builder().build();
    }

    @PostMapping("/addList")
    @ApiOperation(value = "批量添加菜单")
    public Object addList(@RequestBody @Valid List<MenuAddParam> paramList) {
        int count = 0;
        for (MenuAddParam menuAddParam : paramList) {
            menuService.addMenu(menuAddParam);
            count++;
        }
        return Response.builder().data(count).build();
    }
}
