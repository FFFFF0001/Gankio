package com.mifind.gankio.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mifind.gankio.conf.Conf;
import com.mifind.gankio.event.SkinChangeEvent;
import com.mifind.gankio.http.ICallBack;
import com.mifind.gankio.http.RequestManager;
import com.mifind.gankio.model.GankModel;
import com.mifind.gankio.utils.ColorUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import me.xiaopan.android.widget.ToastUtils;

/**
 * Created by xuanjiawei on 2016/8/24.
 * revise by lihe
 * 全部列表页
 */
public class MainFragment extends BaseGankFragment {
    public static final String TAG = MainFragment.class.getSimpleName();

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentview = CreateView(inflater, container);
        ColorUtils.fabInitColor(floatingActionButton);
        EventBus.getDefault().register(this);
        setupFAB();
        return contentview;
    }

    private void setupFAB() {
        floatingActionButton.setVisibility(View.VISIBLE);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(getActivity())
                        .title("搜索")
                        .input("请输入关键字", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                if (!TextUtils.isEmpty(input)) {
                                    QueryGank(input.toString());
                                }
                            }
                        }).show();
            }
        });
    }

    @Override
    protected void RequestData() {
        RequestManager.getInstance().debug("request").get("all", Conf.RequestAll(pageSize, page), true, new ICallBack<List<GankModel>>() {

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

    private void QueryGank(String query) {
        progressBar.setVisibility(View.VISIBLE);
        baseGankAdapter.clearItems();
        RequestManager.getInstance().debug("request").get("all", Conf.QueryGank(query, 10, page), true, new ICallBack<List<GankModel>>() {

            @Override
            public void onSuccess(List<GankModel> result) {
                progressBar.setVisibility(View.GONE);
                if (result == null) {
                    ToastUtils.toastS(mContext, "这里没有你要找的干货哟~下拉刷新回");
                } else {
                    baseGankAdapter.updateData(result);
                }
            }

            @Override
            public void onFailure(String message) {
                progressBar.setVisibility(View.GONE);
                Logger.i("onFailure :" + message);
                ToastUtils.toastS(mContext, "这里没有你要找的干货哟~下拉刷新回");
            }
        });
    }

    @Override
    protected void loadMore() {
        page++;
        RequestData();
    }

    @Override
    public void onRefresh() {
        page = 1;
        mdatalist.clear();
        RequestData();
    }

    @Subscribe
    public void onEventMainThread(SkinChangeEvent event) {
        ColorUtils.fabUpdateColor(floatingActionButton,event.getCurrentTheme());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}