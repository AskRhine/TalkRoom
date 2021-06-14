package com.project.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyulai
 * Created with IntelliJ IDEA.
 * Date: 21.6.10
 * Time: 10:33
 * Description: 服务器
 */
public class Server {
    /**
     * 用于存放servlet的map集合，方便进行对比
     */
    public static Map servletMap = new HashMap();


    public Server() {
        try {
            //开启服务器,并且开放8088端口
            ServerSocket serverSocket = new ServerSocket(8088);
            while (true) {
                //监听端口,如果有客户端连接到服务器就将连接的信息封装为socket对象
                Socket socket = serverSocket.accept();
                new SocketThread(socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //服务器启动
    public static void main(String[] args) {
        new Server();
    }


}
