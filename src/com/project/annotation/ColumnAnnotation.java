package com.project.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liuyulai
 * Created with IntelliJ IDEA.
 * Date: 21.6.8
 * Time: 10:41
 * Description: 列名注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnAnnotation {
    String value();
}
