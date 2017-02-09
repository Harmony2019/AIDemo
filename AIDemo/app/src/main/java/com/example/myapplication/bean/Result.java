package com.example.myapplication.bean;

/**
 * Created by Administrator on 2017/2/8.
 */

/**
 * GSON返回的数据在这里处理
 */

public class Result {
    private int code;
    private String text;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
