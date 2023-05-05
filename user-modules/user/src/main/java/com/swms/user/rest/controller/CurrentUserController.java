package com.swms.user.rest.controller;

import com.google.common.base.Preconditions;
import com.swms.user.rest.common.BaseResource;
import com.swms.user.rest.common.PageResult;
import com.swms.user.rest.param.user.CurrentUserInfoUpdatedParam;
import com.swms.user.rest.param.user.UserUpdatePasswordParam;
import com.swms.user.service.CurrentUserService;
import com.swms.user.service.MenuService;
import com.swms.user.service.model.MenuTree;
import com.swms.user.utils.Response;
import com.swms.user.api.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;


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

    @PostMapping("/getMenuTree")
    @ApiOperation("查询菜单树(内部测试用)")
    public Object getMenuTree() throws Exception {
        List<MenuTree> menuTrees = menuService.getMenuTreeByUser(UserContext.getCurrentUser());
        return Response.builder().data(menuTrees).build();
    }

    @PostMapping("/searchMenuTree")
    @ApiOperation(value = "查询菜单树(前端使用)", response = MenuTree.class)
    public Object searchMenuTree(@RequestParam Integer pageIndex,
                                 @RequestParam Integer pageSize) throws Exception {
        if (pageIndex == null || pageIndex <= 0) {
            pageIndex = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }
        PageResult result = new PageResult();
        List<MenuTree> menuTrees = menuService.getMenuTreeByUser(UserContext.getCurrentUser());
        if (null != menuTrees && menuTrees.size() >= pageSize) {
            int fromIndex = (pageIndex - 1) * pageSize;
            int toIndex = pageIndex * pageSize;
            if (fromIndex > menuTrees.size() - 1) {
                fromIndex = menuTrees.size() - 1;
            }
            if (toIndex > menuTrees.size()) {
                toIndex = menuTrees.size();
            }
            result.setResults(menuTrees.subList(fromIndex, toIndex));
        } else {
            result.setResults(menuTrees);
        }
        if (menuTrees != null) {
            result.setTotal(String.valueOf(menuTrees.size()));
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
