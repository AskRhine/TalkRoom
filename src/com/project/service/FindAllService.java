package com.project.service;

import com.project.annotation.ServiceAnnotation;
import com.project.bean.UserBean;
import com.project.dao.IUserDao;
import com.project.dao.impl.UserDaoImpl;
import com.project.server.Request;
import com.project.server.Response;
import com.project.server.Servlet;

import java.util.List;

/**
 * @author liuyulai
 * Created with IntelliJ IDEA.
 * Date: 21.6.11
 * Time: 13:52
 * Description: 查询所有的业务组件
 */
@ServiceAnnotation(name = "findAll")
public class FindAllService implements Servlet {
    private IUserDao iUserDao = new UserDaoImpl();

    @Override
    public void service(Request request, Response response) {

        List<UserBean> list = iUserDao.findAllUser();
        String info = "<table border='1' width='50%'>";
        //表头
        info += "<thead><tr><th>用户名</th><th>密码</th><th>生日</th><th>性别</th><th>操作</th></tr></thead>";
        info += "<tbody>";
        for (UserBean user : list) {
            info += "<tr><td>" + user.getUsername() + "</td>" +
                    "<td>" + user.getPassword() + "</td>" +
                    "<td>" + user.getBirthday() + "</td>" +
                    "<td>" + user.getSex() + "</td>" +
                    "<td><a href='del?id=" + user.getId() + "'>删除</a>" +
                    " <a href='findById?id=" + user.getId() + "'>修改</a></td></tr>";
        }
        info += "</tbody></table>";
        info += "<a href='add.html'>添加用户</a>";
        response.sendMessage(info);
    }
}
