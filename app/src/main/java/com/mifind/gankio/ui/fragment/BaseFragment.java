package com.mifind.gankio.ui.fragment;

import android.app.Fragment;
import android.view.View;

import com.mifind.gankio.callback.ILoadingView;

/**
 * Created by Xuanjiawei on 2016/6/14.
 */
public class BaseFragment extends Fragment implements ILoadingView {

    @Override
    public void startLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg, View.OnClickListener onClickListener) {

    }
}
