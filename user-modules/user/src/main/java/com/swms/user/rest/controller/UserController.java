package com.swms.user.rest.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.swms.user.repository.entity.Role;
import com.swms.user.repository.entity.UserRole;
import com.swms.user.repository.model.UserHasRole;
import com.swms.user.rest.common.BaseResource;
import com.swms.user.rest.common.PageResult;
import com.swms.user.rest.param.CommonParam;
import com.swms.user.rest.param.menu.NavigationVo;
import com.swms.user.rest.param.user.UserAddParam;
import com.swms.user.rest.param.user.UserPageParam;
import com.swms.user.rest.param.user.UserResetPasswordParam;
import com.swms.user.rest.param.user.UserRoleFetchParam;
import com.swms.user.rest.param.user.UserUpdateParam;
import com.swms.user.rest.param.user.UserUpdateStatusParam;
import com.swms.user.service.MenuService;
import com.swms.user.service.RoleService;
import com.swms.user.service.UserRoleService;
import com.swms.user.service.UserService;
import com.swms.user.service.model.AuthUserInfo;
import com.swms.user.utils.Response;
import com.swms.user.api.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 用户资源接口
 *
 * @author sws
 * @version 1.0
 * @since 2020-07-24
 */
@RestController
@RequestMapping(BaseResource.API + "user")
@Api(tags = "用户接口")
@AllArgsConstructor
@Slf4j
public class UserController extends BaseResource {

    private final UserService userService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;
    private final MenuService menuService;

    @PostMapping("/pageQuery")
    @ApiOperation("分页条件查询用户(内部测试用)")
    public Object pageQuery(@RequestBody @Valid UserPageParam param) {
        return Response.builder().data(userService.getPage(param)).build();
    }

    @PostMapping("/search")
    @ApiOperation(value = "分页条件查询用户(前端使用)", response = UserHasRole.class)
    public Object search(@RequestParam Integer pageIndex,
                         @RequestParam Integer pageSize,
                         @RequestBody @Valid UserPageParam param) {
        param.setPageSize(pageSize);
        param.setCurrentPage(pageIndex);
        IPage<UserHasRole> page = userService.getPage(param);
        return Response.builder().data(PageResult.convert(page)).build();
    }

    @PostMapping("/add")
    @ApiOperation("添加用户")
    public Object add(@RequestBody @Valid UserAddParam param) throws Exception {
        userService.addUser(param);
        return Response.builder().build();
    }

    @PostMapping("/getRoleList")
    @ApiOperation(value = "查询启用的角色（修改用户时, 查询所有启用的角色）", response = Role.class)
    public Object getRoleList() {
        LambdaQueryWrapper<Role> wrapper = Wrappers.<Role>lambdaQuery()
            .eq(Role::getStatus, 1)
            .orderByDesc(Role::getGmtCreated);
        List<Role> list = roleService.list(wrapper);
        return Response.builder().data(list).build();
    }

    @PostMapping("/getRoleByUser")
    @ApiOperation(value = "查询用户所属角色（修改用户时, 查询当前用户所属的角色）", response = UserRole.class)
    public Object getRoleByUser(@RequestBody @Valid UserRoleFetchParam param) {
        return Response.builder().data(userRoleService.getByUserId(param.getUserId())).build();
    }

    @PostMapping("/update")
    @ApiOperation("修改用户")
    public Object update(@RequestBody @Valid UserUpdateParam param) throws Exception {
        userService.updateUser(param);
        return Response.builder().build();
    }

    @PostMapping("/updateStatus")
    @ApiOperation("修改用户状态")
    public Object updateStatus(@RequestBody UserUpdateStatusParam param) throws Exception {
        userService.updateStatus(param.getUserId(), param.getStatus());
        return Response.builder().build();
    }


    @PostMapping("/resetPassword")
    @ApiOperation("重置密码")
    public Object resetPassword(@RequestBody @Valid UserResetPasswordParam param) throws Exception {
        userService.resetPassword(param.getUserId(), param.getPassword());
        return Response.builder().build();
    }

    @PostMapping("/delete")
    @ApiOperation("删除用户")
    public Object deleteUser(@RequestBody CommonParam param) throws Exception {
        for (Long id : param.getIds()) {
            userService.delete(id);
        }
        return Response.builder().build();
    }

    @PostMapping("/info")
    @ApiOperation(value = "获取当前用户信息", response = AuthUserInfo.class)
    public Object getUserInfo() throws Exception {
        AuthUserInfo authUserInfo = userService.getAuthUserInfo();
        return Response.builder().data(authUserInfo).build();
    }

    @PostMapping("/nav")
    @ApiOperation(value = "获取当前用户角色的菜单", response = NavigationVo.class)
    public Object getCurrentUserNavInfo() {
        Set<String> roleCodes = UserContext.getCurrentRoleCodes();
        return Response.builder().data(menuService.getUserNav(roleCodes)).build();
    }

    @PostMapping("/logout")
    @ApiOperation("退出登录")
    public Object logout() {
        return Response.builder().build();
    }

    @GetMapping("test")
    public Object test(@RequestParam String param) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return param;
    }

    @PostMapping("/sync")
    @ApiOperation("同步用户-支持新增，修改和删除")
    public Object sync(@RequestBody @Valid UserAddParam param) throws Exception {
        userService.syncUser(param);
        return Response.builder().build();
    }
}
