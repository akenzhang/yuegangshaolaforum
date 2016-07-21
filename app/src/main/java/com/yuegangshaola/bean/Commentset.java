package com.yuegangshaola.bean;

import java.util.List;

/**
 * Created by new pc on 2016/7/17.
 */
public class Commentset {
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    private String pid;
    private String tid;
    private String postdatetime;
    private String message;
    private String ip;
    private String imgid;
    private String parentpid;
    private List<Replies> replies ;
    private String city;

    public void setTid(String tid){
        this.tid = tid;
    }

    public String getTid(){
        return this.tid;
    }

    public void setPostdatetime(String postdatetime){
        this.postdatetime = postdatetime;
    }

    public String getPostdatetime(){
        return this.postdatetime;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

    public void setIp(String ip){
        this.ip = ip;
    }

    public String getIp(){
        return this.ip;
    }

    public void setImgid(String imgid){
        this.imgid = imgid;
    }

    public String getImgid(){
        return this.imgid;
    }

    public void setParentpid(String parentpid){
        this.parentpid = parentpid;
    }

    public String getParentpid(){
        return this.parentpid;
    }

    public void setReplies(List<Replies> replies){
        this.replies = replies;
    }

    public List<Replies> getReplies(){
        return this.replies;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getCity(){
        return this.city;
    }
}
