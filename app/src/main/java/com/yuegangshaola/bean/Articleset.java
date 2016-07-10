package com.yuegangshaola.bean;

import java.util.List;

/**
 * Created by new pc on 2016/7/6.
 */
public class Articleset {

    private String totalrowcont;
    private List<Article> article ;

    public void setTotalrowcont(String totalrowcont){
        this.totalrowcont = totalrowcont;
    }

    public String getTotalrowcont(){
        return this.totalrowcont;
    }

    public void setArticle(List<Article> article){
        this.article = article;
    }

    public List<Article> getArticle(){
        return this.article;
    }

}
