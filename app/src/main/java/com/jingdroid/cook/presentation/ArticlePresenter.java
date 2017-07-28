package com.jingdroid.cook.presentation;

import com.jingdroid.cook.data.IArticleGroupModel;
import com.jingdroid.cook.data.IAuthorModel;
import com.jingdroid.cook.data.entity.ArticleEntity;
import com.jingdroid.cook.data.entity.AuthorEntity;
import com.jingdroid.cook.data.repository.ArticleGroupModel;
import com.jingdroid.cook.data.repository.AuthorModel;
import com.jingdroid.cook.presentation.mapper.ModelDataMapper;
import com.jingdroid.cook.presentation.model.ArticleEntityModel;
import com.jingdroid.cook.presentation.model.AuthorEntityModel;
import com.jingdroid.cook.view.IAuthorInfoView;
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

public class ArticlePresenter extends BasePresenter {
    private IArticleGroupModel mIAuthorModel;
    private IAuthorInfoView mIAuthorView;
    Subscription subscription = Subscriptions.empty();

    public ArticlePresenter(IAuthorInfoView mIAuthorView) {
        this.mIAuthorView = mIAuthorView;
        mIAuthorModel = new ArticleGroupModel();
    }
    public void getArticle(String json) {
        subscription = mIAuthorModel.loadArticleInfo(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArticleEntity>() {

                    @Override
                    public void onCompleted() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onError(Throwable arg0) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onNext(ArticleEntity arg0) {
                        // TODO Auto-generated method stub
                        ArticleEntityModel model = ModelDataMapper.getInstance().transform_article(arg0);
                        if (mIAuthorView != null)
                            mIAuthorView.loadArticleInfo(model);
                    }

                });
    }
}
