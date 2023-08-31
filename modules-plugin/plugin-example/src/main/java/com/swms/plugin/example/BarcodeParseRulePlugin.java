package com.swms.plugin.example;

import com.google.common.collect.Lists;
import com.swms.plugin.sdk.SpringPluginApplication;
import org.pf4j.PluginWrapper;

import java.util.List;

public class BarcodeParseRulePlugin extends SpringPluginApplication {

    public BarcodeParseRulePlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public List<Class<?>> pluginConfigClasses() {
        return Lists.newArrayList(BarcodeConfig.class);
    }
}
