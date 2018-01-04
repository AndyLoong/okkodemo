package com.gionee.okkodemo;

import android.app.Application;

import com.gionee.okko.Okko;

/**
 * Created by xiaozhilong on 18-1-4.
 */

public class OkkoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Okko.init(this);
    }
}
