package com.project.dao.impl;

import com.project.bean.UserBean;
import com.project.dao.BaseDao;
import com.project.dao.IUserDao;

import java.sql.SQLException;
import java.util.List;

/**
 * @author liuyulai
 * Created with IntelliJ IDEA.
 * Date: 21.6.11
 * Time: 11:09
 * Description: No Description
 */
public class UserDaoImpl extends BaseDao implements IUserDao {
    @Override
    public UserBean login(String username, String password) {
        UserBean userBean = null;
        if (this.find("select * from t_user where u_username=? and u_password = ?", UserBean.class, username, password).size() > 0) {
            userBean = (UserBean) this.find("select * from t_user where u_username=? and u_password = ?", UserBean.class, username, password).get(0);
        }
        return userBean;
    }

    @Override
    public void addUser(UserBean userBean) {
        this.insert(userBean);
    }

    @Override
    public void delUser(int id) {
        this.deleteOrUpdate("delete from t_user where pk_userId = ?", id);
    }

    @Override
    public void updateUser(int id, String newPassword) {
        this.deleteOrUpdate("update t_user set u_password = ? where pk_userId = ?", newPassword, id);
    }

    @Override
    public List<UserBean> findAllUser() {
        return this.find("select * from t_user", UserBean.class);
    }

    @Override
    public UserBean findUserById(int userId) {
        return (UserBean) this.find("select * from t_user where pk_userId = ?", UserBean.class, userId).get(0);
    }
}
