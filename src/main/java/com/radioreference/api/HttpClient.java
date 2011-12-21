package com.radioreference.api;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;
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
    public static HttpClient getInstance() {
        if (instance == null) {
            instance = new HttpClient();
        }
        return instance;
    }

    public InputStream execute(Request requestBean) throws IOException {
        if (requestBean == null) {
            throw new IllegalArgumentException("Invalid request!");
        }

        HttpUriRequest request = null;


        String url = requestBean.getUrl();
        RequestMethod method = requestBean.getMethod();

        switch (method) {
            case GET: {
                request = new HttpGet(url);
                break;
            }
            case POST: {
                request = new HttpPost(url);
                Map<String, String> params = requestBean.getParameters();
                //Parameters may be null
                if (params != null && !params.isEmpty()) {
                    ((HttpPost) request).setEntity(mapToEntity(params));
                }
                break;
            }
            default:
                break;
        }

        return executeRequest(request);
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

    /**
     * Convert a Map to UrlEncodedFormEntity
     *
     * @param parameters A Map of key:value
     * @return UrlEncodedFormEntity using UTF-8 encoding
     * @throws java.io.UnsupportedEncodingException
     *          if UTF-8 encoding is not supported
     */
    private static UrlEncodedFormEntity mapToEntity(Map<String, String> parameters)
            throws UnsupportedEncodingException {
        if (parameters == null) {
            throw new IllegalArgumentException("Invalid parameters unable map to Entity");
        }

        if (parameters.isEmpty()) {
            return null;
        }

        ArrayList<NameValuePair> parametersList = new ArrayList<NameValuePair>();

        for (@SuppressWarnings("rawtypes") Map.Entry element : parameters.entrySet()) {
            NameValuePair nameValuePair = new BasicNameValuePair((String) element.getKey(), (String) element.getValue());
            parametersList.add(nameValuePair);
        }

        return new UrlEncodedFormEntity(parametersList, HTTP.UTF_8);
    }

    /**
     * HTTP request methods
     *
     * @version 1.0
     */
    public static enum RequestMethod {
        GET,
        POST,
        PUT,
        DELETE
    }

}
