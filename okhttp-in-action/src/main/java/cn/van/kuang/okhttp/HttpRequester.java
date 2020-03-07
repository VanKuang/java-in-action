package cn.van.kuang.okhttp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequester {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpRequester.class);

    public String doGet(final String url) {
        final OkHttpClient okHttpClient = OkHttpClientFactory.getOkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        try (final Response response = okHttpClient.newCall(request).execute()) {
            LOGGER.info("Got response, statusCode={}", response.code());
            if (response.isSuccessful()) {
                final ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    return responseBody.string();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Fail to call [" + url + "]", e);
        }

        throw new RuntimeException("Fail to call [" + url + "]");
    }

    public static void main(String[] args) {
        final HttpRequester httpRequester = new HttpRequester();
        final String response = httpRequester.doGet("https://www.baidu.com");
        LOGGER.info("{}", response);
    }

}
