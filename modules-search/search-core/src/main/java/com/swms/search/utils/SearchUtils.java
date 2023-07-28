package com.swms.search.utils;

import cn.zhxu.bs.bean.DbField;
import cn.zhxu.bs.bean.SearchBean;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.swms.search.parameter.SearchParam;
import com.swms.utils.exception.WmsException;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class SearchUtils {

    private SearchUtils() {

    }

    private static final String PACKAGE_NAME = "com.swms.search.parameter.";

    public static Class<?> createClass(SearchParam searchParam) throws CannotCompileException, ClassNotFoundException {

        ClassPool classPool = ClassPool.getDefault();
        CtClass ct = classPool.getOrNull(PACKAGE_NAME + searchParam.getSearchIdentity());

        if (ct == null) {
            return createClass(searchParam, classPool);
        } else {
            // if the SearchParam columns size is changed then recreate the class
            if (ct.getDeclaredFields().length != searchParam.getShowColumns().size()) {
                ct.detach();
                return createClass(searchParam, classPool);
            } else {

                return Class.forName(PACKAGE_NAME + searchParam.getSearchIdentity());
            }
        }

    }

    private static Class<?> createClass(SearchParam searchParam, ClassPool classPool) throws CannotCompileException {
        CtClass ct;
        try {
            ct = createClass(classPool, searchParam);
        } catch (Exception e) {
            log.error("create class: {} error: ", searchParam.getSearchIdentity(), e);
            throw new WmsException("search server create class error:" + e.getMessage());
        }
        return ct.toClass(SearchParam.class);
    }

    private static CtClass createClass(ClassPool classPool, SearchParam searchParam) throws NotFoundException, CannotCompileException {
        CtClass dynamicClass = classPool.makeClass(PACKAGE_NAME + searchParam.getSearchIdentity());

        for (SearchParam.Column column : searchParam.getShowColumns()) {
            CtField field = new CtField(classPool.get(column.getJavaType()), column.getName(), dynamicClass);
            dynamicClass.addField(field);

            //add dbField mapping
            if (StringUtils.isNotBlank(column.getDbField())) {
                Annotation annotation = new Annotation(DbField.class.getName(), dynamicClass.getClassFile().getConstPool());
                annotation.addMemberValue("value", new StringMemberValue(column.getDbField(), dynamicClass.getClassFile().getConstPool()));
                AnnotationsAttribute annotationsAttribute = new AnnotationsAttribute(dynamicClass.getClassFile().getConstPool(), AnnotationsAttribute.visibleTag);
                annotationsAttribute.addAnnotation(annotation);
                field.getFieldInfo().addAttribute(annotationsAttribute);
            }
        }

        SearchParam.SearchObject searchObject = searchParam.getSearchObject();
        if (searchObject != null) {
            AnnotationsAttribute classAnnotations = new AnnotationsAttribute(dynamicClass.getClassFile().getConstPool(), AnnotationsAttribute.visibleTag);
            Annotation annotation = new Annotation(SearchBean.class.getName(), dynamicClass.getClassFile().getConstPool());

            if (StringUtils.isNotEmpty(searchObject.getDataSource())) {
                annotation.addMemberValue("dataSource", new StringMemberValue(searchObject.getDataSource(), dynamicClass.getClassFile().getConstPool()));
            }
            if (StringUtils.isNotEmpty(searchObject.getTables())) {
                annotation.addMemberValue("tables", new StringMemberValue(searchObject.getTables(), dynamicClass.getClassFile().getConstPool()));
            }
            if (StringUtils.isNotEmpty(searchObject.getWhere())) {
                annotation.addMemberValue("where", new StringMemberValue(searchObject.getWhere(), dynamicClass.getClassFile().getConstPool()));
            }
            if (StringUtils.isNotEmpty(searchObject.getGroupBy())) {
                annotation.addMemberValue("groupBy", new StringMemberValue(searchObject.getGroupBy(), dynamicClass.getClassFile().getConstPool()));
            }
            if (StringUtils.isNotEmpty(searchObject.getOrderBy())) {
                annotation.addMemberValue("orderBy", new StringMemberValue(searchObject.getOrderBy(), dynamicClass.getClassFile().getConstPool()));
            }
            classAnnotations.addAnnotation(annotation);
            dynamicClass.getClassFile().addAttribute(classAnnotations);
        }

        return dynamicClass;
    }

    private static final Set<String> arrayOps = Sets.newHashSet("bt", "il");

    /**
     * amis array parameters is String , we need to convert it to arrays to adapter Bean Searcher framework
     *
     * @param paramMap
     *
     * @return
     */
    public static Map<String, Object> handleArrayParams(Map<String, Object> paramMap) {

        Map<String, Object> newParamMap = Maps.newHashMap(paramMap);

        for (Map.Entry<String, Object> next : paramMap.entrySet()) {
            String key = next.getKey();
            String value = String.valueOf(next.getValue());
            if (arrayOps.contains(value)) {
                String fieldName = key.trim().split("-")[0];
                String fieldValue = paramMap.getOrDefault(fieldName, "").toString();

                if (StringUtils.isNotEmpty(fieldValue)) {
                    int index = 0;
                    for (String v : fieldValue.split(",")) {
                        newParamMap.put(fieldName + "-" + (index++), v);
                    }
                    newParamMap.remove(fieldName);
                }
            }
        }

        return newParamMap;
    }
}
