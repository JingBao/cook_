package com.jingdroid.cook.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.jingdroid.cook.R;
import com.jingdroid.cook.presentation.utils.BitmapBlurUtil;

public class ArticleGroupActivity extends AppCompatActivity {

    private static final String INTENT_EXTRA_PARAMS = "groupbg";

    private Toolbar toolbar;
    private ImageButton ibBack;
    private ImageButton ibShare;
    private ImageButton ibCollection;
    private CoordinatorLayout layoutGroup;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, ArticleGroupActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_article_group);
        Bundle bundle = getIntent().getExtras();
        Bitmap bm = bundle.getParcelable("groupbg");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ibBack = (ImageButton) findViewById(R.id.ib_back);
        ibShare = (ImageButton) findViewById(R.id.ib_share);
        ibCollection = (ImageButton) findViewById(R.id.ib_collection);
        layoutGroup = (CoordinatorLayout) findViewById(R.id.layout_group);
        Bitmap mDest = BitmapBlurUtil.doBlur(bm, 10, true, ArticleGroupActivity.this);
        layoutGroup.setBackground(new BitmapDrawable(getResources(), mDest));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void blur(Bitmap bkg, View view, float radius) {
        Bitmap overlay = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.drawBitmap(bkg, -view.getLeft(), -view.getTop(), null);
        RenderScript rs = RenderScript.create(this);
        Allocation overlayAlloc = Allocation.createFromBitmap(rs, overlay);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());
        blur.setInput(overlayAlloc);
        blur.setRadius(radius);
        blur.forEach(overlayAlloc);
        overlayAlloc.copyTo(overlay);
        view.setBackground(new BitmapDrawable(getResources(), overlay));
        rs.destroy();
    }

}
