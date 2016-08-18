package com.mifind.gankio.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;

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
import me.xiaopan.android.widget.ToastUtils;

/**
 * Created by 李赫 on 2016/8/15.
 * recyclerview 瀑布流图片展示
 */
public class FuLiActivity extends CommonActivity {
    private FuliRecyclerAdapter fuliRecyclerAdapter;
    List<GankModel> mdatalist;
    @Bind(R.id.rv_fuli)
    RecyclerView fulirecyclerview;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    @Override
    protected void initCommonView(View commonView) {
        ButterKnife.bind(FuLiActivity.this);
        mSwipeRefresh.setColorScheme(new int[]{R.color.colorPrimary, R.color.colorRedPrimary, R.color.colorOrangePrimary, R.color.colorGreenPrimary});
    }

    @Override
    protected String bindCommonTitle() {
        return "福利";
    }

    @Override
    protected int bindCommonLayout() {
        return R.layout.activity_fuli;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void setListener() {
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ToastUtils.toastS(mContext, "sshjshshshs");
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        mSwipeRefresh.setRefreshing(true);
        mdatalist = new ArrayList<>();
        fuliRecyclerAdapter = new FuliRecyclerAdapter(mContext, mdatalist);
        fulirecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        requestFuli();
    }


    private void requestFuli() {
        RequestManager.getInstance().debug("request").get("all", Conf.RequestFuli(20, 1), true, new ICallBack<List<GankModel>>() {

            @Override
            public void onSuccess(List<GankModel> result) {
                mdatalist.addAll(result);
                fulirecyclerview.setAdapter(fuliRecyclerAdapter);
                mSwipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(String message) {
                Logger.i("onFailure :" + message);
                ToastUtils.toastS(mContext, message);
                mSwipeRefresh.setRefreshing(false);
            }
        });
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
