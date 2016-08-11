package com.mifind.gankio.ui.view.recycleview;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mifind.gankio.R;
import com.mifind.gankio.model.GankModel;
import com.mifind.gankio.ui.activity.WebViewActivity;

import java.util.List;

/**
 * Created by Xuanjiawei on 2016/8/10.
 */
public class MainRecycleAdapter extends RecyclerView.Adapter {

    private static final int FULI_ITEM = 0x0;
    private static final int OTHER_ITEM = 0x1;
    private List<GankModel> mDataList;
    private LayoutInflater mLayoutInflater;
    private Fragment mFragment;

    public MainRecycleAdapter(Fragment fragment, List<GankModel> mDataList) {
        this.mFragment = fragment;
        this.mDataList = mDataList;
        mLayoutInflater = LayoutInflater.from(mFragment.getActivity());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FULI_ITEM) {
            return new FuliItemHolder(mLayoutInflater.inflate(R.layout.f_item_fuli, parent, false));
        } else if (viewType == OTHER_ITEM) {
            return new OtherItemHolder(mLayoutInflater.inflate(R.layout.f_item_other, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GankModel gankModel = mDataList.get(position);
        if (null == gankModel)
            return;
        if (holder instanceof FuliItemHolder) {
            bindFuliItem(gankModel, (FuliItemHolder) holder);
        } else {
            bindNormalItem(gankModel, (OtherItemHolder) holder);
        }
    }

    private void bindNormalItem(final GankModel gankModel, OtherItemHolder holder) {
        holder.iTvDesc.setText(gankModel.getDesc());
        holder.iTvType.setText(gankModel.getType());
        holder.iTvWho.setText(gankModel.getWho());
        holder.iTvTime.setText(gankModel.getPublishedAt());
        holder.iCardItemFuli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mFragment.getActivity(), WebViewActivity.class);
                intent.putExtra("url", gankModel.getUrl());
                mFragment.getActivity().startActivity(intent);
            }
        });
    }

    private void bindFuliItem(GankModel gankModel, FuliItemHolder holder) {
        Glide.with(mFragment).load(gankModel.getUrl()).crossFade(300).into(holder.iIvFuli);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        GankModel gankModel = mDataList.get(position);
        if ("福利".equals(gankModel.getType())) {
            return FULI_ITEM;
        } else {
            return OTHER_ITEM;
        }
    }


    class FuliItemHolder extends RecyclerView.ViewHolder {
        private ImageView iIvFuli;

        public FuliItemHolder(View itemView) {
            super(itemView);
            iIvFuli = (ImageView) itemView.findViewById(R.id.iv_item_fuli);
        }
    }

    class OtherItemHolder extends RecyclerView.ViewHolder {
        private TextView iTvDesc;
        private TextView iTvTime;
        private TextView iTvType;
        private TextView iTvWho;
        private CardView iCardItemFuli;

        public OtherItemHolder(View itemView) {
            super(itemView);
            iCardItemFuli = (CardView) itemView.findViewById(R.id.card_item_fuli);
            iTvDesc = (TextView) itemView.findViewById(R.id.tv_item_desc);
            iTvTime = (TextView) itemView.findViewById(R.id.tv_item_time);
            iTvType = (TextView) itemView.findViewById(R.id.tv_item_type);
            iTvWho = (TextView) itemView.findViewById(R.id.tv_item_who);
        }
    }
}
