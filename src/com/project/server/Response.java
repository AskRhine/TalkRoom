package com.project.server;

import jdk.jfr.ContentType;

import java.io.*;

/**
 * @author liuyulai
 * Created with IntelliJ IDEA.
 * Date: 21.6.10
 * Time: 14:01
 * Description: 处理写入流的响应类
 */
public class Response {
    /**
     * socket对象的写入流
     */
    private OutputStream outputStream;


    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }


    /**
     * 向客户端发送文本数据
     *
     * @param msg 文本数据
     */
    public void sendMessage(String msg) {
        try {
            this.outputStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将指定的文件数据读取到客户端
     *
     * @param path 文件所在的路径
     */
    public void sendFile(String path) {
        if (path == null) {
            return;
        }
        File file = new File(path);
        //判断文件是否存在
        boolean flag = file.exists();
        //判断文件是否存在,如果不存在则跳出方法
        if (!flag) {
            return;
        }
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
