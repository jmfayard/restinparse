package com.github.jmfayard.model;

import java.util.Map;

import static com.github.jmfayard.internal.ParseRestClientFactory.parseBatchAdapter;


public class ParseBatchRequest {

    public final String method;
    public final String path;
    public final Map<String, Object> body;

    public ParseBatchRequest(String method, Map<String, Object> body, String path) {
        this.method = method;
        this.path = path;
        this.body = body;
    }

    @Override
    public String toString() {
        return toJson();
    }

    private String toJson() {
        return parseBatchAdapter().toJson(this);
    }
}
