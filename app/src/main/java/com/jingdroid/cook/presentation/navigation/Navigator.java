package com.jingdroid.cook.presentation.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.jingdroid.cook.presentation.model.AuthorEntityModel;
import com.jingdroid.cook.view.activity.AboutActivity;
import com.jingdroid.cook.view.activity.ArticleInfoActivity;
import com.jingdroid.cook.view.activity.AuthorInfoActivity;
import com.jingdroid.cook.view.activity.CookersActivity;
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
    public void navigateToArticleInfoActivity(Context context, int groupid, String name ) {
        if (context != null) {
            Intent intentToLaunch = ArticleInfoActivity.getCallingIntent(context, groupid, name);
            context.startActivity(intentToLaunch);
        }
    }
    /**
     * 用户信息
     * @param context
     */
    public void navigateToUserInfoActivity(Context context, String name, String sign, int requestCode) {
        if (context != null) {
            Intent intentToLaunch = UserInfoActivity.getCallingIntent(context, name, sign);
            ((Activity)context).startActivityForResult(intentToLaunch, requestCode);
        }
    }

    /**
     * 所有厨娘
     * @param context
     */
    public void navigateToCookersActivity(Context context) {
        if (context != null) {
            Intent intentToLaunch = CookersActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * 关于厨娘
     * @param context
     */
    public void navigateToAboutActivity(Context context) {
        if (context != null) {
            Intent intentToLaunch = AboutActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }
}
