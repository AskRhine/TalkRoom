package com.project.server;

import com.project.annotation.ServletAnnotation;

/**
 * @author liuyulai
 * Created with IntelliJ IDEA.
 * Date: 21.6.11
 * Time: 17:09
 * Description: 所有业务组件都要实现的接口
 */
@ServletAnnotation(value = "com.project.service")
public interface Servlet {
    /**
     * 业务方法
     *
     * @param request  请求对象
     * @param response 响应对象
     */
    void service(Request request, Response response);
}
