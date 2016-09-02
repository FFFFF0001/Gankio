package com.mifind.gankio.ui.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.bilibili.magicasakura.widgets.TintToolbar;
import com.mifind.gankio.R;
import com.mifind.gankio.conf.Conf;
import com.mifind.gankio.event.SkinChangeEvent;
import com.mifind.gankio.ui.fragment.AndriodFragment;
import com.mifind.gankio.ui.fragment.AppFragment;
import com.mifind.gankio.ui.fragment.ExpandFragment;
import com.mifind.gankio.ui.fragment.FuLiFragment;
import com.mifind.gankio.ui.fragment.IOSFragment;
import com.mifind.gankio.ui.fragment.MainFragment;
import com.mifind.gankio.ui.fragment.RecommodFragment;
import com.mifind.gankio.ui.fragment.RestFragment;
import com.mifind.gankio.ui.fragment.WebFragment;
import com.mifind.gankio.ui.view.CardPickerDialog;
import com.mifind.gankio.utils.ThemeHelper;
import com.orhanobut.logger.Logger;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by JW.Xuan on 2016/8/24 16:17.
 * 邮箱：mifind@sina.com
 */
public class MainActivity extends BaseActivity implements OnNavigationItemSelectedListener, CardPickerDialog.ClickListener {
    /**
     * APP主页  侧滑抽屉可切换fragment
     */
    @Bind(R.id.toolbar)
    TintToolbar mToolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    ActionBarDrawerToggle mActionBarDrawerToggle;
    private PushAgent mPushAgent;

    @Override
    public void initParms(Bundle parms) {
    }

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
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        MainFragment mainFragment = new MainFragment();
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
            CardPickerDialog dialog = new CardPickerDialog();
            dialog.setClickListener(this);
            dialog.show(getSupportFragmentManager(), CardPickerDialog.TAG);
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

    @Override
    public void onConfirm(final int currentTheme) {
        EventBus.getDefault().post(new SkinChangeEvent(currentTheme));
        if (ThemeHelper.getTheme(MainActivity.this) != currentTheme) {
            ThemeHelper.setTheme(MainActivity.this, currentTheme);
            ThemeUtils.refreshUI(MainActivity.this, new ThemeUtils.ExtraRefreshable() {
                        @Override
                        public void refreshGlobal(Activity activity) {
                            if (Build.VERSION.SDK_INT >= 21) {
                                final MainActivity context = MainActivity.this;
                                ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(null, null, ThemeUtils.getThemeAttrColor(context, android.R.attr.colorPrimary));
                                setTaskDescription(taskDescription);
                                getWindow().setStatusBarColor(ThemeUtils.getColorById(context, R.color.theme_color_primary_dark));
                            }
                        }

                        @Override
                        public void refreshSpecificView(View view) {

                        }
                    }
            );
        }
    }
}
