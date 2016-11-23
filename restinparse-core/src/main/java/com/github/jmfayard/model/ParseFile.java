package com.github.jmfayard.model;

import java.util.HashMap;
import java.util.Map;

public class ParseFile {
    public String name;
    public String url;
    public String __type;


    public ParseFile(String name, String url) {
        this.__type = "File";
        this.name = name;
        this.url = url;

    }

    public ParseFile() {
        this.__type = "File";
    }

    @Override
    public String toString() {
        return "ParseFile{" +
                "__type='" + __type + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public Map<String, String> map() {
        HashMap<String, String> result = new HashMap<>();
        result.put("__type", "File");
        result.put("name", name);
        result.put("url", url);
        return result;
    }
}
