package cn.van.kuang.okhttp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.concurrent.TimeUnit;

public final class OkHttpClientFactory {

    // according to the doc., it should be shared
    private static OkHttpClient okHttpClient;

    public static synchronized OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .addInterceptor(chain -> {
                        final Request request = chain.request();

                        Response response = null;
                        for (int i = 0; i < 5; i++) {
                            if (response != null) {
                                response.close(); // TODO should close not successful response?
                            }

                            response = chain.proceed(request);
                            if (response.isSuccessful()) {
                                return response;
                            }
                        }

                        return response;
                    })
                    .build();
        }
        return okHttpClient;
    }

    private OkHttpClientFactory() {
    }

}
