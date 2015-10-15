package com.example.butterknife;

import com.facebook.buck.android.support.exopackage.ExopackageApplication;

/**
 * Created by guyacong on 15/10/2.
 */
public class AppShell extends ExopackageApplication {


    public AppShell() {
        super(com.example.butterknife.BuildConfig.EXOPACKAGE_FLAGS != 0);
    }

}
