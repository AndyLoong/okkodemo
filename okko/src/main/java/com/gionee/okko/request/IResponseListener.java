package com.gionee.okko.request;

/**
 * Created by xiaozhilong on 18-1-3.
 */

/**
 * 异步从服务器获取数据的时候，需要传入此回调，获取数据
 */
public interface IResponseListener {

    /**
     * 从服务器获取数据失败，调用此接口
     */
    public void onFail();

    /**
     * 从服务器获取数据成功，调用此接口
     * @param response 通过此接口可以获取相关类型的返回数据
     */
    public void onSuccess(IHttpResponse response);
}
