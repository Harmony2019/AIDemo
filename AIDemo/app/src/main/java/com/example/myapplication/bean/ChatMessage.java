package com.example.myapplication.bean;

import java.util.Date;

/**
 * Created by Administrator on 2017/2/8.
 */

/**
 * 获取接受到的两种类型的数据 并发送出去
 */

public class ChatMessage {


    private String name;
    private String msg;
    private Type type;
    private Date date;

    public ChatMessage(String msg,Type type,Date date) {
        this.date = date;
        this.msg = msg;
        this.type = type;
    }

    public ChatMessage(){

    }


    public enum Type {
        INCOMING, OUTCOMING;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
