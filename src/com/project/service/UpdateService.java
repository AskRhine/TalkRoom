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
 * Time: 15:57
 * Description: No Description
 */
@ServiceAnnotation(name = "update")
public class UpdateService implements Servlet {
    private IUserDao iUserDao = new UserDaoImpl();


    @Override
    public void service(Request request, Response response) {
        //得到表单数据
        int id = Integer.parseInt(request.getParameter("id"));
        String pwd = request.getParameter("pwd");
        //调用持久方法完成修改
        iUserDao.updateUser(id, pwd);
        FindAllService s = new FindAllService();
        s.service(request, response);
    }

}
