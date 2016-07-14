package com.yuegangshaola.bean;

import java.util.List;

/**
 * Created by new pc on 2016/7/14.
 */
public class ArticledetailRoot {
    private List<Articledetail> articledetail ;

    public void setArticledetail(List<Articledetail> articledetail){
        this.articledetail = articledetail;
    }

    public List<Articledetail> getArticledetail(){
        return this.articledetail;
    }
}
