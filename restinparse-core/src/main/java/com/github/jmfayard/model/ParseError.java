package com.github.jmfayard.model;

public class ParseError extends RuntimeException {
    public final int code;
    public final String message;

    public ParseError(int code, String message) {
        super("ParseError[" + code + "] " + message);
        this.code = code;
        this.message = message;
    }


}
