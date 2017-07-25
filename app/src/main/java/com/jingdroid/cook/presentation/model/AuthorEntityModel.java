package com.jingdroid.cook.presentation.model;

/**
 * Created by Jing on 2017/7/24.
 */

public class AuthorEntityModel {
    private int author_id;
    private String author_name;
    private String author_head;
    private String author_sign;
    private int typeid;

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_head() {
        return author_head;
    }

    public void setAuthor_head(String author_head) {
        this.author_head = author_head;
    }

    public String getAuthor_sign() {
        return author_sign;
    }

    public void setAuthor_sign(String author_sign) {
        this.author_sign = author_sign;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }
}
