package com.mantianhong.bean;

import java.util.List;

/**
 * Created by new pc on 2016/7/17.
 */
public class CommentRoot {

    private List<Commentset> commentset ;
    public void setCommentset(List<Commentset> commentset){
        this.commentset = commentset;
    }
    public List<Commentset> getCommentset(){
        return this.commentset;
    }
}
