package com.swms.user.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 给客户端颁发client信息
 *
 * @author sws
 * @version 1.0
 */
@RestController
@RequestMapping("/config.js")
public class WebController {

    private final String configJs = null;

//    public WebController(WebProp webProp, Gson gson) {
//        Map<String, Object> configJsConfig = webProp.getConfig();
//        configJsConfig.put("env", "prod");
//        this.configJs = "window.config = " + gson.toJson(configJsConfig);
//    }

    @GetMapping(produces = "application/javascript;charset=UTF-8")
    public String getConfigJs() {
        return configJs;
    }

}
