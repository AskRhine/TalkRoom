package com.project.service;

import com.project.annotation.ServiceAnnotation;
import com.project.dao.IUserDao;
import com.project.dao.impl.UserDaoImpl;
import com.project.server.Request;
import com.project.server.Response;
import com.project.server.Servlet;

/**
 * @author liuyulai
 * Created with IntelliJ IDEA.
 * Date: 21.6.11
 * Time: 10:37
 * Description: 登陆组件
 */
@ServiceAnnotation(name = "land" )
public class LoginService implements Servlet {
    private IUserDao iUserDao = new UserDaoImpl();

    /**
     * 登录业务
     *
     * @param request  请求对象
     * @param response 相应对象
     */
    @Override
    public void service(Request request, Response response) {
        String username = request.getParameter("username");
        String password = request.getParameter("pwd");
        if (iUserDao.login(username, password) != null) {
            response.sendMessage("登录成功<br><a href='findAll'>点击查看所有人员<a/>");
        } else {
            response.sendMessage("登陆失败<br><a href='login.html'>点击此处进行跳转登录界面<a/>");
        }
    }


}
