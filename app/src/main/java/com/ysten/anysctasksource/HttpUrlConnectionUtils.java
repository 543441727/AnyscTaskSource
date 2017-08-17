package com.ysten.anysctasksource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * author : wangjitao
 * e-mail : 543441727@qq.com
 * time   : 2017/08/16
 * desc   : 网络工具类
 * version: 1.0
 */
public class HttpUrlConnectionUtils {
    public static byte[] getByte(String path){
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(path);
            if (url != null) {
                httpURLConnection = (HttpURLConnection) url.openConnection();

                // 设置连接网络的超时时间
                httpURLConnection.setRequestMethod("GET");//GET和POST必须全大写
                httpURLConnection.setConnectTimeout(10000);//连接的超时时间
                httpURLConnection.setReadTimeout(5000);//读数据的超时时间

                // 表示设置本次http请求使用GET方式请求
                httpURLConnection.setRequestMethod("GET");
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {
                    // 从服务器获得一个输入流
                    InputStream is = httpURLConnection.getInputStream();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len=0;
                    while((len=is.read(buffer))!=-1){
                        baos.write(buffer, 0, len);
                    }
                    is.close();
                    byte[] datas = baos.toByteArray();
                    baos.close();

                    return datas;

                }
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}
