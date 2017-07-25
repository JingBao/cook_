package com.jingdroid.cook.view.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jingdroid.cook.R;
import com.jingdroid.cook.presentation.AuthorPresenter;
import com.jingdroid.cook.presentation.GroupPresenter;
import com.jingdroid.cook.presentation.application.MyApplication;
import com.jingdroid.cook.presentation.model.ArticleGroupEntityModel;
import com.jingdroid.cook.presentation.model.AuthorEntityModel;
import com.jingdroid.cook.view.IAuthorView;
import com.jingdroid.cook.view.IGroupView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecommentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecommentFragment extends Fragment implements IAuthorView,IGroupView {
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
    //文章组信息
    private ImageView[] ivGroupimg = new ImageView[2];
    private TextView[] tvGroupTitle = new TextView[2];

    private AuthorPresenter mAuthorPresenter;
    private GroupPresenter mGroupPresenter;

    public RecommentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecommentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecommentFragment newInstance(String param1, String param2) {
        RecommentFragment fragment = new RecommentFragment();
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
            json.put("typeid", "0");
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
        View rootView = inflater.inflate(R.layout.fragment_recomment, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
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

        View[] group = new View[2];
        group[0] = rootView.findViewById(R.id.view_group_recomment1);
        group[1] = rootView.findViewById(R.id.view_group_recomment2);
        for (int i = 0; i < 2; i++) {
            ivGroupimg[i] = (ImageView) group[i].findViewById(R.id.iv_group_bg);
            tvGroupTitle[i] = (TextView) group[i].findViewById(R.id.tv_group_title);
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
    public void loadAuthor(List<AuthorEntityModel> authors) {
        for (int i = 0; i < authors.size(); i++) {
            tvHeadname[i].setText(authors.get(i).getAuthor_name());
            tvHeadsign[i].setText(authors.get(i).getAuthor_sign());
            if (authors.get(i).getAuthor_head() != null && !"".equals(authors.get(i).getAuthor_head())) {
                ((MyApplication)(getActivity().getApplicationContext())).getImageLoader().displayImage(authors.get(i).getAuthor_head(),ivHeadimg[i]);
            }
        }
    }

    @Override
    public void loadGroup(List<ArticleGroupEntityModel> groups) {
        for (int i = 0; i < 2; i++) {
            tvGroupTitle[i].setText(groups.get(i).getArticle_group_title());
            if (groups.get(i).getArticle_group_head() != null && !"".equals(groups.get(i).getArticle_group_head())) {
                ((MyApplication)(getActivity().getApplicationContext())).getImageLoader().loadImage(groups.get(i).getArticle_group_head(),
                        ((MyApplication)(getActivity().getApplicationContext())).getOptions(),new ImageloadOnListener(i));
            }
        }
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

    private class ImageloadOnListener extends SimpleImageLoadingListener {

        int index;
        public ImageloadOnListener(int index) {
            this.index = index;
        }
        @Override
        public void onLoadingStarted(String imageUri, View view) {
            // TODO Auto-generated method stub
            super.onLoadingStarted(imageUri, view);
        }
        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            Log.d("TAG", "getUserInfo:onLoadingComplete");
//            mUserHeader.setImageBitmap(loadedImage);
            ivGroupimg[index].setBackground(new BitmapDrawable(getResources(),loadedImage));
        }
    }
}
