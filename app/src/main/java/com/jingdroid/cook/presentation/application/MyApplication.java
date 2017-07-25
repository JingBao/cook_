package com.jingdroid.cook.presentation.application;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by Jing on 2017/7/24.
 */

public class MyApplication extends Application {

    private final String TAG = "MyApplication";
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
    }

    private void initImageLoader(){
        imageLoader = ImageLoader.getInstance();
        File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "/Android/data/"+getApplicationContext().getPackageName()+"/cache");
        Log.d(TAG, "cacheDir:"+cacheDir.getPath());

        options = new DisplayImageOptions.Builder()
//				.cacheInMemory().cacheOnDisc() 					// 缓存
//				.displayer(new RoundedBitmapDisplayer(6)) 		// 设置成圆角图片
                .bitmapConfig(Bitmap.Config.RGB_565)    		// 设置图片的质量
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)	// 设置图片的缩放类型，该方法可以有效减少内存的占用
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .denyCacheImageMultipleSizesInMemory()
                .threadPoolSize(3)
                .memoryCacheSize(2 * 1024 * 1024) 								//设置内存缓存的大小
//                .discCache(new LimitedAgeDiscCache(cacheDir, 2*7*24*60*60))		//2周清除缓存
                .build();
        imageLoader.init(config);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
    public DisplayImageOptions getOptions() {
        return options;
    }
}
