package com.jingdroid.cook.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jingdroid.cook.R;
import com.jingdroid.cook.presentation.ArticlePresenter;
import com.jingdroid.cook.presentation.model.ArticleEntityModel;
import com.jingdroid.cook.presentation.model.ArticleGroupEntityModel;
import com.jingdroid.cook.presentation.utils.ScreenUtils;
import com.jingdroid.cook.view.IAuthorInfoView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.XMLReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ArticleInfoActivity extends BaseActivity implements IAuthorInfoView,View.OnClickListener {

    private static final String INTENT_EXTRA_PARAMS_GROUPID = "groupid";
    private static final String INTENT_EXTRA_PARAMS_NAME = "name";
    private ImageButton ibBack;
    private ImageButton ibShare;
    private ImageButton ibCollection;
    private TextView tvArticleContent;
    private TextView tvArticleTitle;

    private String mName;

    public static Intent getCallingIntent(Context context, int groupid, String name) {
        Intent callingIntent = new Intent(context, ArticleInfoActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAMS_GROUPID, groupid);
        callingIntent.putExtra(INTENT_EXTRA_PARAMS_NAME, name);
        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_info);
        setMeizuStatusBarDarkIcon(this, true);
        int groupid = getIntent().getIntExtra(INTENT_EXTRA_PARAMS_GROUPID, 0);
        mName = getIntent().getStringExtra(INTENT_EXTRA_PARAMS_NAME);
        initView();
        ArticlePresenter mPresenter = new ArticlePresenter(this);
        JSONObject json = new JSONObject();
        try {
            json.put("groupid", groupid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPresenter.getArticle(json.toString());
    }

    private void initView() {
        ibBack = (ImageButton) findViewById(R.id.ib_back);
        ibCollection = (ImageButton) findViewById(R.id.ib_collection);
        ibShare = (ImageButton) findViewById(R.id.ib_share);
        tvArticleContent = (TextView) findViewById(R.id.tv_article_content);
        tvArticleTitle = (TextView) findViewById(R.id.tv_article_title);
        ibBack.setOnClickListener(this);
        tvArticleTitle.setText(mName);
    }

    @Override
    public void loadAuthorGroup(List<ArticleGroupEntityModel> list) {

    }

    @Override
    public void loadArticleInfo(ArticleEntityModel article) {
        HtmlImageGetter htmlImageGetter = new HtmlImageGetter();
        Spanned spanned = Html.fromHtml(article.getArticle_context().replace("<p", "<a").replace("</p>", "</a>"), htmlImageGetter, null);
        Log.d("ArticleInfoActivity", "spanned:"+article.getArticle_context().replace("<p", "<a").replace("</p>", "</a>"));

        tvArticleContent.setText(spanned);
    }

    /**
     * Show a view with a progress bar indicating a loading process.
     */
    @Override
    public void showLoading() {

    }

    /**
     * Hide a loading view.
     */
    @Override
    public void hideLoading() {

    }

    /**
     * Show a retry view in case of an error when retrieving data.
     */
    @Override
    public void showRetry() {

    }

    /**
     * Hide a retry view shown if there was an error when retrieving data.
     */
    @Override
    public void hideRetry() {

    }

    /**
     * Show an error message
     *
     * @param message A string representing an error.
     */
    @Override
    public void showError(String message) {

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                finish();
                break;
        }
    }

    /**
     * 重写图片加载接口
     *
     * @author Ruffian
     * @date 2016年1月15日
     */
    class HtmlImageGetter implements Html.ImageGetter {

        /**
         * 获取图片
         */
        @Override
        public Drawable getDrawable(String source) {
            LevelListDrawable d = new LevelListDrawable();
            Drawable empty = getResources().getDrawable(
                    R.mipmap.ic_photo_size_select_actual_black_48dp);
            d.addLevel(0, 0, empty);
            d.setBounds(0, 0, empty.getIntrinsicWidth(),
                    empty.getIntrinsicHeight());
            new LoadImage().execute(source, d);
            return d;
        }

        /**
         * 异步下载图片类
         *
         * @author Ruffian
         * @date 2016年1月15日
         */
        class LoadImage extends AsyncTask<Object, Void, Bitmap> {

            private LevelListDrawable mDrawable;

            @Override
            protected Bitmap doInBackground(Object... params) {
                String source = (String) params[0];
                mDrawable = (LevelListDrawable) params[1];
                try {
                    InputStream is = new URL(source).openStream();
                    return BitmapFactory.decodeStream(is);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            /**
             * 图片下载完成后执行
             */
            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    BitmapDrawable d = new BitmapDrawable(bitmap);
                    mDrawable.addLevel(1, 1, d);
                    /**
                     * 适配图片大小 <br/>
                     * 默认大小：bitmap.getWidth(), bitmap.getHeight()<br/>
                     * 适配屏幕：getDrawableAdapter
                     */
                    mDrawable = getDrawableAdapter(ArticleInfoActivity.this, mDrawable,
                            bitmap.getWidth(), bitmap.getHeight());

//	                 mDrawable.setBounds(-10, 0, bitmap.getWidth()+10,
//	                 bitmap.getHeight());

                    mDrawable.setLevel(1);

                    /**
                     * 图片下载完成之后重新赋值textView<br/>
                     * mtvActNewsContent:我项目中使用的textView
                     *
                     */
                    tvArticleContent.invalidate();
                    CharSequence t = tvArticleContent.getText();
                    tvArticleContent.setText(t);

                }
            }

            /**
             * 加载网络图片,适配大小
             *
             * @param context
             * @param drawable
             * @param oldWidth
             * @param oldHeight
             * @return
             * @author Ruffian
             * @date 2016年1月15日
             */
            public LevelListDrawable getDrawableAdapter(Context context,
                                                        LevelListDrawable drawable, int oldWidth, int oldHeight) {
                LevelListDrawable newDrawable = drawable;
                long newHeight = 0;// 未知数
                int newWidth = ScreenUtils.getScreenH(ArticleInfoActivity.this);// 默认屏幕宽
                newHeight = (newWidth * oldHeight) / oldWidth;
//                 LogUtils.w("oldWidth:" + oldWidth + "oldHeight:" +
//                 oldHeight);
//                 LogUtils.w("newHeight:" + newHeight + "newWidth:" +
//                 newWidth);
                newDrawable.setBounds(0, 0, newWidth, (int) newHeight);
                return newDrawable;
            }
        }

    }

    public void onShare(View v) {
        Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
    }

    public void onCollection(View v) {
        Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
    }
}
