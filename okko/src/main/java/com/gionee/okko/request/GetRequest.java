package com.gionee.okko.request;

import com.gionee.okko.utils.HttpUtils;


import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by xiaozhilong on 18-1-2.
 */

public class GetRequest extends AbstractRequest {

    public GetRequest() {
        super();
    }

    @Override
    protected String getMethod() {
        return "GET";
    }

    @Override
    protected RequestBody generateRequestBody() {
        return null;
    }

    @Override
    public Request generateRequest() {
        String url = HttpUtils.createUrlFromParams(getUrl(), getParams());
        Headers headers = HttpUtils.createHeaders(getHeader());
        Request.Builder builder = new Request.Builder()
                .url(url)
                .method(getMethod(), generateRequestBody())
                .tag(getTag());
        if (headers != null) {
            builder.headers(headers);
        }

        return builder.build();
    }
}
