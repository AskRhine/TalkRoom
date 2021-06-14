package com.project.dao;

import com.project.annotation.ColumnAnnotation;
import com.project.annotation.TableAnnotation;
import com.project.annotation.IdAnnotation;
import com.project.dao.impl.UserDaoImpl;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author liuyulai
 * Created with IntelliJ IDEA.
 * Date: 21.6.11
 * Time: 11:10
 * Description: No Description
 */
public class BaseDao {
    protected Connection connection;
    protected PreparedStatement preparedStatement;
    protected ResultSet resultSet;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:12345/testdatabase?characterEncoding=utf-8", "root", "1122");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void closeConnection() {
        if (resultSet != null) {
            try {
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * 查询方法
     *
     * @param sql        sql语句
     * @param beanClass  类模板
     * @param valueArray 值列表
     * @return 实体类集合
     */
    public List find(String sql, Class beanClass, Object... valueArray) {
        List list = new ArrayList();
        this.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            if (valueArray != null) {
                for (int i = 0; i < valueArray.length; i++) {
                    preparedStatement.setObject(i + 1, valueArray[i]);
                }
            }
            resultSet = preparedStatement.executeQuery();
            //获取结果集的检查对象
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            //得到查询列的个数
            int columnCount = resultSetMetaData.getColumnCount();
            while (resultSet.next()) {
                //产生实体类对象
                Object beanObject = beanClass.getDeclaredConstructor().newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = resultSetMetaData.getColumnName(i);
                    //从结果集中获取指定列的数据
                    Object resultValue = resultSet.getObject(resultSetMetaData.getColumnLabel(i));
                    if (resultValue instanceof java.sql.Date) {
                        resultValue = LocalDate.parse(resultValue.toString());
                    }
                    Field f = findFiled(beanClass, columnName);
                    f.setAccessible(true);
                    f.set(beanObject, resultValue);
                }
                list.add(beanObject);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            closeConnection();
        }
        return list;
    }


    /**
     * 将某数据根据条件删除或更新
     *
     * @param sql         sql语句
     * @param valuesArray 传入的条件数组
     */
    public void deleteOrUpdate(String sql, Object... valuesArray) {
        this.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < valuesArray.length; i++) {
                preparedStatement.setObject(i + 1, valuesArray[i]);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeConnection();
        }

    }


    /**
     * 添加对象至数据库方法
     *
     * @param beanObject bean实体对象
     * @return 拼接好的SQL语句
     */
    public void insert(Object beanObject) {
        String sql = "insert into ";
        String sql2 = " values(";
        //获取类模板
        Class beanClass = beanObject.getClass();
        //获取类注解
        boolean flag = beanClass.isAnnotationPresent(TableAnnotation.class);
        if (flag) {
            TableAnnotation tableAnnotation = (TableAnnotation) beanClass.getDeclaredAnnotation(TableAnnotation.class);
            String name = tableAnnotation.value();
            sql += name + " (";
        }
        try {
            //获取所有属性
            Field[] fields = beanClass.getDeclaredFields();
            List list = new LinkedList();
            for (int i = 0; i < fields.length; i++) {
                boolean flag2 = fields[i].isAnnotationPresent(ColumnAnnotation.class);
                boolean flag3 = fields[i].isAnnotationPresent(IdAnnotation.class);
                if (flag2 && !flag3) {
                    ColumnAnnotation tableAnnotation = fields[i].getAnnotation(ColumnAnnotation.class);
                    String nameTable = tableAnnotation.value();
                    fields[i].setAccessible(true);
                    Object obj = fields[i].get(beanObject);
                    //还没有到最后一个元素
                    if (i != fields.length - 1) {
                        sql += nameTable + ",";
                        sql2 += "?,";
                    } else {
                        //最后一个元素
                        sql += nameTable + ") ";
                        sql2 += "?)";
                    }
                    list.add(obj);

                }
            }
            this.getConnection();
            preparedStatement = connection.prepareStatement(sql + sql2);
            for (int i = 0; i < list.size(); i++) {
                preparedStatement.setObject(i + 1, list.get(i));
            }
            preparedStatement.executeUpdate();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    /**
     * 根据
     *
     * @param beanClass  类模板
     * @param columnName 根据列名得到属性名
     * @return
     */
    public Field findFiled(Class beanClass, String columnName) {
        //得到该类中所有的属性列表
        Field[] fieldArray = beanClass.getDeclaredFields();
        for (Field field : fieldArray) {
            //判断该属性是否有ColumnAnnotation注解
            boolean flag = field.isAnnotationPresent(ColumnAnnotation.class);
            if (flag) {
                //得到注解实体
                ColumnAnnotation column = field.getAnnotation(ColumnAnnotation.class);
                //得到该注解的value值
                String name = column.value();
                //将属性上的注解value值与传入的列名做对比，如果相同则传出该属性
                if (name.equals(columnName)) {
                    return field;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        IUserDao iUserDao = new UserDaoImpl();
//        iUserDao.addUser(new UserBean("testjkllk", LocalDate.parse("1990-08-01"), "woman"));
//        System.out.println(iUserDao.findAllUser());
//        iUserDao.delUser(5);
//        iUserDao.updateUser(2, "xmxmxm");
//        System.out.println(iUserDao.findAllUser());
        System.out.println(iUserDao.findUserById(2));
//        System.out.println(iUserDao.login("tester1", "1122"));
    }
}
