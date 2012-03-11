package com.radioreference.api;

import java.util.HashMap;
import java.util.Map;

/**
 * A really simple request bean
 *
 * @version 1.0
 */
class Request {
    /**
     * Unique resource locator
     */
    private String mUrl;
    /**
     * For REST like APIs
     */
    private String callMethodName;
    /**
     * Parameters to the call
     * Note:
     * - this parameters if any are appended to the end of the URL
     * - Parameters change a lot so please think a lot about before you re-use them
     */
    private Map<String, String> parameters;

    /**
     * @param url {@link #mUrl}
     */
    Request(String url) {
        setUrl(url);
    }

    /**
     * @param url        {@link #mUrl}
     * @param callMethod {@link #callMethodName}
     */
    public Request(String url, String callMethod) {
        this(url);
        setCallMethodName(callMethod);
    }

    /**
     * @return {@link #mUrl}
     */
    public String getUrl() {
        String methodName = callMethodName;
        if (methodName == null) {
            methodName = "";
        }
        String realUrl = mUrl + methodName;
        return Utils.urlEncode(realUrl, parameters, true);
    }

    /**
     * @return {@link #mUrl}
     *         The difference between this and {@link #getUrl} is that doesn't append anything
     */
    public String getBaseUrl() {
        return mUrl;
    }

    /**
     * @param mUrl the {@link #mUrl} to set
     */
    public void setUrl(String mUrl) {
        if (mUrl == null) {
            throw new IllegalArgumentException("Invalid URL");
        }
        this.mUrl = mUrl;
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
            Object obj1 = request.getUrl();
            Object obj2 = getUrl();
            if (obj1 != null) {
                return obj1.equals(obj2);
            }
            return obj1 == obj2;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getUrl().hashCode();
    }

    @Override
    public String toString() {
        return super.toString()
                + "[fullUrl: " + getUrl()
                + ", baseUrl: " + getBaseUrl()
                + ", parameters: " + getParameters()
                + "]";
    }
}
