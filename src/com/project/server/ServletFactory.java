package com.project.server;

import com.project.annotation.ServiceAnnotation;
import com.project.annotation.ServletAnnotation;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyulai
 * Created with IntelliJ IDEA.
 * Date: 21.6.11
 * Time: 17:19
 * Description: 通过注解方式产生servlet实现
 */
public class ServletFactory {
    //    private static Properties pro = new Properties();
//    static {
//        try {
//            pro.load(new FileReader("web.txt"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//

//    /**
//     * 根据URL，得到处理该请求的业务组件对象
//     *
//     * @param url URL
//     * @return 业务组件对象
//     */
//    public static Object getServlet(String url) {
//        String classPath = pro.getProperty(url);
//        //请求的是文件
//        if (classPath == null) {
//            return null;
//        }
//
//        try {
//            //加载类，得到类模版
//            Class c = Class.forName(classPath);
//            return c.getDeclaredConstructor().newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    /**
     * 注解方式获取servlet实现
     *
     * @param c
     * @param url
     * @return
     */
    public synchronized static Object getServlet(Class c, String url) {
        try {
            //获取注解对象
            ServletAnnotation servletAnnotation = (ServletAnnotation) c.getDeclaredAnnotation(ServletAnnotation.class);
            //获取service实现所在位置
            String packageName = servletAnnotation.value();
            //获取service中所有实现类
            List<Class> list = getClassList(packageName);
            for (int i = 0; i < list.size(); i++) {
                Class serClass = list.get(i);
                //判断是否有这个注解
                boolean flag = serClass.isAnnotationPresent(ServiceAnnotation.class);
                if (flag) {
                    ServiceAnnotation sa = (ServiceAnnotation) serClass.getDeclaredAnnotation(ServiceAnnotation.class);
                    String command = sa.name();
                    if (command.equals(url)) {
                        Class se = Class.forName(serClass.getName());
                        Servlet s = (Servlet) se.getDeclaredConstructor().newInstance();
                        if (Server.servletMap.get(s.getClass().getName()) == null) {
                            System.out.println("暂时没有产生" + url + "这个servlet");
                            Server.servletMap.put(s.getClass().getName(), s);
                            return s;
                        } else {
                            System.out.println("map集合中已经有" + url + "这个servlet");
                            return Server.servletMap.get(s.getClass().getName());
                        }
                    }
                }
            }
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 通过包名获取该包中所有类的集合
     *
     * @param packageName 包名
     * @return 该包目录下所有类集合
     */
    private static List<Class> getClassList(String packageName) {
        try {
            List classList = new ArrayList<>();
            String packageDirName = packageName.replace('.', '/');
            URL url = Thread.currentThread().getContextClassLoader()
                    .getResources(packageDirName).nextElement();
            String filePath = URLDecoder.decode(url.getFile(), "utf-8");
            File dirFile = new File(filePath);
            String[] classFiles = dirFile.list();
            for (String f : classFiles) {
                if (f.endsWith(".class")) {
                    String fileName = f.substring(0, f.lastIndexOf(".class"));
                    Class c = Class.forName(packageName + "." + fileName);
                    classList.add(c);
                }
            }
            return classList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void init() {

    }


    public static void main(String[] args) {
        Servlet a1 = (Servlet) ServletFactory.getServlet(Servlet.class, "add");
        Servlet a2 = (Servlet) ServletFactory.getServlet(Servlet.class, "add");
        System.out.println(a1 == a2);
        Servlet d1 = (Servlet) ServletFactory.getServlet(Servlet.class, "del");
        System.out.println(a1 == d1);
    }
}

