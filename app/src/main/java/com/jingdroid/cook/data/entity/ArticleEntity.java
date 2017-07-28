package com.jingdroid.cook.data.entity;

/**
 * Created by Jing on 2017/7/27.
 */

public class ArticleEntity {
    private int article_id;
    private String article_title;
    private String article_context;
    private int article_quantity;
    private int article_groupid;
    private int authorid;
    private int typeid;

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getArticle_context() {
        return article_context;
    }

    public void setArticle_context(String article_context) {
        this.article_context = article_context;
    }

    public int getArticle_quantity() {
        return article_quantity;
    }

    public void setArticle_quantity(int article_quantity) {
        this.article_quantity = article_quantity;
    }

    public int getArticle_groupid() {
        return article_groupid;
    }

    public void setArticle_groupid(int article_groupid) {
        this.article_groupid = article_groupid;
    }

    public int getAuthorid() {
        return authorid;
    }

    public void setAuthorid(int authorid) {
        this.authorid = authorid;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }
}
