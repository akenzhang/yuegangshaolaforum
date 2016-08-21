package com.mantianhong.bean;

/**
 * Created by AKENZHANG on 2016/8/21.
 */
public class Mysearch {

    private String tid;
    private String fid;
    private String poster;
    private String title;
    private String views;
    private String replies;
    private String postdatetime;

    public void setTid(String tid){
        this.tid = tid;
    }

    public String getTid(){
        return this.tid;
    }

    public void setFid(String fid){
        this.fid = fid;
    }

    public String getFid(){
        return this.fid;
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

    public void setViews(String views){
        this.views = views;
    }

    public String getViews(){
        return this.views;
    }

    public void setReplies(String replies){
        this.replies = replies;
    }

    public String getReplies(){
        return this.replies;
    }

    public void setPostdatetime(String postdatetime){
        this.postdatetime = postdatetime;
    }

    public String getPostdatetime(){
        return this.postdatetime;
    }
}
