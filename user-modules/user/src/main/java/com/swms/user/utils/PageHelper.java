package com.swms.user.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Query result wrap util
 *
 * @author sws
 */
@Slf4j
@SuppressWarnings({"rawtypes"})
public class PageHelper {

    private static final String QUERY_KEY_SEPARATOR = "_";

    private static final String QUERY_RESULTS_TOTAL = "total";

    private static final String QUERY_RESULTS_DATA = "results";

    /**
     * 对于单个实体对象查询结果的转换
     */
    public static <T, Q> Map<String, Object> convertWithPageInfo(Page<Q> queryResults, Class<T> tClass) {

        Map<String, Object> pageDataMap = new LinkedHashMap<>();

        if (queryResults == null) {
            pageDataMap.put(QUERY_RESULTS_TOTAL, 0);
            pageDataMap.put(QUERY_RESULTS_DATA, null);
            return pageDataMap;
        }
        pageDataMap.put(QUERY_RESULTS_TOTAL, queryResults.getTotal());
        pageDataMap.put(QUERY_RESULTS_DATA, convertWithList(queryResults.getRecords(), tClass));

        return pageDataMap;
    }

    /**
     * 对单个实体对象查询结果的转换
     */
    @SneakyThrows
    public static <T, Q> T convertWithSingle(Q single, Class<T> voClass) {
        T t = voClass.newInstance();
        if (ObjectUtils.isEmpty(single)) {
            return t;
        }
        extracted(voClass, t, single);
        return t;
    }

    /**
     * 对 List<T> 实体对象查询结果的转换
     */
    public static <T, Q> List<T> convertWithList(List<Q> list, Class<T> voClass) {

        if (list == null) {
            return new ArrayList<>();
        }

        List<T> collect = list.stream().map(single -> {
            try {
                T t = voClass.newInstance();
                extracted(voClass, t, single);
                return t;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        return collect;

    }


    /**
     * 这里目前是需要将所有查询出来的字段进行映射，如果存在有字段没有被映射到 tClass 里面则会报错，可以改为不报错，
     * 但是就可能会不小心漏掉一些字段。
     */
    private static <T> void extracted(Class<T> tClass, T t, Object qObject) throws IllegalAccessException, InvocationTargetException {
        String simpleName = qObject.getClass().getSimpleName();
        Field[] declaredFields = qObject.getClass().getDeclaredFields();
        Field[] fields = qObject.getClass().getFields();

        List<Field> all = Stream.of(declaredFields, fields).flatMap(Arrays::stream).collect(Collectors.toList());

        for (Field field : all) {
            String methodName = "set" + simpleName + QUERY_KEY_SEPARATOR + field.getName();
            if (ClassUtils.hasMethod(tClass, methodName, field.getType())) {
                field.setAccessible(true);
                ClassUtils.getMethod(tClass, methodName, field.getType()).invoke(t, field.get(qObject));
            }
        }
    }

}
