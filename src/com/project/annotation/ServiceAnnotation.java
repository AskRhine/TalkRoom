package com.project.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liuyulai
 * Created with IntelliJ IDEA.
 * Date: 21.6.13
 * Time: 12:57
 * Description: 用于对应url请求的注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceAnnotation {
    String name();
}
