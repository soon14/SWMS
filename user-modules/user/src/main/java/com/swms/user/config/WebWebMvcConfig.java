package com.swms.user.config;

import com.swms.user.config.prop.SystemProp;
import com.swms.user.config.prop.WebProp;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * web 配置
 *
 * @author sws
 * @version 1.0
 */
@Configuration
public class WebWebMvcConfig implements WebMvcConfigurer {

    private final WebProp webProp;
    private final SystemProp systemProp;

    public WebWebMvcConfig(WebProp webProp, SystemProp systemProp) {
        this.webProp = webProp;
        this.systemProp = systemProp;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Boolean swaggerEnable = systemProp.getSwaggerEnable();
        if (swaggerEnable == null || !swaggerEnable) {
            registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/empty-mapping/");

            registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/empty-mapping/");
        }
        registry.addResourceHandler("/web/**")
            .addResourceLocations(webProp.getWebLocation())
            .setCacheControl(CacheControl.noCache());
    }

    /**
     * 跳转到首页
     *
     * @param registry registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/web").setViewName("forward:/web/index.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new BufferedImageHttpMessageConverter());
    }

}
