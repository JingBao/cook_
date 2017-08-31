package com.jingdroid.cook.view.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ListView

import com.jingdroid.cook.R
import com.jingdroid.cook.presentation.model.AuthorEntityModel
import com.jingdroid.cook.presentation.navigation.Navigator
import com.jingdroid.cook.view.adapter.CookersAdapter

import java.util.ArrayList

class CookersActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var listCookers: ListView? = null
    private var mAdapter: CookersAdapter? = null
    private var list: MutableList<AuthorEntityModel>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //透明导航栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
        setContentView(R.layout.activity_cookers)
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        toolbar!!.title = "厨娘"
        setSupportActionBar(toolbar)

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer!!.setDrawerListener(toggle)
        toggle.syncState()
        toolbar.setNavigationIcon(R.mipmap.ic_menu_black_36dp)

        val navigationView = findViewById(R.id.nav_view) as NavigationView?
        navigationView!!.setNavigationItemSelectedListener(this)

        listCookers = findViewById(R.id.list_cookers) as ListView?
        list = ArrayList<AuthorEntityModel>()
        for (i in 0..9) {
            val model = AuthorEntityModel()
            list!!.add(model)
        }
        mAdapter = CookersAdapter(this, list)
        listCookers!!.adapter = mAdapter
        setListViewHeightBasedOnChildren(listCookers)
        listCookers!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val model = parent.getItemAtPosition(position) as AuthorEntityModel
            Navigator.getInstance().navigateToAuthorInfoActivity(this@CookersActivity, model)
        }
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout?
        drawer!!.closeDrawer(GravityCompat.START)
        return true
    }

    companion object {

        fun getCallingIntent(context: Context): Intent {
            return Intent(context, CookersActivity::class.java)
        }
    }
}
