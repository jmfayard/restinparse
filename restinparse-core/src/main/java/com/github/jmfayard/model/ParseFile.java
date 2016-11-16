package com.github.jmfayard.model;

public class ParseFile {
    public final String name;
    public final String url;
    public final String __type;


    public ParseFile(String name, String url) {
        __type = "File";
        this.name = name;
        this.url = url;
    }

    @Override
    public String toString() {
        return "ParseFile{" +
                "__type='" + __type + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
