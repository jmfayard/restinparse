package com.github.jmfayard.model;

public class ParsePtr {
    public final String __type;
    public final String className;
    public final String objectId;

    public ParsePtr(String className, String objectId) {
        __type = "Pointer";
        this.className = className;
        this.objectId = objectId;
    }
}
