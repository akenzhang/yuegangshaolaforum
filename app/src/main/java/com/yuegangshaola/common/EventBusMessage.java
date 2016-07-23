package com.yuegangshaola.common;

/**
 * Created by AKENZHANG on 2016/7/23.
 */
public class EventBusMessage {
    private String mMsg;
    private int mTid;

    public int getmTid() {
        return mTid;
    }

    public void setmTid(int mPid) {
        this.mTid = mPid;
    }

    public EventBusMessage(String msg, int intPid){
        this.mMsg = msg;
        this.mTid = intPid;

    }
    public String getMsg(){
        return mMsg;
    }

}
