package com.github.jmfayard.model;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.util.HashMap;
import java.util.Map;

public class Something {
    HashMap<String, Object> map = new HashMap<String, Object>();

    public Map<String, Object> map() {
        return map;
    }

    @Override
    public String toString() {
        return map.toString();
    }

    public static class Adapter {

        @FromJson
        public Something fromJson(Map map) {
            Something something = new Something();
            something.map().putAll(map);
            return something;
        }

        @ToJson
        public Map toJson(Something something) {
            return something.map();
        }
    }
}
