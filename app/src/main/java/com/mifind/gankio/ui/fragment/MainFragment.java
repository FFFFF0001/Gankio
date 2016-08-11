package com.mifind.gankio.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mifind.gankio.R;
import com.mifind.gankio.model.GankModel;
import com.mifind.gankio.ui.view.recycleview.MainRecycleAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Xuanjiawei on 2016/8/10.
 */
public class MainFragment extends BaseFragment {

    @Bind(R.id.rv_main)
    RecyclerView mRvMainLayout;

    private List<GankModel> mDataList;
    private Context mContext;
    public MainFragment(Context context , List<GankModel> mDataList) {
        this.mContext = context;
        this.mDataList = mDataList;
    }

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        ButterKnife.bind(this, view);
        mRvMainLayout.setAdapter(new MainRecycleAdapter(this, mDataList));
        mRvMainLayout.setLayoutManager(new LinearLayoutManager(mContext));
        return view;
    }
}
