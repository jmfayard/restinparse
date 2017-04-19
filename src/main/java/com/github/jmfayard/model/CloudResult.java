package com.github.jmfayard.model;

public class CloudResult<T> {
    public final T result;

    public CloudResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return result.toString();
    }
}
