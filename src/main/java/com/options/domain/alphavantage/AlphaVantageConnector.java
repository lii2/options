package com.options.domain.alphavantage;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AlphaVantageConnector implements AlphaVantageConstants {

    public AlphaVantageConnector() {
        Set<String> loggers = new HashSet<>(Arrays.asList("org.apache.http", "groovyx.net.http"));
        for (String log : loggers) {
            Logger logger = (Logger) LoggerFactory.getLogger(log);
            logger.setLevel(Level.INFO);
            logger.setAdditive(false);
        }
    }

    public String getTimeSeriesDaily(String ticker) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        String query = String.format(Query.TIME_SERIES_DAILY.getUrl(), ticker, TRADE_APP_API_KEY) + "&datatype=csv";
        RestTemplate restTemplate = getRestTemplate();
        String response = restTemplate.getForObject(query, String.class);
        return response;
    }

    public String getSmaDaily(String ticker, String interval) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        String query = String.format(Query.SMA_DAILY.getUrl(), ticker, interval, TRADE_APP_API_KEY) + "&datatype=csv";
        RestTemplate restTemplate = getRestTemplate();
        String response = restTemplate.getForObject(query, String.class);
        return response;
    }

    public String getEmaDaily(String ticker, String interval) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        String query = String.format(Query.EMA_DAILY.getUrl(), ticker, interval, TRADE_APP_API_KEY) + "&datatype=csv";
        RestTemplate restTemplate = getRestTemplate();
        String response = restTemplate.getForObject(query, String.class);
        return response;
    }

    public String getMacd(String ticker) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        String query = String.format(Query.MACD_DAILY.getUrl(), ticker, TRADE_APP_API_KEY) + "&datatype=csv";
        RestTemplate restTemplate = getRestTemplate();
        String response = restTemplate.getForObject(query, String.class);
        return response;
    }

    private RestTemplate getRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                return true;
            }
        };
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        return new RestTemplate(requestFactory);
    }
}