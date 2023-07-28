package com.swms.user.rest.controller;


import com.swms.user.repository.entity.User;
import com.swms.user.repository.entity.UserRole;
import com.swms.user.repository.model.UserHasRole;
import com.swms.user.rest.common.BaseResource;
import com.swms.user.rest.param.user.UserAddParam;
import com.swms.user.rest.param.user.UserUpdateParam;
import com.swms.user.rest.param.user.UserUpdateStatusParam;
import com.swms.user.service.UserRoleService;
import com.swms.user.service.UserService;
import com.swms.utils.http.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    private final UserRoleService userRoleService;

    @GetMapping("/{id}")
    @ApiOperation(value = "查询用户", response = UserHasRole.class, responseContainer = "List")
    public Object getUserById(@Valid @PathVariable Long id) {
        User user = userService.getById(id);
        List<UserRole> userRoles = userRoleService.getByUserId(id);

        UserHasRole userRole = new UserHasRole();
        BeanUtils.copyProperties(user, userRole);
        userRole.setRoleIds(StringUtils.join(userRoles.stream().map(UserRole::getRoleId).toList(), ","));
        return Response.builder().data(userRole).build();
    }

    @PostMapping("/add")
    @ApiOperation("添加用户")
    public Response<Object> add(@RequestBody @Valid UserAddParam param) {
        userService.addUser(param);
        return Response.builder().build();
    }

    @PostMapping("/update")
    @ApiOperation("修改用户")
    public Object update(@RequestBody @Valid UserUpdateParam param) {
        userService.updateUser(param);
        return Response.builder().build();
    }

    @PostMapping("/updateStatus")
    @ApiOperation("修改用户状态")
    public Object updateStatus(@RequestBody UserUpdateStatusParam param) {
        userService.updateStatus(param.getUserId(), param.getStatus());
        return Response.builder().build();
    }


    @PostMapping("/resetPassword/{id}")
    @ApiOperation("重置密码")
    public Object resetPassword(@PathVariable long id) {
        userService.resetPassword(id, "123456");
        return Response.builder().build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    public Object deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return Response.builder().build();
    }

    @PostMapping("/sync")
    @ApiOperation("同步用户-支持新增，修改和删除")
    public Object sync(@RequestBody @Valid UserAddParam param) {
        userService.syncUser(param);
        return Response.builder().build();
    }
}
