package com.gionee.okko.utils;

import android.net.Uri;
import android.text.TextUtils;

import com.gionee.okko.request.PostRequest;

import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.MediaType;

/**
 * Created by xiaozhilong on 18-1-3.
 */

public final class HttpUtils {
    private static final String TAG = "HttpUtils";

    /**
     * 将参数拼接到Url后面
     *
     * @param url    服务器网址
     * @param params 参数
     * @return 返回带参数的Url
     */
    public static String createUrlFromParams(String url, Map<String, String> params) {
        if (TextUtils.isEmpty(url)) {
            Logger.e(TAG, "createUrlFromParams, url is empty : url = " + url);
            return null;
        }

        if (params == null || params.size() == 0) {
            Logger.d(TAG, "createUrlFromParams, params is empty, ignore.");
            return url;
        }

        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        for (String key : keys) {
            builder.appendQueryParameter(key, params.get(key));
        }
        String result = builder.build().toString();
        Logger.d(TAG, "createUrlFromParams, url with params = " + result);
        return result;
    }

    /**
     * 将用户传入的header参数转化为okhttp识别的header
     *
     * @param originHeader 用户传入的header
     * @return 返回okhttp识别的Headers
     */
    public static Headers createHeaders(Map<String, String> originHeader) {
        if (originHeader == null || originHeader.size() == 0) {
            Logger.d(TAG, "createHeaders, originHeader is empty!");
            return null;
        }

        Headers.Builder builder = new Headers.Builder();
        Set<String> keys = originHeader.keySet();
        for (String key : keys) {
            builder.set(key, originHeader.get(key));
        }
        return builder.build();
    }

    /**
     * 根据文件名获取MIME类型
     *
     * @param fileName 文件名
     * @return 返回MIME类型
     */
    public static MediaType guessMimeType(String fileName) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        fileName = fileName.replace("#", "");   //解决文件名中含有#号异常的问题
        String contentType = fileNameMap.getContentTypeFor(fileName);
        if (contentType == null) {
            return PostRequest.MEDIA_TYPE_STREAM;
        }
        return MediaType.parse(contentType);
    }
}
