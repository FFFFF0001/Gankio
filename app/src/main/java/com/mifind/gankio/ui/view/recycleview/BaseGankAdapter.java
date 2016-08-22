package com.mifind.gankio.ui.view.recycleview;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mifind.gankio.R;
import com.mifind.gankio.model.GankModel;
import com.mifind.gankio.ui.activity.WebViewActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 李赫 on 2016/8/18.
 */
public class BaseGankAdapter extends RecyclerView.Adapter<BaseGankAdapter.BaseGankItemHolder> {

    List<GankModel> mDatalist ;
    private LayoutInflater mLayoutInflater;
    private Fragment mfragment;

    public BaseGankAdapter(Fragment fragment, List<GankModel> mDatalist){
        mfragment = fragment;
        this.mDatalist = mDatalist;
        mLayoutInflater = LayoutInflater.from(mfragment.getActivity());
    }
    @Override
    public BaseGankItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseGankItemHolder(parent);
    }

    @Override
    public void onBindViewHolder(BaseGankItemHolder holder, int position) {
        GankModel gankModel = mDatalist.get(position);
        bindCardviewItem(gankModel,holder);
    }

    private void bindCardviewItem(final GankModel gankModel,BaseGankItemHolder holder){
        holder.ganktitle.setText(gankModel.getDesc());
        holder.gankauthor.setText(gankModel.getWho());
        holder.gankdate.setText(gankModel.getPublishedAt());
        holder.cardViewitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mfragment.getActivity(), WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("model", gankModel);
                intent.putExtras(bundle);
                mfragment.getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatalist.size();
    }

    class BaseGankItemHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_gank_date)
        TextView gankdate;
        @Bind(R.id.tv_gank_author)
        TextView gankauthor;
        @Bind(R.id.tv_gank_title)
        TextView ganktitle;
        @Bind(R.id.cardview_base_gank)
        CardView cardViewitem;

        public BaseGankItemHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.f_item_base_gank, parent, false));
            ButterKnife.bind(this,itemView);
        }
    }
}
