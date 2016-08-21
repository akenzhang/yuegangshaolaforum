package com.mantianhong.bean;

import java.util.List;

/**
 * Created by AKENZHANG on 2016/8/21.
 */
public class MysearchRoot {
    private List<Mysearch> mysearch ;
    public void setMysearch(List<Mysearch> mysearch){
        this.mysearch = mysearch;
    }

    public List<Mysearch> getMysearch(){
        return this.mysearch;
    }
}
