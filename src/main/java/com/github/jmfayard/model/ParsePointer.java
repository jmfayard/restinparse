package com.github.jmfayard.model;

public class ParsePointer {
    public final String __type;
    public final String className;
    public final String objectId;

    public ParsePointer(String className, String objectId) {
        __type = "Pointer";
        this.className = className;
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "ParsePointer{" +
                "__type='" + __type + '\'' +
                ", className='" + className + '\'' +
                ", objectId='" + objectId + '\'' +
                '}';
    }
}
