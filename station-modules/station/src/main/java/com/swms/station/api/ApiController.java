package com.swms.station.api;

import com.swms.common.http.Response;
import com.swms.station.executor.HandlerExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * receive operator's operations
 */
@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    private HandlerExecutor handlerExecutor;

    @PostMapping
    public Response<Object> execute(@RequestParam ApiCodeEnum apiCode, @RequestBody(required = false) String body) {
        handlerExecutor.execute(apiCode, body);
        return Response.success();
    }
}
