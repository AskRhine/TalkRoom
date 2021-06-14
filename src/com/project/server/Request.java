package com.project.server;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyulai
 * Created with IntelliJ IDEA.
 * Date: 21.6.10
 * Time: 15:06
 * Description: 处理读取流的请求类
 */
public class Request {
    private String url;
    /**
     * 用于封装表单数据的map集合
     */
    private Map<String, String> formMap = new HashMap<>();

    public Request(InputStream inputStream) {
//        System.out.println("-----客户端请求至服务器-----");
        try {
            //接受客户端发送的数据
            byte[] by = new byte[1024];
            inputStream.read(by);
            String info = new String(by).trim();
//            System.out.println(info);
            if (info != null) {
                url = splitUrl(info);
                System.out.println("url=" + url);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String splitUrl(String info) {
        String urls = "";
        urls = info.split("\\s+")[1].substring(1);
        if (info.startsWith("POST")) {
            String[] infos = info.split("\\s+");
            String userinfo = infos[infos.length - 1];
            pressUrlInfo(userinfo);
        } else if (info.startsWith("GET")) {
            //get的提交是否包含了键值对数据
            if (urls.contains("?")) {
                String[] userInfo = urls.split("[?]");
                String usersInfo = userInfo[1];
                pressUrlInfo(usersInfo);
                urls = userInfo[0];
            }
        }
        return urls;
    }

    /**
     * 将形如A=1&B=2这种字符串封装为map集合
     *
     * @param usersInfo
     */
    private void pressUrlInfo(String usersInfo) {
        String[] paramArray = usersInfo.split("&");
        for (String s : paramArray) {
            //需要判断数组的长度是否=2，如果不等于则不封装
            String[] spl = s.split("=");
            if (spl.length == 2) {
                this.formMap.put(spl[0], spl[1]);
            } else {
                this.formMap.put(spl[0], "");
            }
        }
    }

    /**
     * 根据表单名返回表单值
     *
     * @param key
     * @return
     */
    public String getParameter(String key) {
        return this.formMap.get(key);
    }


    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Request{" +
                "url='" + url + '\'' +
                ", formMap=" + formMap +
                '}';
    }
}
