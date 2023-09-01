package com.swms.plugin.sdk;

import com.swms.plugin.sdk.config.PluginConfigLoadProcessor;
import org.pf4j.PluginWrapper;
import org.pf4j.spring.SpringPlugin;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public abstract class SpringPluginApplication extends SpringPlugin {

    protected SpringPluginApplication(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    protected ApplicationContext createApplicationContext() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.setClassLoader(getWrapper().getPluginClassLoader());
        applicationContext.refresh();

        new PluginConfigLoadProcessor(wrapper).loadConfiguration();
        return applicationContext;
    }
}
