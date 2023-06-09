package com.swms.search.utils;

import cn.zhxu.bs.bean.SearchBean;
import com.swms.search.parameter.SearchParam;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SearchUtils {

    private static final String PACKAGE_NAME = "com.swms.search.parameter.";

    public static Class createClass(SearchParam searchParam) throws CannotCompileException, ClassNotFoundException {

        // Create a new ClassPool
        ClassPool classPool = ClassPool.getDefault();
        CtClass ct = classPool.getOrNull(PACKAGE_NAME + searchParam.getSearchIdentity());
        if (ct == null) {
            try {
                ct = createClass(classPool, searchParam);
            } catch (Exception e) {
                log.error("create class: {} error: ", searchParam.getSearchIdentity(), e);
                throw new RuntimeException(e);
            }
            return ct.toClass(SearchParam.class);
        } else {
            return Class.forName(PACKAGE_NAME + searchParam.getSearchIdentity());
        }

    }

    private static CtClass createClass(ClassPool classPool, SearchParam searchParam) throws NotFoundException, CannotCompileException {
        CtClass dynamicClass = classPool.makeClass(PACKAGE_NAME + searchParam.getSearchIdentity());

        for (SearchParam.Column column : searchParam.getShowColumns()) {
            CtField field = new CtField(classPool.get(column.getJavaType()), column.getObjectField(), dynamicClass);
            dynamicClass.addField(field);
        }

        SearchParam.SearchObject searchObject = searchParam.getSearchObject();
        if (searchObject != null) {
            AnnotationsAttribute classAnnotations = new AnnotationsAttribute(dynamicClass.getClassFile().getConstPool(), AnnotationsAttribute.visibleTag);
            Annotation annotation = new Annotation(SearchBean.class.getName(), dynamicClass.getClassFile().getConstPool());

            annotation.addMemberValue("tables", new StringMemberValue(searchObject.getTables(), dynamicClass.getClassFile().getConstPool()));
            annotation.addMemberValue("where", new StringMemberValue(searchObject.getWhere(), dynamicClass.getClassFile().getConstPool()));
            annotation.addMemberValue("groupBy", new StringMemberValue(searchObject.getGroupBy(), dynamicClass.getClassFile().getConstPool()));
            annotation.addMemberValue("orderBy", new StringMemberValue(searchObject.getOrderBy(), dynamicClass.getClassFile().getConstPool()));
            classAnnotations.addAnnotation(annotation);
            dynamicClass.getClassFile().addAttribute(classAnnotations);
        }

        return dynamicClass;
    }

}
