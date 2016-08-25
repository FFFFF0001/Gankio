package com.mifind.gankio.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mifind.gankio.R;
import com.mifind.gankio.conf.Conf;
import com.mifind.gankio.http.ICallBack;
import com.mifind.gankio.http.RequestManager;
import com.mifind.gankio.model.GankModel;
import com.mifind.gankio.ui.fragment.AndriodFragment;
import com.mifind.gankio.ui.fragment.AppFragment;
import com.mifind.gankio.ui.fragment.ExpandFragment;
import com.mifind.gankio.ui.fragment.FuLiFragment;
import com.mifind.gankio.ui.fragment.IOSFragment;
import com.mifind.gankio.ui.fragment.MainFragment;
import com.mifind.gankio.ui.fragment.RecommodFragment;
import com.mifind.gankio.ui.fragment.RestFragment;
import com.mifind.gankio.ui.fragment.WebFragment;
import com.orhanobut.logger.Logger;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.xiaopan.android.widget.ToastUtils;

/**
 * Created by JW.Xuan on 2016/8/24 16:17.
 * 邮箱：mifind@sina.com
 */
public class MainActivity extends BaseActivity implements OnNavigationItemSelectedListener {
    /**
     * APP主页  侧滑抽屉可切换fragment
     */
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    ActionBarDrawerToggle mActionBarDrawerToggle;
    private PushAgent mPushAgent;

    @Override
    public void initParms(Bundle parms) {}

    @Override
    protected void onResume() {
        super.onResume();
        mPushAgent = PushAgent.getInstance(mContext);
        mPushAgent.enable();
        String device_token = UmengRegistrar.getRegistrationId(mContext);
        Logger.e("token=" + device_token + ", enable=" + mPushAgent.isEnabled() + ", register=" + mPushAgent.isRegistered());
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void setListener() {
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void doBusiness(final Context mContext) {
        //获取首页列表
        requestMain();
    }

    private void requestMain() {
        RequestManager.getInstance().debug("request").get("all", Conf.RequestAll(10, 1), true, new ICallBack<List<GankModel>>() {

            @Override
            public void onSuccess(List<GankModel> result) {
                setDefaultFragment(result);
            }

            @Override
            public void onFailure(String message) {
                Logger.i("onFailure :" + message);
                ToastUtils.toastS(mContext, message);
            }
        });
    }


    private void setDefaultFragment(List<GankModel> result) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        MainFragment mainFragment = new MainFragment(mContext, result);
        transaction.replace(R.id.fl_main_layout, mainFragment);
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            return true;
        }
        if (id == R.id.action_theme) {

            return true;
        }
        if (id == R.id.action_about_app) {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("url", Conf.RequestAboutApp());
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_about_me) {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("url", Conf.RequestBlog());
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_all) {
            if (null == getFragmentManager().findFragmentByTag(MainFragment.TAG)) {
                replaceFragment(R.id.fl_main_layout, MainFragment.newInstance(), MainFragment.TAG);
                setTitle("干货");
            }
        } else if (id == R.id.nav_gift) {
            if (null == getFragmentManager().findFragmentByTag(FuLiFragment.TAG)) {
                replaceFragment(R.id.fl_main_layout, FuLiFragment.newInstance(), FuLiFragment.TAG);
                setTitle("福利");
            }
        } else if (id == R.id.nav_android) {
            if (null == getFragmentManager().findFragmentByTag(AndriodFragment.TAG)) {
                replaceFragment(R.id.fl_main_layout, AndriodFragment.newInstance(), AndriodFragment.TAG);
                setTitle("Andriod");
            }
        } else if (id == R.id.nav_ios) {
            if (null == getFragmentManager().findFragmentByTag(IOSFragment.TAG)) {
                replaceFragment(R.id.fl_main_layout, IOSFragment.newInstance(), IOSFragment.TAG);
                setTitle("IOS");
            }
        } else if (id == R.id.nav_rest) {
            if (null == getFragmentManager().findFragmentByTag(RestFragment.TAG)) {
                replaceFragment(R.id.fl_main_layout, RestFragment.newInstance(), RestFragment.TAG);
                setTitle("休息视频");
            }
        } else if (id == R.id.nav_html) {
            if (null == getFragmentManager().findFragmentByTag(WebFragment.TAG)) {
                replaceFragment(R.id.fl_main_layout, WebFragment.newInstance(), WebFragment.TAG);
                setTitle("前端");
            }
        } else if (id == R.id.nav_expand) {
            if (null == getFragmentManager().findFragmentByTag(ExpandFragment.TAG)) {
                replaceFragment(R.id.fl_main_layout, ExpandFragment.newInstance(), ExpandFragment.TAG);
                setTitle("拓展资源");
            }
        } else if (id == R.id.nav_app) {
            if (null == getFragmentManager().findFragmentByTag(AppFragment.TAG)) {
                replaceFragment(R.id.fl_main_layout, AppFragment.newInstance(), AppFragment.TAG);
                setTitle("App");
            }
        } else if (id == R.id.nav_recommend) {
            if (null == getFragmentManager().findFragmentByTag(RecommodFragment.TAG)) {
                replaceFragment(R.id.fl_main_layout, RecommodFragment.newInstance(), RecommodFragment.TAG);
                setTitle("瞎推荐");
            }
        }
        item.setChecked(true);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPushAgent.disable();
    }
}
