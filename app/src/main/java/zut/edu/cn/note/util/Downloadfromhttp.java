package zut.edu.cn.note.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public  class Downloadfromhttp {
    public static String download(String path) throws IOException {
        //请求服务器端
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(3000);//设置连接超时事件
        connection.setReadTimeout(3000); //设置读取超时事件
        connection.connect();
        //获取io输入字节流->字符流->缓存流
        InputStream inputStream = connection.getInputStream();//字节流
        Reader reader = new InputStreamReader(inputStream);//字符流
        BufferedReader bufferedReader = new BufferedReader(reader);//缓存流
        String result = "";
        String temp;
        while ((temp = bufferedReader.readLine()) != null) {
            result += temp;
        }
        inputStream.close();
        reader.close();
        bufferedReader.close();
        return  result;
    }
}
