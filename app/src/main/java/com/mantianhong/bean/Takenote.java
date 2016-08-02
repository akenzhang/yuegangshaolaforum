package com.mantianhong.bean;

import org.w3c.dom.ProcessingInstruction;

/**
 * Created by AKENZHANG on 2016/8/2.
 */
public class Takenote {

    private String tid;
    private String username;
    private String poster;
    private String title;
    private String postdatetime;

    public void setTid(String tid){
        this.tid = tid;
    }

    public String getTid(){
        return this.tid;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public void setPoster(String poster){
        this.poster = poster;
    }

    public String getPoster(){
        return this.poster;
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
