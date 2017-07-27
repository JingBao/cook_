package com.jingdroid.cook.view.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jingdroid.cook.R;
import com.jingdroid.cook.presentation.GroupPresenter;
import com.jingdroid.cook.presentation.application.MyApplication;
import com.jingdroid.cook.presentation.model.ArticleGroupEntityModel;
import com.jingdroid.cook.view.fragment.RecommentFragment;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by Jing on 2017/7/26.
 */

public class GroupInfoAdapter extends BaseAdapter {

    private Context context;
    private List<ArticleGroupEntityModel> list = new ArrayList<>();

    public GroupInfoAdapter(Context context) {
        this.context = context;
    }

    public void setListData(List<ArticleGroupEntityModel> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_group_layout, null);
            mHolder = new ViewHolder();
            mHolder.iv_group_bg = (ImageView) convertView.findViewById(R.id.iv_group_bg);
            mHolder.tv_group_title = (TextView) convertView.findViewById(R.id.tv_group_title);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.tv_group_title.setText(list.get(position).getArticle_group_title());
        if (list.get(position).getArticle_group_head() != null && !"".equals(list.get(position).getArticle_group_head())) {
            ((MyApplication) (context.getApplicationContext())).getImageLoader().displayImage(list.get(position).getArticle_group_head(),
                    new BgImageViewAware(mHolder.iv_group_bg));
        }
        return convertView;
    }

    class ViewHolder {
        ImageView iv_group_bg;
        TextView tv_group_title;
    }

    public class BgImageViewAware extends ImageViewAware {

        ImageView imageView;

        public BgImageViewAware(ImageView imageView) {
            this(imageView, true);
        }

        public BgImageViewAware(ImageView imageView, boolean checkActualViewSize) {
            super(imageView, checkActualViewSize);
            this.imageView = imageView;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public boolean setImageBitmap(Bitmap bitmap) {
            //重写父类方法，将图片设为背景
            if (Looper.myLooper() == Looper.getMainLooper()) {
//                ImageView imageView = (ImageView)this.viewRef.get();
                if (imageView != null) {
                    imageView.setBackground(new BitmapDrawable(bitmap));
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean setImageDrawable(Drawable drawable) {
            return super.setImageDrawable(drawable);
        }
    }

}
