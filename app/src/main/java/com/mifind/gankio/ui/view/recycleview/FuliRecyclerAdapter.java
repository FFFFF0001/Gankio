package com.mifind.gankio.ui.view.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mifind.gankio.R;
import com.mifind.gankio.model.GankModel;

import java.util.List;

/**
 * Created by 李赫 on 2016/8/15.
 */
public class FuliRecyclerAdapter extends RecyclerView.Adapter<FuliRecyclerAdapter.FuliItemHolder> {

    List<GankModel> mDatalist;
    Context mContext;

    public FuliRecyclerAdapter(Context context, List<GankModel> mDatalist){
        this.mContext = context;
        this.mDatalist = mDatalist;
    }
    @Override
    public FuliItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.f_item_fuli,parent,false);
        FuliItemHolder holder = new FuliItemHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(FuliItemHolder holder, int position) {
        GankModel gankModel = mDatalist.get(position);
        if (null == gankModel)
            return;
        Glide.with(mContext).load(gankModel.getUrl()).crossFade(300).into(holder.iv_fuli);
    }


    @Override
    public int getItemCount() {
        return mDatalist.size();
    }

    class FuliItemHolder extends RecyclerView.ViewHolder {
        private ImageView iv_fuli;

        public FuliItemHolder(View itemView) {
            super(itemView);
            iv_fuli = (ImageView) itemView.findViewById(R.id.iv_item_fuli);
        }
    }
}
