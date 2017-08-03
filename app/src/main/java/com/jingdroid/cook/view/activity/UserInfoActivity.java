package com.jingdroid.cook.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.jingdroid.cook.R;
import com.jingdroid.cook.presentation.utils.AndroidBug5497Workaround;
import com.jingdroid.cook.view.adapter.UserInfoImgAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private EditText mName;
    private GridView mGridImg;
    private UserInfoImgAdapter simpleAdapter;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, UserInfoActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        mName = (EditText) findViewById(R.id.ed_user_name);
        mGridImg = (GridView) findViewById(R.id.gv_user_img);
        mName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d("user", "onFocusChange");
            }
        });
        AndroidBug5497Workaround.assistActivity(this);
        simpleAdapter = new UserInfoImgAdapter(this);
        mGridImg.setAdapter(simpleAdapter);
        mGridImg.setOnItemClickListener(this);
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
    }
}
