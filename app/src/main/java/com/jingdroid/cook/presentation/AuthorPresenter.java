package com.jingdroid.cook.presentation;

import com.jingdroid.cook.data.IAuthorModel;
import com.jingdroid.cook.data.entity.ArticleGroupEntity;
import com.jingdroid.cook.data.entity.AuthorEntity;
import com.jingdroid.cook.data.repository.AuthorModel;
import com.jingdroid.cook.presentation.mapper.ModelDataMapper;
import com.jingdroid.cook.presentation.model.ArticleGroupEntityModel;
import com.jingdroid.cook.presentation.model.AuthorEntityModel;
import com.jingdroid.cook.view.IAuthorView;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by Jing on 2017/7/24.
 */

public class AuthorPresenter extends BasePresenter {
    private IAuthorModel mIAuthorModel;
    private IAuthorView mIAuthorView;
    Subscription subscription = Subscriptions.empty();

    public AuthorPresenter(IAuthorView mIAuthorView) {
        this.mIAuthorView = mIAuthorView;
        mIAuthorModel = new AuthorModel();
    }
    public void getAuthor(String json) {
        subscription = mIAuthorModel.loadAuthor(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<AuthorEntity>>() {

                    @Override
                    public void onCompleted() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onError(Throwable arg0) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onNext(List<AuthorEntity> arg0) {
                        // TODO Auto-generated method stub
                        List<AuthorEntityModel> list = ModelDataMapper.getInstance().transform_authorlist(arg0);
                        if (mIAuthorView != null)
                            mIAuthorView.loadAuthor(list);
                    }

                });
    }
}
