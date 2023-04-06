package com.swms.wms.config;

import com.swms.utils.base.BaseDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.javassist.bytecode.analysis.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.defaults.DefaultSqlSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Intercepts({@Signature(method = "update", type = Executor.class, args = {MappedStatement.class, Object.class})})
public class MybatisAuditDataInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        SqlCommandType sqlCommandType = null;

        for (Object object : args) {
            // 从MappedStatement参数中获取到操作类型
            if (object instanceof MappedStatement ms) {
                sqlCommandType = ms.getSqlCommandType();
                log.debug("操作类型： {}", sqlCommandType);
                continue;
            }
            handleParameters(sqlCommandType, object);
        }
        return invocation.proceed();
    }

    /**
     * handle parameters which are instanceof different types
     *
     * @param sqlCommandType
     * @param object
     *
     * @throws IllegalAccessException
     */
    private void handleParameters(SqlCommandType sqlCommandType, Object object) throws IllegalAccessException {

        log.debug("mybatis arg: {}", object);

        // 从上下文中获取用户名
        //TODO
        // get userName from UserContext
        String userName = "";

        if (object instanceof BaseDO) {
            if (SqlCommandType.INSERT == sqlCommandType) {
                setCreateAndUpdateValues(userName, object, new Date());
            } else if (SqlCommandType.UPDATE == sqlCommandType) {
                setUpdateValues(userName, object, new Date());
            }
        } else if (object instanceof MapperMethod.ParamMap parasMap) {
            String key = "record";
            if (!parasMap.containsKey(key)) {
                return;
            }
            Object paraObject = parasMap.get(key);
            if (paraObject instanceof BaseDO && SqlCommandType.UPDATE == sqlCommandType) {
                setUpdateValues(userName, object, new Date());
            }

            // 兼容批量插入
        } else if (object instanceof DefaultSqlSession.StrictMap strictMap) {

            String key = "collection";
            if (!strictMap.containsKey(key)) {
                return;
            }
            @SuppressWarnings("unchecked")
            List<Object> objs = (List<Object>) strictMap.get(key);
            for (Object obj : objs) {
                if (obj instanceof BaseDO) {
                    if (SqlCommandType.INSERT == sqlCommandType) {
                        setCreateAndUpdateValues(userName, obj, new Date());
                    }
                    if (SqlCommandType.UPDATE == sqlCommandType) {
                        setUpdateValues(userName, obj, new Date());
                    }
                }
            }
        }
    }

    private void setCreateAndUpdateValues(String userName, Object object, Date insertTime) throws IllegalAccessException {
        FieldUtils.writeDeclaredField(object, "createUser", userName);
        FieldUtils.writeDeclaredField(object, "createTime", insertTime);
        setUpdateValues(userName, object, insertTime);
    }

    private void setUpdateValues(String userName, Object object, Date time) throws IllegalAccessException {
        FieldUtils.writeDeclaredField(object, "updateUser", userName);
        FieldUtils.writeDeclaredField(object, "updateTime", time);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

}
