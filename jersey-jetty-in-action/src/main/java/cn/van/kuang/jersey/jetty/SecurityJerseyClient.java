package cn.van.kuang.jersey.jetty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SecurityJerseyClient {

    private final static Logger logger = LoggerFactory.getLogger(SecurityJerseyClient.class);

    private final static String ADDRESS = Constants.SSL_HOST + ":" + Constants.SSL_PORT;

    private void requestHTML() throws Exception {
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                    }
                }
        }, null);

        Client client = ClientBuilder
                .newBuilder()
                .hostnameVerifier((s, session) -> true)
                .sslContext(sslContext)
                .build();

        String html = client
                .target(ADDRESS)
                .path(Constants.PATH_QUERY)
                .path("html")
                .request(MediaType.TEXT_HTML_TYPE)
                .get(String.class);

        logger.info(html);
    }

    public static void main(String[] args) throws Exception {
        SecurityJerseyClient jerseyClient = new SecurityJerseyClient();
        jerseyClient.requestHTML();
    }
}
