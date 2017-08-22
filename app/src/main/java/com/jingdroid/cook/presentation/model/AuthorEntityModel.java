package com.jingdroid.cook.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jing on 2017/7/24.
 */

public class AuthorEntityModel implements Parcelable{
    private int author_id;
    private String author_name;
    private String author_head;
    private String author_sign;
    private int typeid;
    private boolean isAdd;

    public AuthorEntityModel() {

    }

    private AuthorEntityModel(Parcel in) {
        author_id = in.readInt();
        author_name = in.readString();
        author_head = in.readString();
        author_sign = in.readString();
        typeid = in.readInt();
    }

    public static final Creator<AuthorEntityModel> CREATOR = new Creator<AuthorEntityModel>() {
        @Override
        public AuthorEntityModel createFromParcel(Parcel in) {
            return new AuthorEntityModel(in);
        }

        @Override
        public AuthorEntityModel[] newArray(int size) {
            return new AuthorEntityModel[size];
        }
    };

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

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(author_id);
        dest.writeString(author_name);
        dest.writeString(author_head);
        dest.writeString(author_sign);
        dest.writeInt(typeid);
    }
}
