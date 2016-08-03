package com.mantianhong.bean;

import java.util.List;

/**
 * Created by AKENZHANG on 2016/8/3.
 */
public class MycommentRoot {

    private List<Mycomment> mycomment ;

    public void setMycomment(List<Mycomment> mycomment){
        this.mycomment = mycomment;
    }

    public List<Mycomment> getMycomment(){
        return this.mycomment;
    }
}
