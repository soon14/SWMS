package com.swms.station.view;

import com.swms.station.api.ApiCodeEnum;
import com.swms.station.view.handler.IViewHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ViewHelper {

    public static List<IViewHandler> getViewHandlers(ApiCodeEnum apiCode) {
        return new ArrayList<>();
    }
}
