package com.swms.station.api;

import com.swms.common.http.Response;
import com.swms.station.executor.HandlerExecutor;
import com.swms.station.view.ViewHelper;
import com.swms.station.websocket.utils.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ApiController {

    @Autowired
    private HandlerExecutor handlerExecutor;

    @Autowired
    private ViewHelper viewHelper;

    @PutMapping
    public Response<Object> execute(@RequestParam ApiCodeEnum apiCode, @RequestBody(required = false) String body) {
        String stationCode = HttpContext.getStationCode();
        handlerExecutor.execute(apiCode, body, stationCode);
        return Response.success();
    }

    @GetMapping
    public Response<Object> getWorkStationVO() {
        String stationCode = HttpContext.getStationCode();
        return Response.success(viewHelper.getWorkStationVO(stationCode));
    }
}
