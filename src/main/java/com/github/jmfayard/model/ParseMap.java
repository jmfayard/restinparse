package com.github.jmfayard.model;

import com.github.jmfayard.internal.ParseRestClientFactory;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.util.HashMap;
import java.util.Map;

public class ParseMap {
    Map<String, Object> map = new HashMap<String, Object>();

    public ParseMap() {
    }

    public ParseMap(Map<String, Object> map) {
        this.map.putAll(map);
    }

    public Map<String, Object> map() {
        return map;
    }

    public String json() {
        return ParseRestClientFactory.mapAdapter().toJson(this);
    }

    @Override
    public String toString() {
        return json();
    }

    public static class Adapter {

        @FromJson
        public ParseMap fromJson(Map map) {
            ParseMap parseMap = new ParseMap();
            parseMap.map().putAll(map);
            return parseMap;
        }

        @ToJson
        public Map toJson(ParseMap parseMap) {
            return parseMap.map();
        }
    }
}
