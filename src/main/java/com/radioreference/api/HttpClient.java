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
import java.util.HashMap;
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

    /**
     * A really simple request bean
     *
     * @version 1.0
     */
    static class Request {
        /**
         * Method to use GET, POST, etc.
         */
        private RequestMethod method;
        /**
         * Unique resource locator
         */
        private String url;
        /**
         * For REST like APIs
         */
        private String callMethodName;
        /**
         * Parameters to the call
         * Note:
         * - in GET {@link #method} this parameters if any are appended to the end of the URL
         * - Parameters change a lot so please think a lot about before you re-use them
         */
        private Map<String, String> parameters;

        /**
         * @param method {@link #method}
         * @param url    {@link #url}
         */
        private Request(RequestMethod method, String url) {
            setMethod(method);
            setUrl(url);
        }

        /**
         * @param method     {@link #method}
         * @param url        {@link #url}
         * @param callMethod {@link #callMethodName}
         */
        public Request(RequestMethod method, String url, String callMethod) {
            this(method, url);
            setCallMethodName(callMethod);
        }

        /**
         * @return {@link #method}
         */
        public RequestMethod getMethod() {
            return method;
        }

        /**
         * @param method the {@link #method} to set
         *               GET by default
         */
        public void setMethod(RequestMethod method) {
            if (method == null) {
                method = RequestMethod.GET;
            }
            this.method = method;
        }

        /**
         * @return {@link #url}
         *         Note: in case of GET {@link #method} is {@link #url} plus {@link #parameters} if any
         */
        public String getUrl() {
            String realUrl = url + Utils.StringUtils.emptyIfNull(callMethodName);
            if (RequestMethod.GET.equals(method)) {
                return Utils.StringUtils.urlEncode(realUrl, parameters, true);
            }
            return realUrl;
        }

        /**
         * @return {@link #url}
         *         The difference between this and {@link #getUrl()} is that doesn't append anything
         */
        public String getBaseUrl() {
            return url;
        }

        /**
         * @param url the {@link #url} to set
         */
        public void setUrl(String url) {
            if (url == null) {
                throw new IllegalArgumentException("Invalid URL");
            }
            this.url = url;
        }

        /**
         * @return {@link #parameters}
         */
        public Map<String, String> getParameters() {
            if (parameters == null) { //Lazy load
                parameters = new HashMap<String, String>();
            }
            return parameters;
        }

        /**
         * @param parameters the {@link #parameters} to set
         */
        public void setParameters(Map<String, String> parameters) {
            this.parameters = parameters;
        }

        /**
         * Adds a parameter to this request
         *
         * @param parameter The parameter to add
         * @param value     The value of the parameter
         */
        public void addParameter(String parameter, String value) {
            getParameters().put(parameter, value);
        }

        /**
         * Returns the value of the given parameter
         *
         * @param parameter The parameter which the value is needed to return
         *                  if was not added previously is null
         * @return the value of the given parameter or null if parameter was not added before
         */
        public String getParameter(String parameter) {
            return getParameters().get(parameter);
        }

        /**
         * Removes the given parameter
         *
         * @param parameter The parameter to remove
         * @return the value of the given parameter or null if parameter was not added before
         */
        public String removeParameter(String parameter) {
            return getParameters().remove(parameter);
        }

        /**
         * @return {@link #callMethodName}
         */
        public String getCallMethodName() {
            return callMethodName;
        }

        /**
         * @param callMethodName the {@link #callMethodName} to set
         */
        public void setCallMethodName(String callMethodName) {
            this.callMethodName = callMethodName;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Request) {
                Request request = (Request) o;
                if (RequestMethod.GET.equals(method)) {//For GET methods only care about the full URL and method
                    return Utils.CompareUtils.areEquals(request.getMethod(), getMethod())
                            && Utils.CompareUtils.areEquals(request.getUrl(), getUrl());
                } else {
                    return Utils.CompareUtils.areEquals(request.getMethod(), getMethod())
                            && Utils.CompareUtils.areEquals(request.getBaseUrl(), getBaseUrl())
                            && Utils.CompareUtils.areMapsContentEquals(request.getParameters(), getParameters());
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            return (getMethod() + getUrl()).hashCode();
        }

        @Override
        public String toString() {
            return super.toString()
                    + "[ method: " + getMethod()
                    + ", fullUrl: " + getUrl()
                    + ", baseUrl: " + getBaseUrl()
                    + ", parameters: " + getParameters()
                    + "]";
        }
    }
}
