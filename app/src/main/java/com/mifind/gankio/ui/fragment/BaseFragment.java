package com.mifind.gankio.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mifind.gankio.R;
import com.mifind.gankio.http.RequestManager;

import okhttp3.internal.http.RequestException;

/**
 * Created by Xuanjiawei on 2016/6/14.
 */
public abstract class BaseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    protected Context mContext;
    FrameLayout mFlBaseContainer;

    @Override
    public SwipeRefreshLayout onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.f_base, null);
        mFlBaseContainer = (FrameLayout) refreshLayout.findViewById(R.id.fl_base_container);
        refreshLayout.setOnRefreshListener(this);
        setBaseContainer(mFlBaseContainer);
        // 顶部刷新的样式
        refreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        return refreshLayout;
    }

    @Override
    public void onRefresh() {
        onRefreshing();
    }

    public abstract void onRefreshing();

    public abstract void setBaseContainer(ViewGroup viewGroup);

    @Override
    public void onDestroy() {
        RequestManager.getInstance().cancelAllRequest();
        super.onDestroy();
    }
}
