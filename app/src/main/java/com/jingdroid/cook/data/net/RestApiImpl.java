package com.jingdroid.cook.data.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
}
