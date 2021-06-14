package com.project.bean;

import com.project.annotation.ColumnAnnotation;
import com.project.annotation.TableAnnotation;
import com.project.annotation.IdAnnotation;

import java.time.LocalDate;

/**
 * @author liuyulai
 * Created with IntelliJ IDEA.
 * Date: 21.6.11
 * Time: 11:02
 * Description: No Description
 */
@TableAnnotation("t_user")
public class UserBean {
    @IdAnnotation
    @ColumnAnnotation(value = "pk_userId")
    private int id;
    @ColumnAnnotation(value = "u_username")
    private String username;
    @ColumnAnnotation(value = "u_password")
    private String password;
    @ColumnAnnotation(value = "u_birthday")
    private LocalDate birthday;
    @ColumnAnnotation(value = "u_sex")
    private String sex;

    public UserBean() {
    }

    public UserBean(String username, LocalDate birthday, String sex) {
        this.username = username;
        this.birthday = birthday;
        this.sex = sex;
        this.password = "666";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                ", sex='" + sex + '\'' +
                '}' + "\n";
    }
}
