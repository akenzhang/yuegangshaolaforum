package com.mantianhong.bean;

import java.util.List;

/**
 * Created by AKENZHANG on 2016/8/17.
 */
public class MyArticleRoot {

    private List<Myarticle> myarticle ;
    public void setMyarticle(List<Myarticle> myarticle){
        this.myarticle = myarticle;
    }

    public List<Myarticle> getMyarticle(){
        return this.myarticle;
    }
}
