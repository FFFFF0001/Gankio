// Generated code from Butter Knife. Do not modify!
package com.mifind.gankio.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainFragment$$ViewBinder<T extends com.mifind.gankio.ui.fragment.MainFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558529, "field 'mRvMainLayout'");
    target.mRvMainLayout = finder.castView(view, 2131558529, "field 'mRvMainLayout'");
  }

  @Override public void unbind(T target) {
    target.mRvMainLayout = null;
  }
}
