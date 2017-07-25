package com.jingdroid.cook.presentation.model;

/**
 * Created by Jing on 2017/7/25.
 */

public class ArticleGroupEntityModel {
    private int article_group_id;
    private String article_group_title;
    private String article_group_head;
    private int authorid;
    private int typeid;

    public int getArticle_group_id() {
        return article_group_id;
    }

    public void setArticle_group_id(int article_group_id) {
        this.article_group_id = article_group_id;
    }

    public String getArticle_group_title() {
        return article_group_title;
    }

    public void setArticle_group_title(String article_group_title) {
        this.article_group_title = article_group_title;
    }

    public String getArticle_group_head() {
        return article_group_head;
    }

    public void setArticle_group_head(String article_group_head) {
        this.article_group_head = article_group_head;
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
