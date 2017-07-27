package com.jingdroid.cook.data.repository;

import com.jingdroid.cook.data.IAuthorModel;
import com.jingdroid.cook.data.entity.ArticleGroupEntity;
import com.jingdroid.cook.data.entity.AuthorEntity;
import com.jingdroid.cook.data.net.RestApiImpl;

import java.util.List;

import rx.Observable;

/**
 * Created by Jing on 2017/7/24.
 */

public class AuthorModel implements IAuthorModel {
    @Override
    public Observable<List<AuthorEntity>> loadAuthor(String json) {
        return RestApiImpl.getInstance().loadAuthorData(json);
    }

    @Override
    public Observable<List<ArticleGroupEntity>> loadAuthorGroup(String json) {
        return RestApiImpl.getInstance().loadAuthorGroupData(json);
    }
}
