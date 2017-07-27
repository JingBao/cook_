package com.jingdroid.cook.data;

import com.jingdroid.cook.data.entity.ArticleEntity;
import com.jingdroid.cook.data.entity.ArticleGroupEntity;
import com.jingdroid.cook.data.entity.AuthorEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by Jing on 2017/7/24.
 */

public interface IArticleGroupModel {
    Observable<List<ArticleGroupEntity>> loadGroupWithType(String json);
    Observable<ArticleEntity> loadArticleInfo(String json);
}
