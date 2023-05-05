package com.swms.user.rest.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.swms.user.repository.entity.Menu;
import com.swms.user.rest.common.BaseResource;
import com.swms.user.rest.param.CommonParam;
import com.swms.user.rest.param.menu.MenuAddParam;
import com.swms.user.rest.param.menu.MenuUpdateStatusParam;
import com.swms.user.rest.param.menu.MenuUpdatedParam;
import com.swms.user.service.MenuService;
import com.swms.user.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    @PostMapping("/delete")
    @ApiOperation("删除菜单")
    public Object delete(@RequestBody @Valid CommonParam param) {
        param.getIds().forEach(u -> {
            menuService.removeMenuById(u);
        });
        return Response.builder().build();
    }

    @PostMapping("/getMenus")
    @ApiOperation("getMenus")
    public Object getMenus() {
        LambdaQueryWrapper<Menu> wrapper = Wrappers.<Menu>lambdaQuery()
            .eq(Menu::getType, 3);
        List<Menu> list = menuService.list(wrapper);
        return list;
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
