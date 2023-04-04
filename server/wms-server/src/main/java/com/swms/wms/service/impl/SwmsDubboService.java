package com.swms.wms.service.impl;

import com.swms.wms.service.ISwmsDubbo;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@DubboService
@Service
public class SwmsDubboService implements ISwmsDubbo {
    @Override
    public void swmsDubbo() {

    }
}
