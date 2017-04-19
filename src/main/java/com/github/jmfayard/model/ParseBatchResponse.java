package com.github.jmfayard.model;

import static com.github.jmfayard.internal.ParseRestClientFactory.parseBatchResponseAdapter;


public class ParseBatchResponse {

    public final ParseError error;
    public final ParseMap success;

    public ParseBatchResponse(ParseError error, ParseMap success) {
        this.error = error;
        this.success = success;
    }

    @Override
    public String toString() {
        return toJson();
    }

    private String toJson() {
        return parseBatchResponseAdapter().toJson(this);
    }
}
