package com.jingdroid.cook.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jingdroid.cook.R;
import com.jingdroid.cook.presentation.AuthorPresenter;
import com.jingdroid.cook.presentation.GroupPresenter;
import com.jingdroid.cook.presentation.application.MyApplication;
import com.jingdroid.cook.presentation.model.ArticleGroupEntityModel;
import com.jingdroid.cook.presentation.model.AuthorEntityModel;
import com.jingdroid.cook.view.IAuthorView;
import com.jingdroid.cook.view.IGroupView;
import com.jingdroid.cook.view.adapter.GroupInfoAdapter;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SeaFoodFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SeaFoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeaFoodFragment extends Fragment  implements IAuthorView,IGroupView,View.OnClickListener {

    private static final int MSG_DATA_LOAD_COMPLETED_SEAFOOD = 0x13;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    //编辑者信息
    private RoundedImageView[] ivHeadimg = new RoundedImageView[3];
    private TextView[] tvHeadname = new TextView[3];
    private TextView[] tvHeadsign = new TextView[3];
    private TextView[] ivAuthorArticle = new TextView[3];
    private TextView[] ivAuthorSubscribe = new TextView[3];

    private View mLoadingView;
    private View mContentView;
    private ListView mListView;
    private GroupInfoAdapter mGroupInfoAdapter;

    private AuthorPresenter mAuthorPresenter;
    private GroupPresenter mGroupPresenter;

    private boolean dataloadAuthorComplete;
    private boolean dataloadGroupComplete;

    private Handler mHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_DATA_LOAD_COMPLETED_SEAFOOD:
                    if (dataloadAuthorComplete && dataloadGroupComplete) {
                        mContentView.setVisibility(View.VISIBLE);
                        mLoadingView.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };
    public SeaFoodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SeaFoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SeaFoodFragment newInstance(String param1, String param2) {
        SeaFoodFragment fragment = new SeaFoodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mAuthorPresenter = new AuthorPresenter(this);
        mGroupPresenter = new GroupPresenter(this);
        JSONObject json = new JSONObject();
        try {
            json.put("typeid", "3");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAuthorPresenter.getAuthor(json.toString());
        mGroupPresenter.getGroup(json.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sea_food, container, false);
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
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void loadAuthor(List<AuthorEntityModel> authors) {
        for (int i = 0; i < authors.size(); i++) {
            if (i == 3)
                break;
            tvHeadname[i].setText(authors.get(i).getAuthor_name());
            tvHeadsign[i].setText(authors.get(i).getAuthor_sign());
            if (authors.get(i).getAuthor_head() != null && !"".equals(authors.get(i).getAuthor_head())) {
                ((MyApplication)(getActivity().getApplicationContext())).getImageLoader().displayImage(authors.get(i).getAuthor_head(),ivHeadimg[i]);
            }
        }
        dataloadAuthorComplete = true;
        mHander.sendEmptyMessage(MSG_DATA_LOAD_COMPLETED_SEAFOOD);
    }

    @Override
    public void loadGroup(List<ArticleGroupEntityModel> groups) {
        mGroupInfoAdapter.setListData(groups);
        mGroupInfoAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(mListView);
        dataloadGroupComplete = true;
        mHander.sendEmptyMessage(MSG_DATA_LOAD_COMPLETED_SEAFOOD);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
