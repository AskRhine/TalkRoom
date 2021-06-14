package com.project.service;

import com.project.annotation.ServiceAnnotation;
import com.project.bean.UserBean;
import com.project.dao.IUserDao;
import com.project.dao.impl.UserDaoImpl;
import com.project.server.Request;
import com.project.server.Response;
import com.project.server.Servlet;

import java.time.LocalDate;

/**
 * @author liuyulai
 * Created with IntelliJ IDEA.
 * Date: 21.6.11
 * Time: 14:23
 * Description: 添加用户业务组件
 */
@ServiceAnnotation(name = "add")
public class AddService implements Servlet {

    private static IUserDao iUserDao = new UserDaoImpl();

    @Override
    public void service(Request request, Response response) {
        //得到表单数据
        String user = request.getParameter("username");
        String sex = request.getParameter("sex");
        LocalDate birthday = LocalDate.parse(request.getParameter("birthday"));
        //将表单数据封装为实体对象
        UserBean userBean = new UserBean(user, birthday, sex);
        iUserDao.addUser(userBean);
        FindAllService findAllService = new FindAllService();
        findAllService.service(request, response);
    }
}
