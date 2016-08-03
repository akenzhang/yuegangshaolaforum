package com.mantianhong.bean;

/**
 * Created by AKENZHANG on 2016/8/2.
 */
public class Mycomment {

    private String tid;
    private String message;
    private String title;
    private String postdatetime;

    public void setTid(String tid){
        this.tid = tid;
    }

    public String getTid(){
        return this.tid;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setPostdatetime(String postdatetime){
        this.postdatetime = postdatetime;
    }

    public String getPostdatetime(){
        return this.postdatetime;
    }

}
