package com.project.service;

import com.project.annotation.ServiceAnnotation;
import com.project.bean.UserBean;
import com.project.dao.IUserDao;
import com.project.dao.impl.UserDaoImpl;
import com.project.server.Request;
import com.project.server.Response;
import com.project.server.Servlet;

/**
 * @author liuyulai
 * Created with IntelliJ IDEA.
 * Date: 21.6.11
 * Time: 15:24
 * Description: No Description
 */
@ServiceAnnotation(name = "findById")
public class FindByIdService implements Servlet {
    private IUserDao iUserDao = new UserDaoImpl();

    @Override
    public void service(Request request, Response response) {
        //得到表单数据
        int id = Integer.parseInt(request.getParameter("id"));
        UserBean user = iUserDao.findUserById(id);
        String info = "<form action='update'>";
        info += "用户名：" + user.getUsername() + "<br>";
        info += "密码：<input  type='password' name='pwd'><br>";
        info += "性别：" + user.getSex() + "<br>";
        info += "生日：" + user.getBirthday() + "<br>";
        info += "<input type='hidden' name='id' value='" + user.getId() + "'>";
        info += "<input type='submit' value='修改'>";
        info += "</form>";
        response.sendMessage(info);
    }

}
