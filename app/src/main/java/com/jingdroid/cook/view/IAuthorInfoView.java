package com.jingdroid.cook.view;

import com.jingdroid.cook.presentation.model.ArticleEntityModel;
import com.jingdroid.cook.presentation.model.ArticleGroupEntityModel;

import java.util.List;

/**
 * Created by Jing on 2017/7/27.
 */

public interface IAuthorInfoView extends ILoadDataView{
    void loadAuthorGroup(List<ArticleGroupEntityModel> list);
    void loadArticleInfo(ArticleEntityModel article);
}
