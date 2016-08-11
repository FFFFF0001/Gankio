package com.mifind.gankio;

import android.app.Application;
import android.content.Context;

import com.mifind.gankio.http.RequestManager;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by Xuanjiawei on 2016/8/8.
 */
public class GankApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Logger.init("gankio")
                .methodOffset(2)
                .methodCount(2)
                .hideThreadInfo()
                .logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);
        //必须调用初始化
        RequestManager.init(this);
        //以下都不是必须的，根据需要自行选择
        RequestManager.getInstance().debug("gankio");

    }

    public static Context getContext() {
        return context;
    }
}
