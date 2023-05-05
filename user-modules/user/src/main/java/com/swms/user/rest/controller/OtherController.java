package com.swms.user.rest.controller;

import com.swms.user.rest.common.BaseResource;
import com.swms.user.rest.param.other.EnumFetchParam;
import com.swms.user.service.model.EnumDTO;
import com.swms.user.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sws
 * @date 2020/12/18 9:41
 */
@Api(tags = "99.其它接口")
@RestController
@RequestMapping(BaseResource.API + "/other")
public class OtherController extends BaseResource {

    @ApiOperation(value = "查询枚举(SystemEnum、AuthGrantTypeEnum、MenuTypeEnum、YesOrNo)", response = EnumDTO.class)
    @PostMapping(value = "/getEnum")
    public Object getEnum(@RequestBody @Valid EnumFetchParam enumNames) {
        return Response.builder().build();
    }

}

