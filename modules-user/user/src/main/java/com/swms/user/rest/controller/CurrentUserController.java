package com.swms.user.rest.controller;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.swms.user.repository.entity.Menu;
import com.swms.user.repository.entity.Role;
import com.swms.user.rest.common.BaseResource;
import com.swms.user.rest.param.user.CurrentUserInfoUpdatedParam;
import com.swms.user.rest.param.user.UserUpdatePasswordParam;
import com.swms.user.service.CurrentUserService;
import com.swms.user.service.MenuService;
import com.swms.user.service.UserRoleService;
import com.swms.common.utils.http.Response;
import com.swms.common.utils.user.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author sws
 * @version 1.0
 */
@RestController
@RequestMapping(BaseResource.API + "currentUser")
@Api(tags = "当前认证的用户接口")
@RequiredArgsConstructor
@Slf4j
public class CurrentUserController extends BaseResource {

    private final CurrentUserService userService;
    private final MenuService menuService;

    private final UserRoleService userRoleService;

    @GetMapping("/getAuth")
    @ApiOperation("查询菜单树")
    public Object getAuth() throws Exception {
        List<Menu> menuTrees = menuService.getMenuTreeByUser(UserContext.getCurrentUser());
        Map<String, Menu> menuMap = menuTrees.stream().collect(Collectors.toMap(Menu::getTitle, v -> v));

        List<Role> roles = userRoleService.getByUserName(UserContext.getCurrentUser());
        Set<String> warehouseSet = roles.stream()
            .filter(v -> v.getWarehouseCodes() != null)
            .flatMap(v -> v.getWarehouseCodes().stream()).collect(Collectors.toSet());

        Map<String, Object> authMap = Maps.newHashMap();
        authMap.put("menus", menuMap);
        authMap.put("warehouses", StringUtils.join(warehouseSet, ","));

        return authMap;
    }

    @PostMapping("/searchMenuTree")
    @ApiOperation(value = "查询菜单树(前端使用)", response = Menu.class)
    public Object searchMenuTree(@RequestParam Integer pageIndex,
                                 @RequestParam Integer pageSize) throws Exception {
        if (pageIndex == null || pageIndex <= 0) {
            pageIndex = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }
        Map<String, Object> result = Maps.newHashMap();
        List<Menu> menuTrees = menuService.getMenuTreeByUser(UserContext.getCurrentUser());
        if (null != menuTrees && menuTrees.size() >= pageSize) {
            int fromIndex = (pageIndex - 1) * pageSize;
            int toIndex = pageIndex * pageSize;
            if (fromIndex > menuTrees.size() - 1) {
                fromIndex = menuTrees.size() - 1;
            }
            if (toIndex > menuTrees.size()) {
                toIndex = menuTrees.size();
            }
            result.put("results", menuTrees.subList(fromIndex, toIndex));
        } else {
            result.put("results", menuTrees);
        }
        if (menuTrees != null) {
            result.put("total", String.valueOf(menuTrees.size()));
        }
        return Response.builder().data(result).build();
    }

    @PostMapping("/password")
    @ApiOperation("修改密码")
    public Object updatePassword(@RequestBody @Valid UserUpdatePasswordParam param) throws Exception {
        Preconditions.checkState(Objects.equals(param.getNewPassword(), param.getConfirmNewPassword()));

        userService.updateCurrentUserPassword(UserContext.getCurrentUser(), param.getOldPassword(), param.getNewPassword());
        return Response.builder().build();
    }

    @PostMapping("/updateUserInfo")
    @ApiOperation("修改当前用户信息")
    public Object updateUserInfo(@RequestBody @Valid CurrentUserInfoUpdatedParam param) throws Exception {
        userService.updateInfo(UserContext.getCurrentUser(), param);
        return Response.builder().build();
    }
}
