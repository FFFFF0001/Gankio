package com.mifind.gankio.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mifind.gankio.GankApp;
import com.mifind.gankio.R;
import com.mifind.gankio.conf.Conf;
import com.mifind.gankio.http.ICallBack;
import com.mifind.gankio.http.RequestManager;
import com.mifind.gankio.model.GankModel;
import com.mifind.gankio.ui.view.recycleview.MainRecycleAdapter;
import com.mifind.gankio.utils.PreUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Xuanjiawei on 2016/8/10.
 */
public class MainFragment extends BaseFragment {

    @Bind(R.id.rv_main)
    RecyclerView mRvMainLayout;
    //数据
    private List<GankModel> mDataList;
    private Context mContext;
    private View mView;
    private MainRecycleAdapter mMainRecycleAdapter;
    private SwipeRefreshLayout mBaseView;
    private static int MAIN_INDEX = 1;

    public MainFragment(Context context, List<GankModel> mDataList) {
        MAIN_INDEX = PreUtils.getInt(GankApp.getContext(), "MAIN_INDEX", 1);
        this.mContext = context;
        this.mDataList = mDataList;
    }

    @Override
    public SwipeRefreshLayout onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        mView = view;
        ButterKnife.bind(this, mView);
        mBaseView = super.onCreateView(inflater, container, savedInstanceState);
        mMainRecycleAdapter = new MainRecycleAdapter(this, mDataList);
        mRvMainLayout.setAdapter(mMainRecycleAdapter);
        mRvMainLayout.setLayoutManager(new LinearLayoutManager(mContext));
        return mBaseView;
    }

    @Override
    public void onRefreshing() {
        loadData(MAIN_INDEX);
    }

    @Override
    public void setBaseContainer(ViewGroup superView) {
        superView.addView(mView);
    }

    private void loadData(int i) {
        RequestManager.getInstance().debug("request").get("all", Conf.RequestAll(50, i), true, new ICallBack<List<GankModel>>() {

            @Override
            public void onSuccess(List<GankModel> result) {
                Logger.i("onSuccess 进行刷新接口操作成功");
                mDataList = null;
                mDataList = result;
                mMainRecycleAdapter.updateData(mDataList);
                mBaseView.setRefreshing(false);
                MAIN_INDEX++;
            }

            @Override
            public void onFailure(String message) {
                Logger.i("onFailure :" + message);
                mBaseView.setRefreshing(false);
            }
        });
    }

    @Override
    public void onDestroy() {
        PreUtils.putInt(GankApp.getContext(), "MAIN_INDEX", MAIN_INDEX);
        super.onDestroy();
    }
}
