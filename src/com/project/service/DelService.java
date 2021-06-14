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
 * Time: 15:10
 * Description: 删除业务组件
 */
@ServiceAnnotation(name = "del")
public class DelService implements Servlet {
    private IUserDao iUserDao = new UserDaoImpl();

    @Override
    public void service(Request request, Response response) {


        int id = Integer.parseInt(request.getParameter("id"));
        iUserDao.delUser(id);
        //重新跳转至findall页面
        FindAllService findAllService = new FindAllService();
        findAllService.service(request, response);
    }
}
