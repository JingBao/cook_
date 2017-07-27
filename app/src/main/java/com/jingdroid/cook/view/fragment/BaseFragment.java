package com.jingdroid.cook.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jingdroid.cook.R;
import com.jingdroid.cook.presentation.model.AuthorEntityModel;
import com.jingdroid.cook.view.adapter.GroupInfoAdapter;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by Jing on 2017/7/25.
 */

public class BaseFragment extends Fragment implements View.OnClickListener{

    //编辑者信息
    protected RoundedImageView[] ivHeadimg = new RoundedImageView[3];
    protected TextView[] tvHeadname = new TextView[3];
    protected TextView[] tvHeadsign = new TextView[3];
    protected TextView[] ivAuthorArticle = new TextView[3];
    protected TextView[] ivAuthorSubscribe = new TextView[3];
    protected List<AuthorEntityModel> mAuthors;

    protected View mLoadingView;
    protected View mContentView;
    protected ListView mListView;
    protected GroupInfoAdapter mGroupInfoAdapter;
    protected boolean dataloadAuthorComplete;
    protected boolean dataloadGroupComplete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personal, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        mLoadingView = rootView.findViewById(R.id.viewloading);
        mContentView = rootView.findViewById(R.id.layout_comment_content);
        mListView = (ListView) rootView.findViewById(R.id.list_group);
        mGroupInfoAdapter = new GroupInfoAdapter(getActivity());
        mListView.setAdapter(mGroupInfoAdapter);

        View[] authors = new View[3];
        authors[0] = rootView.findViewById(R.id.layout_author1);
        authors[1] = rootView.findViewById(R.id.layout_author2);
        authors[2] = rootView.findViewById(R.id.layout_author3);
        for (int i = 0; i < 3; i++) {
            authors[i].setOnClickListener(this);
        }
        View[] ar = new View[3];
        ar[0] = rootView.findViewById(R.id.view_author_recomment1);
        ar[1] = rootView.findViewById(R.id.view_author_recomment2);
        ar[2] = rootView.findViewById(R.id.view_author_recomment3);

        for (int i = 0; i < 3; i++) {
            ivHeadimg[i] = (RoundedImageView) ar[i].findViewById(R.id.iv_headimg);
            tvHeadname[i] = (TextView) ar[i].findViewById(R.id.tv_headname);
            tvHeadsign[i] = (TextView) ar[i].findViewById(R.id.tv_headsign);
            ivAuthorArticle[i] = (TextView) ar[i].findViewById(R.id.iv_author_article);
            ivAuthorSubscribe[i] = (TextView) ar[i].findViewById(R.id.iv_author_subscribe);
        }
    }

    @Override
    public void onClick(View v) {

    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}