package com.jingdroid.cook.data.repository;

import com.jingdroid.cook.data.IArticleGroupModel;
import com.jingdroid.cook.data.IAuthorModel;
import com.jingdroid.cook.data.entity.ArticleEntity;
import com.jingdroid.cook.data.entity.ArticleGroupEntity;
import com.jingdroid.cook.data.entity.AuthorEntity;
import com.jingdroid.cook.data.net.RestApiImpl;

import java.util.List;

import rx.Observable;

/**
 * Created by Jing on 2017/7/24.
 */

public class ArticleGroupModel implements IArticleGroupModel {
    @Override
    public Observable<List<ArticleGroupEntity>> loadGroupWithType(String json) {
        return RestApiImpl.getInstance().loadArticleGroupData(json);
    }

    @Override
    public Observable<ArticleEntity> loadArticleInfo(String json) {
        return RestApiImpl.getInstance().loadArticleData(json);
    }
}
