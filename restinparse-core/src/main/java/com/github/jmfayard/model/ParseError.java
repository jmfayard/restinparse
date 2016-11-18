package com.github.jmfayard.model;

public class ParseError extends RuntimeException {
    public final int code;
    public final String error;

    public ParseError(int code, String error) {
        super("ParseError{" +
                "code=" + code +
                ", error='" + error + '\'' +
                '}');
        this.code = code;
        this.error = error;
    }

    @Override
    public String toString() {
        return  super.getMessage();
    }
}
