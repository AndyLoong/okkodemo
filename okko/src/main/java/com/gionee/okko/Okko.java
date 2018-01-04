package com.gionee.okko;

import android.content.Context;

import com.gionee.okko.interceptor.LoggingInterceptor;
import com.gionee.okko.request.GetRequest;
import com.gionee.okko.request.IHttpResponse;
import com.gionee.okko.request.IResponseListener;
import com.gionee.okko.request.PostRequest;
import com.gionee.okko.utils.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xiaozhilong on 18-1-2.
 */

/**
 * 此类封装了Okhttp的方法，使用更加简洁：
 */
public final class Okko {
    private static final String TAG = "Okko";

    private static final int TIMEOUT_READ = 10000;
    private static final int TIMEOUT_WRITE = 10000;
    private static final int TIMEOUT_CONNECT = 10000;
    private static final int CACHE_MAX_SIZE = 10 * 1024 * 1024;

    private static Context mContext;
    private OkHttpClient mOkHttpClient;

    /**
     * Okko 单例，采用静态内部类的方式，只创建一个OkHttpClient，保证只有一个连接池和线程池
     * 减少延时和节省内存
     */
    public static Okko getInstance() {
        return OkkoHolder.holder;
    }

    private Okko() {
        checkInit();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(TIMEOUT_READ, TimeUnit.MILLISECONDS)
                .writeTimeout(TIMEOUT_WRITE, TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new LoggingInterceptor())
                .cache(new Cache(new File(mContext.getCacheDir().getAbsolutePath()), CACHE_MAX_SIZE));
        mOkHttpClient = builder.build();
    }

    /**
     * @param context 此处使用Application Context，不会造成内存泄露
     */
    public static void init(Context context) {
        mContext = context.getApplicationContext();
    }

    private void checkInit() {
        if (mContext == null) {
            throw new NullPointerException("没有初始化，请在Application里面调用init()函数进行初始化！！！");
        }
    }

    /**
     * 可以设置自己定制化的OkHttpClient,只设置一次
     *
     * @param okHttpClient
     */
    public void setOkHttpClient(OkHttpClient okHttpClient) {
        mOkHttpClient = okHttpClient;
    }

    /**
     * 采用“GET”方式，从服务器获取数据，此方法会阻塞线程
     *
     * @param getRequest 此对象包含了用户传入的所有参数
     * @return 返回数据结果
     */
    public IHttpResponse getSync(GetRequest getRequest) {
        Request request = getRequest.generateRequest();
        try {
            final Response response = mOkHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                Logger.e(TAG, "getSync, get response fail.");
                return null;
            }

            return new IHttpResponse() {
                @Override
                public String getString() {
                    try {
                        return response.body().string();
                    } catch (IOException e) {
                        Logger.e(TAG, "getSync getString fail, e = " + e.toString());
                    }
                    return null;

                }

                @Override
                public InputStream getStream() {
                    return response.body().byteStream();
                }
            };
        } catch (IOException e) {
            Logger.e(TAG, "getSync, IOException = " + e.toString());
            return null;
        }
    }

    /**
     * 采用“GET”方式，从服务器获取数据，此方法不会阻塞线程
     *
     * @param getRequest 此对象包含了用户传入的所有参数
     * @param listener   获取数据完成，会调用此回调函数通知用户
     */
    public void getAsync(GetRequest getRequest, final IResponseListener listener) {
        Request request = getRequest.generateRequest();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.d(TAG, "getAsync onFailure, e = " + e.toString());
                if (listener != null) {
                    listener.onFail();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (listener == null) {
                    Logger.d(TAG, "getAsync onResponse, listener is null.");
                    return;
                }

                if (!response.isSuccessful()) {
                    Logger.d(TAG, "getAsync, onResponse fail!");
                    listener.onFail();
                    return;
                }

                ResponseWrapper wrapper = new ResponseWrapper();
                wrapper.wrapResponse(response);
                listener.onSuccess(wrapper);
            }
        });
    }

    /**
     * 采用“POST”方式，从服务器获取数据，此方法会阻塞线程
     *
     * @param postRequest 此对象包含了用户传入的所有参数
     * @return 返回数据结果
     */
    public IHttpResponse postSync(PostRequest postRequest) {
        Request request = postRequest.generateRequest();
        try {
            final Response response = mOkHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                Logger.e(TAG, "postSync, get response fail.");
                return null;
            }

            return new IHttpResponse() {
                @Override
                public String getString() {
                    try {
                        return response.body().string();
                    } catch (IOException e) {
                        Logger.e(TAG, "postSync getString fail, e = " + e.toString());
                    }
                    return null;

                }

                @Override
                public InputStream getStream() {
                    return response.body().byteStream();
                }
            };
        } catch (IOException e) {
            Logger.e(TAG, "postSync, IOException = " + e.toString());
            return null;
        }
    }

    /**
     * 采用“POST”方式，从服务器获取数据，此方法不会阻塞线程
     *
     * @param postRequest 此对象包含了用户传入的所有参数
     * @param listener    获取数据完成，会调用此回调函数通知用户
     */
    public void postAsync(PostRequest postRequest, final IResponseListener listener) {
        Request request = postRequest.generateRequest();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.d(TAG, "postAsync onFailure, e = " + e.toString());
                if (listener != null) {
                    listener.onFail();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (listener == null) {
                    Logger.d(TAG, "postAsync onResponse, listener is null.");
                    return;
                }

                if (!response.isSuccessful()) {
                    Logger.d(TAG, "postAsync, onResponse fail!");
                    listener.onFail();
                    return;
                }

                ResponseWrapper wrapper = new ResponseWrapper();
                wrapper.wrapResponse(response);
                listener.onSuccess(wrapper);
            }
        });
    }

    /**
     * 通过此方法创建新的OkHttpClient，但是可以与拷贝的OkHttpClient复用相同的线程池和连接池
     * 可以修改一些参数，比如readTimeout
     *
     * @return
     */
    public OkHttpClient.Builder newBuilder() {
        return mOkHttpClient.newBuilder();
    }

    private static class OkkoHolder {
        private static Okko holder = new Okko();
    }

    private static class ResponseWrapper implements IHttpResponse {
        private static final String TAG = "IHttpResponse";

        private Response mResponse;

        public void wrapResponse(Response response) {
            mResponse = response;
        }

        @Override
        public String getString() {
            if (mResponse == null) {
                Logger.d(TAG, "getString, response is null.");
                return null;
            }
            try {
                return mResponse.body().string();
            } catch (IOException e) {
                Logger.e(TAG, "getString, IOException = " + e.toString());
            }

            return null;
        }

        @Override
        public InputStream getStream() {
            if (mResponse == null) {
                Logger.d(TAG, "getStream, response is null.");
                return null;
            }
            return mResponse.body().byteStream();
        }
    }

}
