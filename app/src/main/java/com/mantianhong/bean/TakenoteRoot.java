package com.mantianhong.bean;

import java.util.List;

/**
 * Created by AKENZHANG on 2016/8/2.
 */
public class TakenoteRoot {
    private List<Takenote> takenote ;
    public void setTakenote(List<Takenote> takenote){
        this.takenote = takenote;
    }

    public List<Takenote> getTakenote(){
        return this.takenote;
    }
}
