// Generated code from Butter Knife. Do not modify!
package com.mifind.gankio.ui.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends com.mifind.gankio.ui.activity.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558512, "field 'mToolbar'");
    target.mToolbar = finder.castView(view, 2131558512, "field 'mToolbar'");
    view = finder.findRequiredView(source, 2131558509, "field 'mDrawerLayout'");
    target.mDrawerLayout = finder.castView(view, 2131558509, "field 'mDrawerLayout'");
    view = finder.findRequiredView(source, 2131558510, "field 'mNavigationView'");
    target.mNavigationView = finder.castView(view, 2131558510, "field 'mNavigationView'");
  }

  @Override public void unbind(T target) {
    target.mToolbar = null;
    target.mDrawerLayout = null;
    target.mNavigationView = null;
  }
}
