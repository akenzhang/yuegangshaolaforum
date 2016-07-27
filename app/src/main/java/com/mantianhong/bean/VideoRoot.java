package com.mantianhong.bean;

import java.util.List;

/**
 * Created by AKENZHANG on 2016/7/27.
 */
public class VideoRoot {

    private List<Video> Video ;

    public void setVideo(List<Video> Video){
        this.Video = Video;
    }
    public List<Video> getVideo(){
        return this.Video;
    }

}
