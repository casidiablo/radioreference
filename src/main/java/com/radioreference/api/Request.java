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
         * Method to use GET, POST, etc.
         */
        private HttpClient.RequestMethod method;
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
        Request(HttpClient.RequestMethod method, String url) {
            setMethod(method);
            setUrl(url);
        }

        /**
         * @param method     {@link #method}
         * @param url        {@link #url}
         * @param callMethod {@link #callMethodName}
         */
        public Request(HttpClient.RequestMethod method, String url, String callMethod) {
            this(method, url);
            setCallMethodName(callMethod);
        }

        /**
         * @return {@link #method}
         */
        public HttpClient.RequestMethod getMethod() {
            return method;
        }

        /**
         * @param method the {@link #method} to set
         *               GET by default
         */
        public void setMethod(HttpClient.RequestMethod method) {
            if (method == null) {
                method = HttpClient.RequestMethod.GET;
            }
            this.method = method;
        }

        /**
         * @return {@link #url}
         *         Note: in case of GET {@link #method} is {@link #url} plus {@link #parameters} if any
         */
        public String getUrl() {
            String realUrl = url + Utils.StringUtils.emptyIfNull(callMethodName);
            if (HttpClient.RequestMethod.GET.equals(method)) {
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
                if (HttpClient.RequestMethod.GET.equals(method)) {//For GET methods only care about the full URL and method
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
