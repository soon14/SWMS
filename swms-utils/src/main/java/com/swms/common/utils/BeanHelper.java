package com.swms.common.utils;

import com.swms.common.exception.WmsException;
import com.swms.common.exception.code_enum.CommonErrorDescEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class BeanHelper {

    public static <T> T copyNonNullProperties(Object src, Class<T> clazz, String... ignoreFields) {
        try {
            T t = clazz.getDeclaredConstructor().newInstance();
            copyNonNullProperties(src, t, ignoreFields);
            return t;
        } catch (Exception e) {
            log.warn("copyNonNullProperties error: ", e);
            throw new WmsException(CommonErrorDescEnum.SYSTEM_EXEC_ERROR);
        }
    }

    public static void copyNonNullProperties(Object src, Object target, String... ignoreFields) {

        List<String> list = new ArrayList<>();

        if (ignoreFields != null) {
            List<String> ignoreList = Arrays.asList(ignoreFields);
            list.addAll(ignoreList);
        }
        Set<String> nullPropertyNames = getNullPropertyNames(src);
        nullPropertyNames.addAll(list);
        String[] ignoreArray = new String[nullPropertyNames.size()];

        BeanUtils.copyProperties(src, target, nullPropertyNames.toArray(ignoreArray));
    }

    /**
     * 忽略掉id的copy
     *
     * @param src
     * @param target
     */
    public static void copyNonNullPropertiesIgnoreId(Object src, Object target) {

        List<String> list = new ArrayList<>();
        list.add("id");
        Set<String> nullPropertyNames = getNullPropertyNames(src);
        nullPropertyNames.addAll(list);
        String[] ignoreArray = new String[nullPropertyNames.size()];
        BeanUtils.copyProperties(src, target, nullPropertyNames.toArray(ignoreArray));
    }

    /**
     * @param srcList    数据源列表
     * @param targetList 目标源列表
     * @param targetCls  目标源数据类型
     * @param strings
     * @param <T>
     * @param <E>
     */
    public static <T, E> void copyNonNullPropertiesListToList(List<T> srcList, List<E> targetList, Class<E> targetCls, String... strings) {
        if (CollectionUtils.isEmpty(srcList) || targetList == null) {
            return;
        }
        for (T s : srcList) {
            try {
                E e = targetCls.getDeclaredConstructor().newInstance();
                copyNonNullProperties(s, e, strings);
                targetList.add(e);
            } catch (InstantiationException | IllegalAccessException
                | NoSuchMethodException | InvocationTargetException instantiationException) {
                log.error("copyNonNullPropertiesListToList error", instantiationException);
            }
        }
    }

    private static Set<String> getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        return emptyNames;
    }

    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
        try {
            T bean = clazz.newInstance();
            BeanMap beanMap = BeanMap.create(bean);
            beanMap.putAll(map);
            return bean;
        } catch (Exception e) {
            throw new WmsException(CommonErrorDescEnum.SYSTEM_EXEC_ERROR);
        }
    }

    public static <T> List<T> deepCopy(List<T> src) {
        try {
            if (CollectionUtils.isEmpty(src)) {
                return Collections.emptyList();
            }

            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            @SuppressWarnings("unchecked")
            List<T> dest = (List<T>) in.readObject();
            return dest;
        } catch (Exception e) {
            throw new WmsException(CommonErrorDescEnum.SYSTEM_EXEC_ERROR);
        }
    }
}
