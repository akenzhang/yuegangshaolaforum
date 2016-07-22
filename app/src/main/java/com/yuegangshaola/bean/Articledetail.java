package com.yuegangshaola.bean;

/**
 * Created by new pc on 2016/7/14.
 */
public class Articledetail {
    private String mTitle;
    private String mContent;
    private String mCategory;
    private String mPoster;
    private String mPostDatetime;
    private String mTid;
    private String mFid;
    private String mViews;

    public String getmImageList() {
        return mImageList;
    }

    public void setmImageList(String mImageList) {
        this.mImageList = mImageList;
    }

    private String mImageList;

    public void setMTitle(String mTitle){
        this.mTitle = mTitle;
    }

    public String getMTitle(){
        return this.mTitle;
    }

    public void setMContent(String mContent){
        this.mContent = mContent;
    }

    public String getMContent(){
        return this.mContent;
    }

    public void setMCategory(String mCategory){
        this.mCategory = mCategory;
    }

    public String getMCategory(){
        return this.mCategory;
    }

    public void setMPoster(String mPoster){
        this.mPoster = mPoster;
    }

    public String getMPoster(){
        return this.mPoster;
    }

    public void setMPostDatetime(String mPostDatetime){
        this.mPostDatetime = mPostDatetime;
    }

    public String getMPostDatetime(){
        return this.mPostDatetime;
    }

    public void setMTid(String mTid){
        this.mTid = mTid;
    }

    public String getMTid(){
        return this.mTid;
    }

    public void setMFid(String mFid){
        this.mFid = mFid;
    }

    public String getMFid(){
        return this.mFid;
    }

    public void setMViews(String mViews){
        this.mViews = mViews;
    }

    public String getMViews(){
        return this.mViews;
    }
}
