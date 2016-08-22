package com.mifind.gankio.ui.view.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mifind.gankio.R;
import com.mifind.gankio.model.GankModel;
import com.mifind.gankio.utils.Utils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 李赫 on 2016/8/15.
 */
public class FuliRecyclerAdapter extends RecyclerView.Adapter<FuliRecyclerAdapter.FuliItemHolder> {

    private final static float SIZE_SCALE_01 = 4 / 3f;
    private final static float SIZE_SCALE_02 = 4 / 4f;

    private HashMap<Integer, Float> indexMap = new HashMap<Integer, Float>();
    private int screenWidth;
    List<GankModel> mDatalist;
    Context mContext;

    public FuliRecyclerAdapter(Context context, List<GankModel> mDatalist) {
        this.mContext = context;
        this.mDatalist = mDatalist;
        screenWidth = Utils.getScreenSize(context)[0] - Utils.dp2px(context, 8) * 3;
    }

    @Override
    public FuliItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.f_item_fuli, parent, false);
        FuliItemHolder holder = new FuliItemHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(FuliItemHolder holder, int position) {
        GankModel gankModel = mDatalist.get(position);
        if (null == gankModel) {
            return;
        }
        resizeItemView(holder.iv_fuli, getScaleType(position));
        holder.iv_fuli.setScaleType(ImageView.ScaleType.FIT_XY);
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

    private float getScaleType(int position) {
        if (!indexMap.containsKey(position)) {
            float scaleType;
            if (position == 0) {
                scaleType = SIZE_SCALE_01;
            } else if (position == 1) {
                scaleType = SIZE_SCALE_02;
            } else {
                scaleType = Utils.getRandomInt() % 2 == 0 ? SIZE_SCALE_01 : SIZE_SCALE_02;
            }
            indexMap.put(position, scaleType);
        }
        return indexMap.get(position);
    }

    private void resizeItemView(ImageView frontCoverImage, float scaleType) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) frontCoverImage.getLayoutParams();
        params.width = screenWidth / 2;
        params.height = (int) (params.width / scaleType) - Utils.dp2px(mContext, 8);
        frontCoverImage.setLayoutParams(params);
    }

}
