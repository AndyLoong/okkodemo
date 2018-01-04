package com.gionee.okko.request;

import java.io.InputStream;

/**
 * Created by xiaozhilong on 18-1-3.
 */

/**
 * 同步从服务器获取数据的时候，返回此接口
 */
public interface IHttpResponse {

    /**
     *
     * @return 返回字符串类型数据，比如json格式
     */
    public String getString();

    /**
     *
     * @return 返回字节流类型的数据
     */
    public InputStream getStream();
}
