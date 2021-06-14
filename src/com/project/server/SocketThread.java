package com.project.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author liuyulai
 * Created with IntelliJ IDEA.
 * Date: 21.6.10
 * Time: 13:44
 * Description: 基于线程的不同socket对象,方便多用户进行访问
 */
public class SocketThread implements Runnable {
    public static String header = "HTTP/1.1 200 OK\r\n" +
            "Content-Type: text/html\r\n" +
            "\r\n";

    /**
     * Socket对象
     */
    private Socket socket;

    /**
     * 每new一个对象就开启一个新的线程
     *
     * @param socket
     */
    public SocketThread(Socket socket) {
        this.socket = socket;
        //启动线程线程
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            //服务器接收字节流,从socket中得到读取流,用于读取客户端给服务器的数据
            InputStream inputStream = socket.getInputStream();
            //从socket中得到写入流,用于发送数据至客户端
            OutputStream outputStream = socket.getOutputStream();
            //将写入流封装成response对象
            Response response = new Response(outputStream);
            //将读取流封装成request对象
            Request request = new Request(inputStream);
            System.out.println(request);
            //获取文件url地址
            String str = request.getUrl();
            //如果客户端请求跳转页面，则判断登录是否成功
            response.sendMessage(header);
            //根据url得到servlet对象，并且引用其实现类
            Servlet servlet = (Servlet) ServletFactory.getServlet(Servlet.class, str);
            if (servlet != null) {
                response.sendMessage("<html><meta charset='utf-8'>");
                servlet.service(request, response);
                response.sendMessage("</html>");
            } else {
                response.sendFile(str);
            }
            //关闭流
            outputStream.close();
            inputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
