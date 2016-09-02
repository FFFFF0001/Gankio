package com.mifind.gankio.http;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.mifind.gankio.db.DBManager;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import me.xiaopan.android.net.NetworkUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 管理所有请求类
 * Created by xuanjiawei on 2016/2/1.
 */
public class RequestManager {
    private static final String ERROR = "error";
    private static final String RESULTS = "results";
    public static final int DEFAULT_MILLISECONDS = 6000; //默认的超时时间
    private static RequestManager mInstance; //单例
    private static Application mContext; //全局上下文
    private OkHttpClient.Builder okHttpClientBuilder;     //ok请求的客户端
    private OkHttpClient okHttpClient;
    private static Handler mDelivery = new Handler(Looper.getMainLooper());

    public static RequestManager getInstance() {
        if (mInstance == null) {
            synchronized (RequestManager.class) {
                if (mInstance == null) {
                    mInstance = new RequestManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 必须在全局Application先调用，获取context上下文，否则缓存无法使用
     */
    public static void init(Application app) {
        mContext = app;
    }

    private RequestManager() {
        okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        okHttpClientBuilder.readTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        okHttpClientBuilder.writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        mDelivery = new Handler(Looper.getMainLooper());
        okHttpClient = okHttpClientBuilder.build();
    }

    /**
     * 调试模式
     */
    public RequestManager debug(String tag) {
        okHttpClientBuilder.addInterceptor(new LoggerInterceptor(tag, true));
        return this;
    }


    public void get(Object tag, final String url, final ICallBack callBack) {
        get(tag, url, false, callBack);
    }

    public void get(Object tag, final String url, final boolean isCache, final ICallBack callBack) {
        //读取缓存数据
        final DBManager dbManager = new DBManager();
        String data = dbManager.getData(url);
        if (!"".equals(data)) {
            //解析json数据并返回成功回调
            callBack.onSuccess(new Gson().fromJson(data, callBack.type));
        }

        //判断网络是否已连接，连接则往下走，未连接则返回失败回调，并终止请求
        if (!NetworkUtils.isConnectedByState(mContext)) {
            callBack.onFailure("network not contented!!");
            return;
        }
        //初始化请求对象
        Request request = new Request.Builder()
                .url(url)
                .tag(tag)
                .build();

        //像服务器发送异步请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mDelivery.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailure(e.getLocalizedMessage());
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取String类型响应，注意是string(),不是toString()
                final String json = response.body().string();
                //在控制台格式化打印json数据
                Logger.json(json);
                mDelivery.post(new Runnable() {
                    @Override
                    public void run() {
                        handleResponse(json, callBack, dbManager, url, isCache);
                    }
                });

            }
        });
    }

    /**
     * 处理请求结果
     *
     * @param json
     * @param callBack
     * @param dbManager
     * @param url
     */
    private void handleResponse(String json, ICallBack callBack, DBManager dbManager, String url, boolean isCache) {
        try {
            //转化为json对象
            JSONObject jsonObject = new JSONObject(json);
            //判断error字段是否存在，不存在返回失败信息并结束请求
            if (jsonObject.isNull(ERROR)) {
                callBack.onFailure("error key not exists!!");
                return;
            }
            //判断后台返回结果，true表示失败，false表示成功，失败则返回错误回调并结束请求
            if (jsonObject.getBoolean(ERROR)) {
                callBack.onFailure("request failure!!");
                return;
            }
            //判断results字段是否存在，不存在返回时报回调并结束请求
            if (jsonObject.isNull(RESULTS)) {
                callBack.onFailure("results key not exists!!");
                return;
            }
            //获取results的值
            String results = jsonObject.getString(RESULTS);
            if (isCache) {
                //插入缓存数据库
                dbManager.insertData(url, results);
            }

            //返回成功回调
            callBack.onSuccess(new Gson().fromJson(results, callBack.type));
        } catch (JSONException e) {
            callBack.onFailure(e.getLocalizedMessage());
        }
    }

    /**
     * 根据tag取消请求
     *
     * @param tag 标签
     */
    public void cancelRequest(Object tag) {
        for (Call call : okHttpClient.dispatcher().queuedCalls()) {
            if (call.request().tag().equals(tag)) {
                call.cancel();
            }
        }
        for (Call call : okHttpClient.dispatcher().runningCalls()) {
            if (call.request().tag().equals(tag)) {
                call.cancel();
            }
        }
    }

    public void cancelAllRequest() {
        for (Call call : okHttpClient.dispatcher().queuedCalls()) {
            call.cancel();
        }
        for (Call call : okHttpClient.dispatcher().runningCalls()) {
            call.cancel();
        }
    }
}
