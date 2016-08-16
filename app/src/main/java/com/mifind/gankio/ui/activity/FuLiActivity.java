package com.mifind.gankio.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mifind.gankio.R;
import com.mifind.gankio.conf.Conf;
import com.mifind.gankio.http.ICallBack;
import com.mifind.gankio.http.RequestManager;
import com.mifind.gankio.model.GankModel;
import com.mifind.gankio.ui.view.recycleview.FuliRecyclerAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 李赫 on 2016/8/15.
 * recyclerview 瀑布流图片展示
 */
public class FuLiActivity extends BaseActivity {
    private FuliRecyclerAdapter fuliRecyclerAdapter;
    List<GankModel> mdatalist;

    @Bind(R.id.mToolbar)
    Toolbar mToolbar;
    @Bind(R.id.rv_fuli)
    RecyclerView fulirecyclerview;
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_fuli;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("福利");
    }

    @Override
    public void setListener() {

    }

    @Override
    public void doBusiness(Context mContext) {
        mdatalist = new ArrayList<>();
        fuliRecyclerAdapter = new FuliRecyclerAdapter(mContext,mdatalist);
        fulirecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        requestMain();
    }

    private void requestMain() {
        RequestManager.getInstance().debug("request").get("all", Conf.RequestFuli(50, 1), true, new ICallBack<List<GankModel>>() {

            @Override
            public void onSuccess(List<GankModel> result) {
                mdatalist.addAll(result);
                fulirecyclerview.setAdapter(fuliRecyclerAdapter);
            }

            @Override
            public void onFailure(String message) {
                Logger.i("onFailure :" + message);
                requestMain();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
