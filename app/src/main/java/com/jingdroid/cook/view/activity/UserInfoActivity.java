package com.jingdroid.cook.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.jingdroid.cook.R;
import com.jingdroid.cook.presentation.utils.AndroidBug5497Workaround;
import com.jingdroid.cook.view.adapter.UserInfoImgAdapter;
import com.jingdroid.cook.view.widget.SquareLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfoActivity extends BaseActivity implements AdapterView.OnItemClickListener,View.OnClickListener {

    private static final String INTENT_EXTRAS_PARAMS_NAME = "name";
    private static final String INTENT_EXTRAS_PARAMS_SIGN = "sign";
    private ImageView ivBack;
    private ImageView ivToolbarSubmit;
    private EditText edUserSign;
    private EditText mName;
    private GridView mGridImg;
    private UserInfoImgAdapter simpleAdapter;

    private Integer resourceId;

    public static Intent getCallingIntent(Context context, String name, String sign) {
        Intent callingIntent = new Intent(context, UserInfoActivity.class);
        callingIntent.putExtra(INTENT_EXTRAS_PARAMS_NAME, name);
        callingIntent.putExtra(INTENT_EXTRAS_PARAMS_SIGN, sign);
        return callingIntent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        setMeizuStatusBarDarkIcon(this, true);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivToolbarSubmit = (ImageView) findViewById(R.id.iv_toolbar_submit);
        edUserSign = (EditText) findViewById(R.id.ed_user_sign);
        mName = (EditText) findViewById(R.id.ed_user_name);
        mGridImg = (GridView) findViewById(R.id.gv_user_img);
        simpleAdapter = new UserInfoImgAdapter(this);
        mGridImg.setAdapter(simpleAdapter);
        mGridImg.setOnItemClickListener(this);
        ivBack.setOnClickListener(this);
        ivToolbarSubmit.setOnClickListener(this);
        mName.setText(intent.getStringExtra(INTENT_EXTRAS_PARAMS_NAME));
        edUserSign.setText(intent.getStringExtra(INTENT_EXTRAS_PARAMS_SIGN));
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
        Toast.makeText(this, "选择:"+position ,Toast.LENGTH_LONG).show();
        resourceId = (Integer) parent.getItemAtPosition(position);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_toolbar_submit:
                String name = mName.getText().toString();
                String sign = edUserSign.getText().toString();
                // 网络交互

                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("sign", sign);
                bundle.putInt("resourceId", resourceId);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

//    public static void setGridViewHeightBasedOnChildren(GridView gridView) {
//        // 获取GridView对应的Adapter
//        ListAdapter listAdapter = gridView.getAdapter();
//        if (listAdapter == null) {
//            return;
//        }
//        int rows;
//        int columns=0;
//        int horizontalBorderHeight=0;
//        Class<?> clazz=gridView.getClass();
//        try {
//            //利用反射，取得每行显示的个数
//            Field column=clazz.getDeclaredField("mRequestedNumColumns");
//            column.setAccessible(true);
//            columns=(Integer)column.get(gridView);
//            //利用反射，取得横向分割线高度
//            Field horizontalSpacing=clazz.getDeclaredField("mRequestedHorizontalSpacing");
//            horizontalSpacing.setAccessible(true);
//            horizontalBorderHeight=(Integer)horizontalSpacing.get(gridView);
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//        //判断数据总数除以每行个数是否整除。不能整除代表有多余，需要加一行
//        if(listAdapter.getCount()%columns>0){
//            rows=listAdapter.getCount()/columns+1;
//        }else {
//            rows=listAdapter.getCount()/columns;
//        }
//        int totalHeight = 0;
//        for (int i = 0; i < rows; i++) { //只计算每项高度*行数
//            View listItem = listAdapter.getView(i, null, gridView);
//            listItem.measure(0, 0); // 计算子项View 的宽高
//            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
//        }
//        ViewGroup.LayoutParams params = gridView.getLayoutParams();
//        params.height = totalHeight+horizontalBorderHeight*(rows-1);//最后加上分割线总高度
//        gridView.setLayoutParams(params);
//    }
//
//    public static void setListViewHeightBasedOnChildren(GridView listView) {
//        // 获取listview的adapter
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null) {
//            return;
//        }
//        // 固定列宽，有多少列
//        int col = 3;// listView.getNumColumns();
//        int totalHeight = 0;
//        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
//        // listAdapter.getCount()小于等于8时计算两次高度相加
//        for (int i = 0; i < listAdapter.getCount(); i += col) {
//            // 获取listview的每一个item
//            SquareLayout listItem = (SquareLayout) listAdapter.getView(i, null, listView);
////            listItem.measure(0, 0);
//            // 获取item的高度和
////            totalHeight += listItem.getMeasuredHeight();
//            totalHeight += listItem.getMeasuredWidth();
//        }
//
//        // 获取listview的布局参数
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        // 设置高度
//        params.height = totalHeight;
//        // 设置margin
//        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 20, 10, 20);
//        // 设置参数b
//        listView.setLayoutParams(params);
//    }
}
