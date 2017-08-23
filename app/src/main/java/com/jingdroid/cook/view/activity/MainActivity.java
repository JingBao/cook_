package com.jingdroid.cook.view.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jingdroid.cook.R;
import com.jingdroid.cook.presentation.navigation.Navigator;
import com.jingdroid.cook.view.adapter.SimpleFragmentPagerAdapter;
import com.jingdroid.cook.view.fragment.DessertFragment;
import com.jingdroid.cook.view.fragment.FlavorFragment;
import com.jingdroid.cook.view.fragment.PersonalFragment;
import com.jingdroid.cook.view.fragment.RecommentFragment;
import com.jingdroid.cook.view.fragment.SeaFoodFragment;
import com.jingdroid.cook.view.fragment.SearchRevealFragment;
import com.jingdroid.cook.view.widget.AppBarStateChangeListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        RecommentFragment.OnFragmentInteractionListener,PersonalFragment.OnFragmentInteractionListener,
        DessertFragment.OnFragmentInteractionListener,SeaFoodFragment.OnFragmentInteractionListener,FlavorFragment.OnFragmentInteractionListener {

    private static final int REQEUST_CODE = 0x11;

    private SimpleFragmentPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView mSearchView;
    private RollPagerView mRollViewPager;
    private AppBarLayout mAppBarLayout;
    private View headerLayout;
    private TextView tvHeaderName;
    private TextView tvHeaderSign;

    ActionBarDrawerToggle toggle;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("首页");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mSearchView = (ImageView) findViewById(R.id.iv_toolbar_search);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.mipmap.ic_menu_white_36dp);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerLayout = navigationView.getHeaderView(0);
        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.getInstance().navigateToUserInfoActivity(MainActivity.this,tvHeaderName.getText().toString(),
                        tvHeaderSign.getText().toString(),  REQEUST_CODE);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        tvHeaderName = (TextView) headerLayout.findViewById(R.id.tv_header_name);
        tvHeaderSign = (TextView) headerLayout.findViewById(R.id.tv_header_sign);

        List<Fragment> listFragmentsa = new ArrayList<Fragment>();
        Fragment fragmentRecomment = new RecommentFragment();
        Fragment fragmentPersnal = new PersonalFragment();
        Fragment fragmentDessert = new DessertFragment();
        Fragment fragmentSeafood = new SeaFoodFragment();
        Fragment fragmentFlavor = new FlavorFragment();

        listFragmentsa.add(fragmentRecomment);
        listFragmentsa.add(fragmentPersnal);
        listFragmentsa.add(fragmentDessert);
        listFragmentsa.add(fragmentSeafood);
        listFragmentsa.add(fragmentFlavor);

        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), listFragmentsa);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager.setOffscreenPageLimit(4);
        mRollViewPager = (RollPagerView) findViewById(R.id.roll_view_pager);

        //设置播放时间间隔
        mRollViewPager.setPlayDelay(2000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollViewPager.setAdapter(new TestLoopAdapter(mRollViewPager));

        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        mRollViewPager.setHintView(new ColorPointHintView(this, Color.WHITE,Color.BLACK));
        mRollViewPager.setVisibility(View.GONE);
        mRollViewPager.pause();

        final SearchRevealFragment mfragment = new SearchRevealFragment();
        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mfragment.isVisible()) {
                    mfragment.onBackPressed();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("searchImg_x", mSearchView.getLeft());
                    bundle.putInt("searchImg_t", mSearchView.getTop());
                    bundle.putInt("searchImg_w", mSearchView.getWidth());
                    bundle.putInt("searchImg_h", mSearchView.getHeight());
                    mfragment.setArguments(bundle);

                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(android.R.id.content, mfragment, "fragment_my")
                            .addToBackStack("fragment:reveal")
                            .commit();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        SearchRevealFragment fragment = (SearchRevealFragment) getSupportFragmentManager().findFragmentByTag("fragment_my");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START) || fragment != null) {
            drawer.closeDrawer(GravityCompat.START);
            fragment.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, new SearchRevealFragment(), "fragment_my")
                    .addToBackStack("fragment:reveal")
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action

        } else if (id == R.id.nav_gallery) {
            Navigator.getInstance().navigateToCookersActivity(this);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about) {
            Navigator.getInstance().navigateToAboutActivity(this);
        } else if (id == R.id.nav_send) {
            showExitDialog("", "确认要清除所有缓存数据吗", new DialogClickListener(DIALOG_PARAMS_DELCACHE));
        } else if (id == R.id.nav_exit) {
            showExitDialog("", "确认要退出登录吗", new DialogClickListener(DIALOG_PARAMS_EXIT));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class TestLoopAdapter extends LoopPagerAdapter {

        public TestLoopAdapter(RollPagerView viewPager) {
            super(viewPager);
        }
        private int[] imgs = {
                R.mipmap.bar_bg,
                R.mipmap.img2,
                R.mipmap.img3,
        };


        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }


        @Override
        public int getRealCount() {
            return imgs.length;
        }
    }

    public void updateStatus() {
        mRollViewPager.setVisibility(View.VISIBLE);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if( state == State.EXPANDED ) {
                    //展开状态
                    mRollViewPager.setVisibility(View.VISIBLE);
                    setMeizuStatusBarDarkIcon(MainActivity.this, false);
                    toggle.syncState();
                    toolbar.setNavigationIcon(R.mipmap.ic_menu_white_36dp);
                    mSearchView.setImageResource(R.mipmap.ic_search_white_36dp);
                    getSupportActionBar().setDisplayShowTitleEnabled(false);
                }else if(state == State.COLLAPSED){
                    //折叠状态
                    mRollViewPager.setVisibility(View.GONE);
                    setMeizuStatusBarDarkIcon(MainActivity.this, true);
                    toggle.syncState();
                    toolbar.setNavigationIcon(R.mipmap.ic_menu_black_36dp);
                    mSearchView.setImageResource(R.mipmap.ic_search_black_36dp);
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                }else {
                    //中间状态
                    mRollViewPager.setVisibility(View.VISIBLE);
                    setMeizuStatusBarDarkIcon(MainActivity.this, false);
                    toggle.syncState();
                    toolbar.setNavigationIcon(R.mipmap.ic_menu_white_36dp);
                    mSearchView.setImageResource(R.mipmap.ic_search_white_36dp);
                    getSupportActionBar().setDisplayShowTitleEnabled(false);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQEUST_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            int resourceId = bundle.getInt("resourceId");
            String name = bundle.getString("name");
            String sign = bundle.getString("sign");
            if (resourceId != 0) {
                headerLayout.setBackgroundResource(resourceId);
            }
            tvHeaderName.setText(name);
            tvHeaderSign.setText(sign);
        }
    }

    private static final int DIALOG_PARAMS_EXIT = 0x92;
    private static final int DIALOG_PARAMS_DELCACHE = 0x93;
    public class DialogClickListener implements DialogInterface.OnClickListener {

        int index;
        public DialogClickListener(int index) {
            this.index = index;
        }
        /**
         * This method will be invoked when a button in the dialog is clicked.
         *
         * @param dialog The dialog that received the click.
         * @param which  The button that was clicked (e.g.
         *               {@link DialogInterface#BUTTON1}) or the position
         */
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (this.index) {
                case DIALOG_PARAMS_EXIT:
                    finish();
                    break;
                case DIALOG_PARAMS_DELCACHE:
                    dialog.cancel();
                    break;
            }
        }
    }
}
