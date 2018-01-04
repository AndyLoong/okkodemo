package com.gionee.okkodemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.gionee.okko.Okko;
import com.gionee.okko.request.GetRequest;
import com.gionee.okko.request.IHttpResponse;
import com.gionee.okko.request.IResponseListener;

/**
 * Created by xiaozhilong on 18-1-4.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        testOkko();
    }

    private void testOkko() {
        Okko okko = Okko.getInstance();
        final GetRequest request = new GetRequest();
        request.setUrl("https://www.baidu.com");
        okko.getAsync(request, new IResponseListener() {
            @Override
            public void onFail() {

            }

            @Override
            public void onSuccess(IHttpResponse response) {
                if (response != null) {
                    Log.d("Andy", "result = " + response.getString());
                }
            }
        });
    }
}
