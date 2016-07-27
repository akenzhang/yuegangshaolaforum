package com.mantianhong.bean;

/**
 * Created by AKENZHANG on 2016/7/27.
 */
public class Video {

    private String id;
    private String desc;
    private String link;
    private String pic;
    private String views;

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public String getDesc(){
        return this.desc;
    }

    public void setLink(String link){
        this.link = link;
    }

    public String getLink(){
        return this.link;
    }

    public void setPic(String pic){
        this.pic = pic;
    }

    public String getPic(){
        return this.pic;
    }

    public void setViews(String views){
        this.views = views;
    }

    public String getViews(){
        return this.views;
    }
}
