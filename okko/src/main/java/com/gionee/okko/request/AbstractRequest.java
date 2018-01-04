package com.gionee.okko.request;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by xiaozhilong on 18-1-2.
 */

/**
 * Request基类，封装了用户传入的一些参数
 */
abstract class AbstractRequest {
    private String mUrl;
    private Object mTag;
    private Map<String, String> mHeader;
    private Map<String, String> mParams;

    public AbstractRequest() {
        mHeader = new LinkedHashMap<>();
        mParams = new LinkedHashMap<>();
    }

    /**
     * 设置服务器Url
     *
     * @param url
     */
    public void setUrl(String url) {
        mUrl = url;
    }

    /**
     * @return 获取传入的Url
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * 设置tag标识
     *
     * @param tag
     */
    public void setTag(Object tag) {
        mTag = tag;
    }

    /**
     * @return 返回tag
     */
    public Object getTag() {
        return mTag;
    }

    /**
     * 设置单个header键值对
     *
     * @param name  header名称
     * @param value 对应的值
     */
    public void putHeader(String name, String value) {
        mHeader.put(name, value);
    }

    /**
     * 设置一组header键值对
     *
     * @param header
     */
    public void putHeader(Map<String, String> header) {
        mHeader.putAll(header);
    }

    /**
     * @return 返回所有的header键值对
     */
    public Map<String, String> getHeader() {
        return mHeader;
    }

    /**
     * 设置参数键值对
     *
     * @param name  参数名称
     * @param value 对应的值
     */
    public void putParam(String name, String value) {
        mParams.put(name, value);
    }

    /**
     * 设置多个参数键值对
     *
     * @param params 键值对组
     */
    public void putParam(Map<String, String> params) {
        mParams.putAll(params);
    }

    /**
     * @return 返回所有的参数键值对
     */
    public Map<String, String> getParams() {
        return mParams;
    }

    /**
     * @return 返回获取数据的方式，一般为"GET"或"POST"
     */
    protected abstract String getMethod();

    /**
     * “POST”方式时，生成RequestBody
     *
     * @return 返回RequestBody
     */
    protected abstract RequestBody generateRequestBody();

    /**
     * @return 返回Okhttp形式的Request
     * @hide
     */
    public abstract Request generateRequest();

}
