package com.mifind.gankio.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.mifind.gankio.R;

/**
 * Created by JW.Xuan on 2016/8/18 10:06.
 * 邮箱：mifind@sina.com
 */
public abstract class CommonActivity extends BaseActivity {

    Toolbar mToolbar;
    FrameLayout mFlCommonContainerLayout;

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_common;
    }

    @Override
    public void initView(View view) {
        mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        mFlCommonContainerLayout = (FrameLayout) findViewById(R.id.fl_common_layout);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View containerLayout = LayoutInflater.from(this).inflate(bindCommonLayout(), null);
        if (containerLayout != null)
            mFlCommonContainerLayout.addView(containerLayout);
        initCommonView(containerLayout);
        setTitle(bindCommonTitle());
    }

    protected abstract void initCommonView(View commonView);

    protected abstract String bindCommonTitle();

    protected abstract int bindCommonLayout();

}
