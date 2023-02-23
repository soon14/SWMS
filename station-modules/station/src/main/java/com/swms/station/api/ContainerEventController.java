package com.swms.station.api;

import com.google.common.base.Preconditions;
import com.swms.common.http.Response;
import com.swms.station.view.executor.ViewHandlerExecutor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * receive Container Arrived Event
 */
@Controller
@RequestMapping("container/event")
public class ContainerEventController {

    @Autowired
    private ViewHandlerExecutor viewHandlerExecutor;

    @PostMapping("arrived")
    public Response containerArrived(@RequestBody String body) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(body));
        viewHandlerExecutor.containerArrived(body);
        return Response.success();
    }
}
