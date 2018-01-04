package com.gionee.okko.request;

import com.gionee.okko.utils.HttpUtils;

import java.io.File;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by xiaozhilong on 18-1-3.
 */

public class PostRequest extends AbstractRequest {
    public static final MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    public static final MediaType MEDIA_TYPE_HTML = MediaType.parse("text/html; charset=utf-8");
    public static final MediaType MEDIA_TYPE_XML = MediaType.parse("text/xml; charset=utf-8");
    public static final MediaType MEDIA_TYPE_GIF = MediaType.parse("image/gif");
    public static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpeg");
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");
    public static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream"); //二进制流文件
    public static final MediaType MEDIA_TYPE_MULTIPART = MediaType.parse("multipart/form-data"); //需要在表单中进行文件上传时，就需要使用该格式

    private RequestBody mRequestBody;
    private MediaType mMediaType;        //上传的MIME类型
    private String mContent;             //上传的文本内容
    private byte[] mBytes;               //上传的字节数据
    private File mFile;                  //单纯的上传一个文件

    public PostRequest() {
        super();
    }

    @Override
    protected String getMethod() {
        return "POST";
    }

    /**
     * 自定义的请求体
     *
     * @param body 自定义的请求体
     */
    public void uploadRequestBody(RequestBody body) {
        mRequestBody = body;
    }

    /**
     * 上传字符串数据
     *
     * @param string 上传字符串数据
     */
    public void uploadString(String string) {
        mContent = string;
        mMediaType = MEDIA_TYPE_PLAIN;
    }

    /**
     * 上传json数据
     *
     * @param json 上传json数据
     */
    public void uploadJson(String json) {
        mContent = json;
        mMediaType = MEDIA_TYPE_JSON;
    }

    /**
     * 上传的字节数据
     *
     * @param bytes 上传的字节数据
     */
    public void uploadBytes(byte[] bytes) {
        mBytes = bytes;
        mMediaType = MEDIA_TYPE_STREAM;
    }

    /**
     * 上传一个文件
     *
     * @param file 上传的文件
     */
    public void uploadFile(File file) {
        mFile = file;
        mMediaType = HttpUtils.guessMimeType(file.getName());
    }

    /**
     * 上传一个文件
     *
     * @param file      上传的文件
     * @param mediaType 文件的MIME类型
     */
    public void uploadFile(File file, MediaType mediaType) {
        mFile = file;
        mMediaType = mediaType;
    }

    @Override
    protected RequestBody generateRequestBody() {
        Map<String, String> params = getParams();
        if (params != null && params.size() != 0) {
            FormBody.Builder builder = new FormBody.Builder();
            createFormBodyWithParams(builder, params);
            return builder.build();
        }

        if (mRequestBody != null) {
            return mRequestBody;
        }

        if (mContent != null && mMediaType != null) {
            return RequestBody.create(mMediaType, mContent);
        }

        if (mBytes != null && mMediaType != null) {
            return RequestBody.create(mMediaType, mBytes);
        }

        if (mFile != null && mMediaType != null) {
            return RequestBody.create(mMediaType, mFile);
        }

        return null;
    }

    @Override
    public Request generateRequest() {
        Headers headers = HttpUtils.createHeaders(getHeader());
        Request.Builder builder = new Request.Builder()
                .url(getUrl())
                .method(getMethod(), generateRequestBody())
                .tag(getTag());
        if (headers != null) {
            builder.headers(headers);
        }

        return builder.build();
    }

    private void createFormBodyWithParams(FormBody.Builder builder, Map<String, String> params) {
        if (params == null || params.size() == 0) {
            return;
        }

        Set<String> keys = params.keySet();
        for (String key : keys) {
            builder.add(key, params.get(key));
        }
    }
}
