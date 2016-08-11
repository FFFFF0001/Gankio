// Generated code from Butter Knife. Do not modify!
package com.mifind.gankio.ui.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class WebViewActivity$$ViewBinder<T extends com.mifind.gankio.ui.activity.WebViewActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558511, "field 'mWebView'");
    target.mWebView = finder.castView(view, 2131558511, "field 'mWebView'");
  }

  @Override public void unbind(T target) {
    target.mWebView = null;
  }
}
