//package com.swms.user.rest.controller;
//
//import cn.hutool.core.util.StrUtil;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.swms.user.repository.entity.OauthClientDetails;
//import com.swms.user.rest.common.BaseResource;
//import com.swms.user.rest.common.PageResult;
//import com.swms.user.rest.param.oauthclient.OauthClientFetchParam;
//import com.swms.user.rest.param.oauthclient.OauthClientPageParam;
//import com.swms.user.rest.param.oauthclient.OauthClientUpdatedParam;
//import com.swms.user.service.OauthClientDetailsService;
//import com.swms.utils.http.Response;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 授权客户端接口配置
// *
// * @author sws
// * @version 1.0
// */
//@RestController
//@RequestMapping(BaseResource.API + "oauthClient")
//@Api(tags = "授权客户端接口")
//@AllArgsConstructor
//@Slf4j
//public class OauthClientController extends BaseResource {
//
//    private final OauthClientDetailsService oauthClientService;
//
//
//    @PostMapping("/pageQuery")
//    @ApiOperation("分页查询授权客户端(内部测试用)")
//    public Object pageQuery(@RequestBody @Valid OauthClientPageParam param) {
//        LambdaQueryWrapper<OauthClientDetails> wrapper = Wrappers.<OauthClientDetails>lambdaQuery();
//        if (!StrUtil.isEmpty(param.getName())) {
//            wrapper.like(OauthClientDetails::getName, param.getName());
//        }
//        wrapper.orderByDesc(OauthClientDetails::getGmtCreated);
//        Page<OauthClientDetails> page = new Page<>(param.getCurrentPage(), param.getPageSize());
//        page = oauthClientService.page(page, wrapper);
//        return Response.builder().data(page).build();
//    }
//
//    @PostMapping("/search")
//    @ApiOperation(value = "分页条件查询用户(前端使用)", response = OauthClientDetails.class)
//    public Object search(@RequestParam Integer pageIndex,
//                         @RequestParam Integer pageSize,
//                         @RequestBody @Valid OauthClientPageParam param) {
//        param.setPageSize(pageSize);
//        param.setCurrentPage(pageIndex);
//        LambdaQueryWrapper<OauthClientDetails> wrapper = Wrappers.<OauthClientDetails>lambdaQuery();
//        if (!StrUtil.isEmpty(param.getName())) {
//            wrapper.like(OauthClientDetails::getName, param.getName());
//        }
//        wrapper.orderByDesc(OauthClientDetails::getGmtCreated);
//        Page<OauthClientDetails> page = new Page<>(param.getCurrentPage(), param.getPageSize());
//        page = oauthClientService.page(page, wrapper);
//        return Response.builder().data(PageResult.convert(page)).build();
//    }
//
//    @PostMapping("/add")
//    @ApiOperation("添加授权客户端")
//    public Object add(@RequestBody @Valid OauthClientUpdatedParam param) throws Exception {
//        String clientSecret = param.getClientSecret();
//        if (StrUtil.isEmpty(clientSecret)) {
//            throw new Exception("秘钥不能为空");
//        }
//        oauthClientService.add(param);
//        return Response.builder().build();
//    }
//
//    @PostMapping("/update")
//    @ApiOperation("修改授权客户端")
//    public Object update(@RequestBody @Valid OauthClientUpdatedParam param) throws Exception {
//        oauthClientService.update(param);
//        return Response.builder().build();
//    }
//
//    @PostMapping("/delete")
//    @ApiOperation("删除授权客户端")
//    public Object delete(@RequestBody @Valid OauthClientFetchParam param) {
//        oauthClientService.deleteById(param.getClientId());
//        return Response.builder().build();
//    }
//}
