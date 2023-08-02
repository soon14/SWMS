package com.swms.station.api;

import com.swms.station.executor.HandlerExecutor;
import com.swms.station.view.ViewHelper;
import com.swms.station.websocket.utils.HttpContext;
import com.swms.common.utils.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * receive operator's operations
 */
@RestController
@RequestMapping("api")
@Validated
public class ApiController {

    @Autowired
    private HandlerExecutor handlerExecutor;

    @Autowired
    private ViewHelper viewHelper;

    @PutMapping
    public Response<Object> execute(@RequestParam ApiCodeEnum apiCode, @RequestBody(required = false) String body) {
        Long workStationId = HttpContext.getWorkStationId();
        handlerExecutor.execute(apiCode, body, workStationId);
        return Response.success();
    }

    @GetMapping
    public Response<Object> getWorkStationVO() {
        Long workStationId = HttpContext.getWorkStationId();
        return Response.success(viewHelper.getWorkStationVO(workStationId));
    }

}
