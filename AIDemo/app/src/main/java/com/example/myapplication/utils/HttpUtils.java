package com.example.myapplication.utils;

import com.example.myapplication.bean.ChatMessage;
import com.example.myapplication.bean.Result;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/7.
 */

public class HttpUtils {
    private static final String URL = "http://www.tuling123.com/openapi/api";

    private static final String API_KEY = "请自行申请key";

    /**
     * 发送一个消息，得到返回的信息
     * @param msg
     * @return
     */
    public static ChatMessage sendMessage(String msg) {
        ChatMessage chatMessage = new ChatMessage();
        String jsonRes = doGet(msg);
        Gson gson = new Gson();
        Result result = null;
        try {
            result = gson.fromJson(jsonRes, Result.class);
            chatMessage.setMsg(result.getText());
        } catch (JsonSyntaxException e) {
            chatMessage.setMsg("服务器繁忙，请稍后再试！");
        }
        chatMessage.setDate(new Date());
        chatMessage.setType(ChatMessage.Type.INCOMING);
        return chatMessage;
    }

    /**
     * 出去接受过来的文本信息
     * @param msg
     * @return GSON格式的字符串
     */

    public static String doGet(String msg) {
        String result = "";
        String url = setParams(msg);
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            URL urlNet = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlNet
                    .openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            is = conn.getInputStream();
            int len = -1;
            byte[] buf = new byte[128];
            baos = new ByteArrayOutputStream();
            while ((len = is.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }

            result = new String(baos.toByteArray());
            baos.flush();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;

    }

    private static String setParams(String msg) {
        String url = null;
        try {
            url = URL + "?key=" + API_KEY + "&info=" + URLEncoder.encode(msg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;

    }
}
