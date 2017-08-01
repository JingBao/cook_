package com.jingdroid.cook.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.jingdroid.cook.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfoActivity extends AppCompatActivity {

    private GridView mGridImg;
    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>> data;
    private int[] icon = {R.mipmap.drawer_head_bg, R.mipmap.drawer_head_bg,
            R.mipmap.drawer_head_bg, R.mipmap.drawer_head_bg,
            R.mipmap.drawer_head_bg, R.mipmap.drawer_head_bg};

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, UserInfoActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("个人信息");
        setSupportActionBar(toolbar);

        mGridImg = (GridView) findViewById(R.id.gv_user_img);

        data = new ArrayList<>();
        getData();
        //新建适配器
        String [] from ={"image"};
        int [] to = {R.id.image};
        simpleAdapter = new SimpleAdapter(this,data, R.layout.item_user_info_grid,from,to);

        mGridImg.setAdapter(simpleAdapter);
    }

    public List<Map<String, Object>> getData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            data.add(map);
        }

        return data;
    }
}
