package com.github.jmfayard.model;

public class ParsePtr {
    final String __type;
    final String className;
    final String objectId;

    public ParsePtr(String className, String objectId) {
        __type = "Pointer";
        this.className = className;
        this.objectId = objectId;
    }
}
