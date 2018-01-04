package com.example.administrator.myapplication.util;

import android.os.Handler;
import android.os.Message;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2018/1/2.
 */

public class HttpUtil {


    /**
     * agrs0 = 页码
     * args1= 查询的参数
     * @param param
     * @return
     */
    //get方式登录
    public  static String httpGet( String[] param ) {
        //1：url对象
        URL url = null;
        String keyword  ="dna" ;
        long pageNo = 0 ;
        try {
            if(param[1] == null  || param[1].trim() != ""){
                keyword = param[1].trim() ;
            }
            if(param[0] == null  || param[0].trim() != ""){
                  pageNo = Long.valueOf(param[0]);
            }
            String address ="http://test.scholarmate.com/app/pubweb/search/pdwhpaper?searchString=" +keyword+
                    "&page.pageNo=" +pageNo+
                    "&searchType=1&token=QCIwr8Q2f9lIlhFnlLCbwbkbpKJzR22NL7IFhWRVhXrlD6TeSuIKIXb7wKqCz6YeBlBuxrYDCeDx5S%2BYKN6uaQ%3D%3D"  ;
            url = new URL(address);
            //2;url.openconnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //3
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10 * 1000);
            //4
            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream inputStream = conn.getInputStream();
                String result = streamToString(inputStream);
                //System.out.println("=====================服务器返回的信息：：" + result);
                return result ;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "" ;
    }

    //get方式登录
    public static void requestNetForGetLogin(final Handler handler,final String username,final String password) {
        //在子线程中操作网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                //urlConnection请求服务器，验证
                try {
                    //1：url对象
                    URL url = new URL("http://192.168.1.100:8081//servlet/LoginServlet?username=" + URLEncoder.encode(username)+ "&pwd=" +  URLEncoder.encode(password) + "");
                    //2;url.openconnection
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //3
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(10 * 1000);
                    //4
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = conn.getInputStream();
                        String result = streamToString(inputStream);

                        System.out.println("=====================服务器返回的信息：：" + result);
                        boolean isLoginsuccess=false;
                        if (result.contains("success")) {
                            isLoginsuccess=true;
                        }
                        Message msg = Message.obtain();
                        msg.obj=isLoginsuccess;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 将输入流转换成字符串
     *
     * @param is 从网络获取的输入流
     * @return
     */
    public  static String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
           // Log.e(TAG, e.toString());
            return null;
        }
    }
    //post方式登录
    public static void requestNetForPOSTLogin(final Handler handler,final String username,final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //urlConnection请求服务器，验证
                try {
                    //1：url对象
                    URL url = new URL("http://192.168.1.100:8081//servlet/LoginServlet");

                    //2;url.openconnection
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    //3设置请求参数
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(10 * 1000);
                    //请求头的信息
                    String body = "username=" + URLEncoder.encode(username) + "&pwd=" + URLEncoder.encode(password);
                    conn.setRequestProperty("Content-Length", String.valueOf(body.length()));
                    conn.setRequestProperty("Cache-Control", "max-age=0");
                    conn.setRequestProperty("Origin", "http://192.168.1.100:8081");

                    //设置conn可以写请求的内容
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(body.getBytes());

                    //4响应码
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = conn.getInputStream();
                        String result = streamToString(inputStream);
                        System.out.println("=====================服务器返回的信息：：" + result);
                        boolean isLoginsuccess=false;
                        if (result.contains("success")) {
                            isLoginsuccess=true;
                        }
                        Message msg = Message.obtain();
                        msg.obj=isLoginsuccess;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
