package com.swms.wms.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.Data;
import org.apache.ibatis.reflection.MetaObject;

@Data
public class MybatisPlusAutoFillHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        this.setFieldValByName("createUser", "system", metaObject);
        this.setFieldValByName("createTime", System.currentTimeMillis(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateUser", "system", metaObject);
        this.setFieldValByName("updateTime", System.currentTimeMillis(), metaObject);
    }
}
