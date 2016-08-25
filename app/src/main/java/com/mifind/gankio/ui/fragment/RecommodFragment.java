package com.mifind.gankio.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mifind.gankio.conf.Conf;
import com.mifind.gankio.http.ICallBack;
import com.mifind.gankio.http.RequestManager;
import com.mifind.gankio.model.GankModel;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by JW.Xuan on 2016/8/24 16:22.
 * 邮箱：mifind@sina.com
 */
public class RecommodFragment extends BaseGankFragment {
    public static final String TAG = RecommodFragment.class.getSimpleName();
    public static RecommodFragment newInstance() {
        return new RecommodFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentview = CreateView(inflater, container);
        return contentview;
    }

    @Override
    protected void RequestData() {
        RequestManager.getInstance().debug("request").get("all", Conf.RequestRecommond(pageSize, page), true, new ICallBack<List<GankModel>>() {

            @Override
            public void onSuccess(List<GankModel> result) {
                Logger.i("onSuccess 进行刷新接口操作成功");
                mdatalist.addAll(result);
                baseGankAdapter.notifyDataSetChanged();
                isLoadingMore = false;
                if (mswipeRefreshLayout != null) {
                    mswipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(String message) {
                Logger.i("onFailure :" + message);
                isLoadingMore = false;
                if (mswipeRefreshLayout != null) {
                    mswipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    protected void loadMore() {
        page ++;
        RequestData();
    }

    @Override
    public void onRefresh() {
        page = 1;
        mdatalist.clear();
        RequestData();
    }
}
