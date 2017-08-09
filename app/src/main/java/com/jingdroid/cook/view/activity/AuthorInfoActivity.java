package com.jingdroid.cook.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.jingdroid.cook.R;
import com.jingdroid.cook.presentation.AuthorInfoPresenter;
import com.jingdroid.cook.presentation.application.MyApplication;
import com.jingdroid.cook.presentation.model.ArticleEntityModel;
import com.jingdroid.cook.presentation.model.ArticleGroupEntityModel;
import com.jingdroid.cook.presentation.model.AuthorEntityModel;
import com.jingdroid.cook.presentation.navigation.Navigator;
import com.jingdroid.cook.view.IAuthorInfoView;
import com.jingdroid.cook.view.adapter.AuthorGroupInfoAdapter;
import com.jingdroid.cook.view.widget.AppBarStateChangeListener;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AuthorInfoActivity extends AppCompatActivity implements IAuthorInfoView,AdapterView.OnItemClickListener,View.OnClickListener {

    public static final String INTENT_EXTRA_PARAMS = "author";

    private AuthorEntityModel author;

    private AppBarLayout mAppBarLayout;
    private View mUserDetail;
    private RoundedImageView ivHeadimg;
    private ImageButton mBackBtn;
    private TextView tvHeadname;
    private TextView tvHeadsign;
    private Button btnSubscribe;
    private TextView tvSubscribe;
    private ListView listAuthorGroup;
    private Toolbar toolbar;
    private AuthorGroupInfoAdapter mAdapter;
    private CollapsingToolbarLayout toolbarLayout;
    private TextView floatTitle;
    //测量值
    private float headerHeight;//顶部高度
    private float minHeaderHeight;//顶部最低高度，即Bar的高度
    private float floatTitleLeftMargin;//header标题文字左偏移量
    private float floatTitleSize;//header标题文字大小
    private float floatTitleSizeLarge;//header标题文字大小（大号）

    public static Intent getCallingIntent(Context context, AuthorEntityModel author) {
        Intent callingIntent = new Intent(context, AuthorInfoActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAMS, author);
        return callingIntent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_info);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        floatTitle = (TextView) findViewById(R.id.tv_author_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        author = getIntent().getParcelableExtra(INTENT_EXTRA_PARAMS);
        floatTitle.setText(author.getAuthor_name());
        initView();
        initMeasure();
        setDataInfo();
        AuthorInfoPresenter mPresenter = new AuthorInfoPresenter(this);
        JSONObject json = new JSONObject();
        try {
            json.put("authorid", author.getAuthor_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPresenter.getAuthorGroup(json.toString());

        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if( state == State.EXPANDED ) {
                    //展开状态
                    mUserDetail.setVisibility(View.VISIBLE);
                    floatTitle.setVisibility(View.GONE);
                }else if(state == State.COLLAPSED){
                    //折叠状态
                    mUserDetail.setVisibility(View.GONE);
                    floatTitle.setVisibility(View.VISIBLE);
                }else {
                    //中间状态
                    mUserDetail.setVisibility(View.GONE);
                    floatTitle.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                super.onOffsetChanged(appBarLayout, i);
                scroll(i);
            }
        });
    }

    private void initView() {
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        mUserDetail = findViewById(R.id.layout_user_info);
        mBackBtn = (ImageButton) findViewById(R.id.ib_back);
        ivHeadimg = (RoundedImageView) findViewById(R.id.iv_headimg);
        tvHeadname = (TextView) findViewById(R.id.tv_headname);
        tvHeadsign = (TextView) findViewById(R.id.tv_headsign);
        btnSubscribe = (Button) findViewById(R.id.btn_subscribe);
        tvSubscribe = (TextView) findViewById(R.id.tv_subscribe);
        listAuthorGroup = (ListView) findViewById(R.id.list_author_group);


        mAdapter = new AuthorGroupInfoAdapter(this);
        listAuthorGroup.setAdapter(mAdapter);
        listAuthorGroup.setOnItemClickListener(this);
        mBackBtn.setOnClickListener(this);
    }

    private void setDataInfo() {
        tvHeadname.setText(author.getAuthor_name());
        tvHeadsign.setText(author.getAuthor_sign());
        ((MyApplication)(getApplicationContext())).getImageLoader().displayImage(author.getAuthor_head(),ivHeadimg);
    }

    @Override
    public void loadAuthorGroup(List<ArticleGroupEntityModel> list) {
        mAdapter.setListData(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadArticleInfo(ArticleEntityModel article) {

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
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int groupid = ((ArticleGroupEntityModel)(parent.getItemAtPosition(position))).getArticle_group_id();
        Navigator.getInstance().navigateToArticleInfoActivity(this, groupid);
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

    private void initMeasure() {
        headerHeight = getResources().getDimension(R.dimen.header_height);
        minHeaderHeight = getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
        floatTitleLeftMargin = getResources().getDimension(R.dimen.float_title_left_margin);
        floatTitleSize = getResources().getDimension(R.dimen.float_title_size);
        floatTitleSizeLarge = getResources().getDimension(R.dimen.float_title_size_large);
    }

    private void scroll(int y) {
        //Y轴偏移量
//        float scrollY = toolbarLayout.getScrollY();
        float scrollY = -y;

        //变化率
        float headerBarOffsetY = headerHeight - minHeaderHeight;//Toolbar与header高度的差值
        float offset = 1 - Math.max((headerBarOffsetY - scrollY) / headerBarOffsetY, 0f);

        Log.d("scroll", "headerBarOffsetY:"+headerBarOffsetY + ",offset:"+offset);
        //Toolbar背景色透明度
        floatTitle.setTextColor(Color.argb((int) (offset * 255), 0, 0, 0));
        //header背景图Y轴偏移
//        headerBg.setTranslationY(scrollY / 2);

        /*** 标题文字处理 ***/
        //标题文字缩放圆心（X轴）
        floatTitle.setPivotX(floatTitle.getLeft() + floatTitle.getPaddingLeft());
        //标题文字缩放比例
        float titleScale = floatTitleSize / floatTitleSizeLarge;
        //标题文字X轴偏移
        floatTitle.setTranslationX(floatTitleLeftMargin * (1-offset));
        //标题文字Y轴偏移：（-缩放高度差 + 大文字与小文字高度差）/ 2 * 变化率 + Y轴滑动偏移
        floatTitle.setTranslationY(
                (-(floatTitle.getHeight() - minHeaderHeight) +//-缩放高度差
                        floatTitle.getHeight() * (1 - titleScale))//大文字与小文字高度差
                        / 2 * offset +
                        (headerHeight - floatTitle.getHeight() ) * (1-offset/10) - floatTitle.getHeight()/2);//Y轴滑动偏移
        //标题文字X轴缩放
        floatTitle.setScaleX(1 - offset * (1 - titleScale));
        //标题文字Y轴缩放
        floatTitle.setScaleY(1 - offset * (1 - titleScale));

        //判断标题文字的显示
//        if (scrollY > headerBarOffsetY) {
//            toolbar.setTitle(getResources().getString(R.string.toolbar_title));
//            floatTitle.setVisibility(View.GONE);
//        } else {
//            toolbar.setTitle("");
//            floatTitle.setVisibility(View.VISIBLE);
//        }
    }
}
