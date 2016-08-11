package com.mifind.gankio.callback;

import android.view.View;

/**
 * Created by Xuanjiawei on 2016/8/10.
 */
public interface ILoadingView {
    void startLoading();

    void hideLoading();

    void showError(String msg, View.OnClickListener onClickListener);
}
