package com.swms.user.rest.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Preconditions;
import com.swms.user.api.UserContext;
import com.swms.user.repository.entity.LoginLog;
import com.swms.user.rest.common.BaseResource;
import com.swms.user.rest.common.PageResult;
import com.swms.user.rest.param.loginlog.LoginLogPageParam;
import com.swms.user.service.LoginLogService;
import com.swms.utils.http.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录日志接口配置
 *
 * @author sws
 * @version 1.0
 */
@RestController
@RequestMapping(BaseResource.API + "loginLog")
@Api(tags = "登录日志接口")
@AllArgsConstructor
@Slf4j
public class LoginLogController extends BaseResource {

    private final LoginLogService loginLogService;

    @PostMapping("/search")
    @ApiOperation(value = "分页查询登录日志(前端使用)", response = LoginLog.class)
    public Object search(@RequestBody(required = false) @Valid LoginLogPageParam param) {
        if (param == null) {
            param = new LoginLogPageParam();
        }
        return Response.builder().data(PageResult.convert(page(param))).build();
    }

    @PostMapping("/currentSearch")
    @ApiOperation(value = "分页查询当前登录用户的登录日志(前端使用)", response = LoginLog.class)
    public Object searchByCurrentUser(@RequestBody @Valid LoginLogPageParam param) {
        return Response.builder().data(PageResult.convert(currentPage(param))).build();
    }

    private Page<LoginLog> page(LoginLogPageParam param) {
        LambdaQueryWrapper<LoginLog> wrapper = Wrappers.<LoginLog>lambdaQuery();
        if (!StrUtil.isEmpty(param.getUsername())) {
            wrapper.like(LoginLog::getUsername, param.getUsername());
        }
        if (param.getLoginResult() != null) {
            wrapper.like(LoginLog::getLoginResult, param.getLoginResult());
        }

        wrapper.orderByDesc(LoginLog::getGmtLoginTime);
        Page<LoginLog> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        page = loginLogService.page(page, wrapper);

        return page;
    }

    private Page<LoginLog> currentPage(LoginLogPageParam param) {
        String currentUsername = UserContext.getCurrentUser();
        Preconditions.checkNotNull(currentUsername);

        LambdaQueryWrapper<LoginLog> wrapper = Wrappers.<LoginLog>lambdaQuery();
        wrapper.eq(LoginLog::getUsername, currentUsername);
        wrapper.orderByDesc(LoginLog::getGmtLoginTime);
        Page<LoginLog> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        page = loginLogService.page(page, wrapper);

        return page;
    }
}
