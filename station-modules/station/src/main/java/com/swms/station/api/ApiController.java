package com.swms.station.api;

import com.google.common.base.Preconditions;
import com.swms.common.http.Response;
import com.swms.station.executor.HandlerExecutor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * receive operator's operations
 */
@Controller
@RequestMapping("api")
public class ApiController {

    @Autowired
    private HandlerExecutor handlerExecutor;

    @PostMapping
    public Response execute(@RequestParam String apiCode, @RequestBody(required = false) String body) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(apiCode));
        handlerExecutor.execute(apiCode, body);
        return Response.success();
    }
}
