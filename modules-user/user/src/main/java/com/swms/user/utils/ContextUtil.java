package com.swms.user.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 手动获取 bean 的类，用于通过 @Autowired 无法注入 bean 的地方
 *
 * @author sws
 * @date 2021/6/21 10:42
 */
@Component
public class ContextUtil implements ApplicationContextAware {

    protected static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        if (applicationContext == null) {
            applicationContext = context;
        }
    }

    public static <T> T getBean(Class<T> clazz) {
        // 拿到ApplicationContext对象实例后就可以手动获取Bean的注入实例对象
        return applicationContext.getBean(clazz);
    }
}
