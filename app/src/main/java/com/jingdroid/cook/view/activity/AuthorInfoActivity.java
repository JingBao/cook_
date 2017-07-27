package com.jingdroid.cook.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AuthorInfoActivity extends AppCompatActivity implements IAuthorInfoView,AdapterView.OnItemClickListener {

    public static final String INTENT_EXTRA_PARAMS = "author";

    private AuthorEntityModel author;

    private RoundedImageView ivHeadimg;
    private TextView tvHeadname;
    private TextView tvHeadsign;
    private Button btnSubscribe;
    private TextView tvSubscribe;
    private ListView listAuthorGroup;
    private AuthorGroupInfoAdapter mAdapter;

    public static Intent getCallingIntent(Context context, AuthorEntityModel author) {
        Intent callingIntent = new Intent(context, AuthorInfoActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAMS, author);
        return callingIntent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_info);
        author = getIntent().getParcelableExtra(INTENT_EXTRA_PARAMS);
        initView();
        setDataInfo();
        AuthorInfoPresenter mPresenter = new AuthorInfoPresenter(this);
        JSONObject json = new JSONObject();
        try {
            json.put("authorid", author.getAuthor_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPresenter.getAuthorGroup(json.toString());
    }

    private void initView() {
        ivHeadimg = (RoundedImageView) findViewById(R.id.iv_headimg);
        tvHeadname = (TextView) findViewById(R.id.tv_headname);
        tvHeadsign = (TextView) findViewById(R.id.tv_headsign);
        btnSubscribe = (Button) findViewById(R.id.btn_subscribe);
        tvSubscribe = (TextView) findViewById(R.id.tv_subscribe);
        listAuthorGroup = (ListView) findViewById(R.id.list_author_group);
        mAdapter = new AuthorGroupInfoAdapter(this);
        listAuthorGroup.setAdapter(mAdapter);
        listAuthorGroup.setOnItemClickListener(this);
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
}
