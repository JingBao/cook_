package com.jingdroid.cook.data.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.jingdroid.cook.data.entity.ArticleEntity;
import com.jingdroid.cook.data.entity.ArticleGroupEntity;
import com.jingdroid.cook.data.entity.AuthorEntity;

import org.json.JSONObject;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Jing on 2017/7/24.
 */

public class RestApiImpl implements RestApi {

    private static RestApiImpl mApiImpl;

    public static RestApiImpl getInstance() {
        if (mApiImpl == null) {
            mApiImpl = new RestApiImpl();
        }
        return mApiImpl;
    }
    @Override
    public Observable<List<AuthorEntity>> loadAuthorData(final String json) {
        Observable<List<AuthorEntity>> observable = Observable.create(new Observable.OnSubscribe<List<AuthorEntity>>() {
            @Override
            public void call(Subscriber<? super List<AuthorEntity>> subscriber) {
                try {
                    String response = ApiConnection.createPost(API_GET_AUTHOR).requestSyncCallPost(json);
                    JSONObject json = new JSONObject(response);
                    String result = json.getString("users");
                    Gson gson = new Gson();
                    List<AuthorEntity> list = gson.fromJson(result, new TypeToken<List<AuthorEntity>>() {}.getType());
                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return observable;
    }

    @Override
    public Observable<List<ArticleGroupEntity>> loadArticleGroupData(final String json) {
        Observable<List<ArticleGroupEntity>> observable = Observable.create(new Observable.OnSubscribe<List<ArticleGroupEntity>>() {
            @Override
            public void call(Subscriber<? super List<ArticleGroupEntity>> subscriber) {
                try {
                    String response = ApiConnection.createPost(API_GET_ARTICLE_GROUP).requestSyncCallPost(json);
                    JSONObject json = new JSONObject(response);
                    String result = json.getString("groups");
                    Gson gson = new Gson();
                    List<ArticleGroupEntity> list = gson.fromJson(result, new TypeToken<List<ArticleGroupEntity>>() {}.getType());
                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return observable;
    }

    @Override
    public Observable<List<ArticleGroupEntity>> loadAuthorGroupData(final String json) {
        Observable<List<ArticleGroupEntity>> observable = Observable.create(new Observable.OnSubscribe<List<ArticleGroupEntity>>() {
            @Override
            public void call(Subscriber<? super List<ArticleGroupEntity>> subscriber) {
                try {
                    String response = ApiConnection.createPost(API_GET_AUTHOR_GROUP).requestSyncCallPost(json);
                    JSONObject json = new JSONObject(response);
                    String result = json.getString("groups");
                    Gson gson = new Gson();
                    List<ArticleGroupEntity> list = gson.fromJson(result, new TypeToken<List<ArticleGroupEntity>>() {}.getType());
                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return observable;
    }

    /**
     * 获取文章信息
     *
     * @param json
     * @return
     */
    @Override
    public Observable<ArticleEntity> loadArticleData(final String json) {
        Observable<ArticleEntity> observable = Observable.create(new Observable.OnSubscribe<ArticleEntity>() {
            @Override
            public void call(Subscriber<? super ArticleEntity> subscriber) {
                try {
                    String response = ApiConnection.createPost(API_GET_ARTICLE_INFO).requestSyncCallPost(json);
                    JSONObject json = new JSONObject(response);
                    String result = json.getString("article");

                    Gson gson = new Gson();
                    List<ArticleEntity> list = gson.fromJson(result, new TypeToken<List<ArticleEntity>>() {}.getType());

//                    JSONObject object = new JSONObject(result);
//                    ArticleEntity article = new ArticleEntity();
//                    article.setArticle_id(object.getInt("article_id"));
//                    article.setArticle_title(object.getString("article_title"));
//                    article.setArticle_context(object.getString("article_context"));
//                    article.setArticle_quantity(object.getInt("article_quantity"));
//                    article.setArticle_groupid(object.getInt("article_groupid"));
//                    article.setAuthorid(object.getInt("authorid"));
//                    article.setTypeid(object.getInt("typeid"));

                    subscriber.onNext(list.get(0));
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return observable;
    }
}
