package com.datapyro.kinesis.model;

import java.io.Serializable;

/**
 * This class represents an Apache access log line.
 * See http://httpd.apache.org/docs/2.2/logs.html for more details.
 */
public class ApacheAccessLog implements Serializable {

    private String ipAddress;
    private String clientId;
    private String userID;
    private String dateTimeString;
    private String method;
    private String endpoint;
    private String protocol;
    private int responseCode;
    private long contentSize;
    private String url;

    public ApacheAccessLog(String ipAddress, String clientId, String userID, String dateTimeString, String method,
                           String endpoint, String protocol, int responseCode, long contentSize, String url) {
        this.ipAddress = ipAddress;
        this.clientId = clientId;
        this.userID = userID;
        this.dateTimeString = dateTimeString;
        this.method = method;
        this.endpoint = endpoint;
        this.protocol = protocol;
        this.responseCode = responseCode;
        this.contentSize = contentSize;
        this.url = url;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s [%s] \"%s %s %s\" %s %s %s",
                             ipAddress, clientId, userID, dateTimeString, method,
                             endpoint, protocol, responseCode, contentSize, url);
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public ApacheAccessLog setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public ApacheAccessLog setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getUserID() {
        return userID;
    }

    public ApacheAccessLog setUserID(String userID) {
        this.userID = userID;
        return this;
    }

    public String getDateTimeString() {
        return dateTimeString;
    }

    public ApacheAccessLog setDateTimeString(String dateTimeString) {
        this.dateTimeString = dateTimeString;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public ApacheAccessLog setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public ApacheAccessLog setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public ApacheAccessLog setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public ApacheAccessLog setResponseCode(int responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public long getContentSize() {
        return contentSize;
    }

    public ApacheAccessLog setContentSize(long contentSize) {
        this.contentSize = contentSize;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ApacheAccessLog setUrl(String url) {
        this.url = url;
        return this;
    }
    
}
