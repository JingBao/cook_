package com.jingdroid.cook.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.jingdroid.cook.presentation.model.AuthorEntityModel;
import com.jingdroid.cook.view.activity.ArticleInfoActivity;
import com.jingdroid.cook.view.activity.AuthorInfoActivity;
import com.jingdroid.cook.view.activity.UserInfoActivity;

/**
 * Created by Jing on 2017/7/27.
 */

public class Navigator {
    public static Navigator instance;

    public static Navigator getInstance() {
        if (instance == null) {
            instance = new Navigator();
        }
        return instance;
    }

    private Navigator(){

    }

    /**
     * author信息页
     * @param context
     * @param author
     */
    public void navigateToAuthorInfoActivity(Context context, AuthorEntityModel author) {
        if (context != null) {
            Intent intentToLaunch = AuthorInfoActivity.getCallingIntent(context, author);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * article信息页
     * @param context
     * @param groupid
     */
    public void navigateToArticleInfoActivity(Context context, int groupid) {
        if (context != null) {
            Intent intentToLaunch = ArticleInfoActivity.getCallingIntent(context, groupid);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * 用户信息
     * @param context
     */
    public void navigateToUserInfoActivity(Context context) {
        if (context != null) {
            Intent intentToLaunch = UserInfoActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }
}
