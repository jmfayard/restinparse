package com.github.jmfayard.model;

public class ParseFile {
    final String name;
    final String url;
    final String __type;


    public ParseFile(String name, String url) {
        __type = "File";
        this.name = name;
        this.url = url;
    }
}
