package com.swms.user.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.swms.user.api.UserContext;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author sws
 * @version 1.0
 * @since 2020-12-24
 */
@Configuration
@MapperScan({"com.swms.user.repository.mapper"})
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInnerInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInnerInterceptor.setMaxLimit(500L);
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Component
    public static class GmtTimeHandler implements MetaObjectHandler {
        @Override
        public void insertFill(MetaObject metaObject) {
            this.strictInsertFill(metaObject, "gmtCreated", Long.class, System.currentTimeMillis());
            this.strictInsertFill(metaObject, "gmtModified", Long.class, System.currentTimeMillis());
            this.strictInsertFill(metaObject, "createUser", String.class, UserContext.getCurrentUser());
            this.strictInsertFill(metaObject, "modifiedUser", String.class, UserContext.getCurrentUser());
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            this.strictUpdateFill(metaObject, "gmtModified", Long.class, System.currentTimeMillis());
            this.strictUpdateFill(metaObject, "modifiedUser", UserContext::getCurrentUser, String.class);
        }
    }
}
