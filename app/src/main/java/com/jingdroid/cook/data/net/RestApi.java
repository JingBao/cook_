package com.jingdroid.cook.data.net;

import com.jingdroid.cook.data.entity.ArticleEntity;
import com.jingdroid.cook.data.entity.ArticleGroupEntity;
import com.jingdroid.cook.data.entity.AuthorEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by Jing on 2017/7/24.
 */

public interface RestApi {
//    String API_BASE_URL = "http://www.jingdroid.com/CookApplication/";
    String API_BASE_URL = "http://10.110.30.134:8080/CookApplication/";

    /* 查询编辑者-通过typeid */
    String API_GET_AUTHOR = API_BASE_URL + "queryAuthorWithType";
    /* 查询文章组-通过typeid */
    String API_GET_ARTICLE_GROUP = API_BASE_URL + "queryGroupWithType";

    String API_GET_AUTHOR_GROUP = API_BASE_URL + "queryGroupWithAuthor";

    String API_GET_ARTICLE_INFO = API_BASE_URL + "queryArticleWithGroup";


    Observable<List<AuthorEntity>> loadAuthorData(String json);

    Observable<List<ArticleGroupEntity>> loadArticleGroupData(String json);

    Observable<List<ArticleGroupEntity>> loadAuthorGroupData(String json);
    /**
     * 获取文章信息
     * @param json
     * @return
     */
    Observable<ArticleEntity> loadArticleData(String json);
}
