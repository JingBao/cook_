package com.jingdroid.cook.data;

import com.jingdroid.cook.data.entity.AuthorEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by Jing on 2017/7/24.
 */

public interface IAuthorModel {
    Observable<List<AuthorEntity>> loadAuthor(String json);
}
