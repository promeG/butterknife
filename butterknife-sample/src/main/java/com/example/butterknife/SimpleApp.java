package com.example.butterknife;

import com.facebook.buck.android.support.exopackage.DefaultApplicationLike;

import android.app.Application;

import butterknife.ButterKnife;

public class SimpleApp extends DefaultApplicationLike {
  private final Application appContext;

  public SimpleApp(Application appContext) {
    this.appContext = appContext;
  }

  @Override public void onCreate() {
    super.onCreate();
    ButterKnife.setDebug(true);
  }
}
