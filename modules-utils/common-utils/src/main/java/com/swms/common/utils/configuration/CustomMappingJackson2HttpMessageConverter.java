package com.swms.common.utils.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.lang.reflect.Type;

@Slf4j
public class CustomMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    /**
     * 判断该转换器是否能将请求内容转换成 Java 对象
     */
    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        // 不需要反序列化
        return false;
    }

    /**
     * 判断该转换器是否能将请求内容转换成 Java 对象
     */
    @Override
    public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
        // 不需要反序列化
        return false;
    }

    /**
     * 判断该转换器是否可以将 Java 对象转换成返回内容.
     * 匹配web api(形如/web/xxxx)中的接口方法的返回参数
     */
    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return true;
    }

}
