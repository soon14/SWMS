package com.swms.mdm.config.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("metaData")
public class MetaDataController {

    @GetMapping("{pathname}")
    public String getByPath(@PathVariable String pathname) {
        return "";
    }
}
