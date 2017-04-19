package com.github.jmfayard.model;


import java.util.Collections;
import java.util.List;

public class ParseBatch {

    public final List<ParseBatchRequest> requests;

    public ParseBatch(List<ParseBatchRequest> requests) {
        this.requests = requests == null ? Collections.emptyList() : requests;
    }

    @Override
    public String toString() {
        return "ParseBatch{" +
                "requests=" + requests +
                '}';
    }
}
