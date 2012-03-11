package com.radioreference.api;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * Apache HttpClient with GZIP support
 * @version 1.0
 */
class HttpClient {
    private static final String HEADER_ENCODING = "Accept-Encoding";
    private static final String ENCODING_GZIP = "gzip";
    /**
     * Unique instance
     */
    private static HttpClient instance;

    /**
     * Singleton constructor
     */
    private HttpClient() {
    }

    /**
     * Singleton method
     *
     * @return {@link #instance}
     */
    static HttpClient getInstance() {
        if (instance == null) {
            instance = new HttpClient();
        }
        return instance;
    }

    InputStream execute(Request requestBean) throws IOException {
        String url = requestBean.getUrl();
        return executeRequest(new HttpGet(url));
    }

    /**
     * The real execute method
     *
     * @param request the request to execute
     * @return Response
     *         Response bean may be null
     * @throws java.io.IOException thrown by the network transactions
     */
    private static InputStream executeRequest(HttpUriRequest request) throws IOException {
        org.apache.http.client.HttpClient client = new DefaultHttpClient();

        try {

            request.addHeader(HEADER_ENCODING, ENCODING_GZIP);

            HttpResponse httpResponse = client.execute(request);

            HttpEntity response = httpResponse.getEntity();

            if (response != null) {
                InputStream inputStream = response.getContent();

                Header contentEncoding = response.getContentEncoding();

                if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
                    inputStream = new GZIPInputStream(inputStream);
                }

                return inputStream;
            }

        } catch (IOException e) {
            client.getConnectionManager().shutdown();
            throw e;
        }
        return null;
    }
}
