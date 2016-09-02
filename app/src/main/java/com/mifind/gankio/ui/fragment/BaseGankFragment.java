package com.mifind.gankio.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilibili.magicasakura.widgets.TintProgressBar;
import com.mifind.gankio.R;
import com.mifind.gankio.model.GankModel;
import com.mifind.gankio.ui.view.recycleview.BaseRecycleAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 李赫 on 2016/8/18.
 * IOS andriod 前端 基类fragment 封装recyclerview + swifpeRefreshlayout 列表滑到最底部自动加载的监听
 */
public abstract class BaseGankFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout mswipeRefreshLayout;
    @Bind(R.id.recycler_view)
    RecyclerView mrecyclerView;
    @Bind(R.id.fab_search)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.progressBar)
    TintProgressBar progressBar;

    protected List<GankModel> mdatalist = new ArrayList<>();
    protected BaseRecycleAdapter baseGankAdapter;
    protected boolean isLoadingMore = false;
    protected Context mContext;
    protected int pageSize = 10;
    protected int page = 1;

    protected LinearLayoutManager mLayoutManager;


    public View CreateView(LayoutInflater inflater, ViewGroup container) {
        mContext = getActivity();
        View view  = inflater.inflate(R.layout.fragement_base_gank,container,false);
        ButterKnife.bind(this,view);
        mswipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        mswipeRefreshLayout.setOnRefreshListener(this);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mrecyclerView.setLayoutManager(mLayoutManager);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.addOnScrollListener(mScrollListener);
        baseGankAdapter = new BaseRecycleAdapter(this,mdatalist);
        mrecyclerView.setAdapter(baseGankAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mswipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mswipeRefreshLayout.setRefreshing(true);
                RequestData();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    protected abstract void RequestData();

    protected abstract void loadMore();

    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            //正在滚动时回调，回调2-3次，手指没抛则回调2次。scrollState = 2的这次不回调
            //回调顺序如下
            //第1次：scrollState = SCROLL_STATE_TOUCH_SCROLL(1) 正在滚动
            //第2次：scrollState = SCROLL_STATE_FLING(2) 手指做了抛的动作（手指离开屏幕前，用力滑了一下）
            //第3次：scrollState = SCROLL_STATE_IDLE(0) 停止滚动
            boolean reachBottom = mLayoutManager.findLastCompletelyVisibleItemPosition()
                    >= mLayoutManager.getItemCount() - 1;
            if(newState == RecyclerView.SCROLL_STATE_IDLE && !isLoadingMore && reachBottom) {
                isLoadingMore = true;
                loadMore();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //滚动时一直回调，直到停止滚动时才停止回调。单击时回调一次
        }
    };
}
