package com.project.dao;

import com.project.bean.UserBean;

import java.util.List;

/**
 * @author liuyulai
 * Created with IntelliJ IDEA.
 * Date: 21.6.11
 * Time: 11:04
 * Description: 用户持久层接口
 */
public interface IUserDao {

    /**
     * 登录方法
     *
     * @param username 用户名
     * @param password 密码
     * @return 如果登陆失败返回null，成功返回用户对象
     */
    public UserBean login(String username, String password);

    /**
     * 添加用户
     *
     * @param userBean
     */
    void addUser(UserBean userBean);


    /**
     * 删除用户
     *
     * @param id
     */
    void delUser(int id);

    /**
     * 修改用户密码
     *
     * @param id
     * @param newPassword
     */
    void updateUser(int id, String newPassword);

    /**
     * 查找用户集合
     *
     * @return
     */
    List<UserBean> findAllUser();

    /**
     * 查找用户对象
     *
     * @param userId
     * @return
     */
    UserBean findUserById(int userId);

}
