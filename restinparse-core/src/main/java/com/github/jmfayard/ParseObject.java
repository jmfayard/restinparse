package com.github.jmfayard;

import com.github.jmfayard.model.ParseFile;
import com.github.jmfayard.model.ParsePointer;
import com.github.jmfayard.model.ParseUser;
import com.github.jmfayard.model.ParseMap;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;

import java.util.*;

public class ParseObject<T extends ParseColumn> {

    private Map<String, Object> map = new HashMap<String, Object>();

    public ParseObject(ParseMap parseMap) {
        this.map = parseMap.map();
    }

    public ParseObject(Map<String, Object> map) {
        this.map = map;
    }



    public Map<String, Object> map() {
        return map;
    }



    public String id() {
        return ((String) map.get("objectId"));
    }

    public int getInt(T column) {
        Object o = getOrDefault(column, 0);
        if (o instanceof Double) {
            return (int) ((Double) o).intValue();
        } else {
            return (int) o;
        }
    }

    public double getDouble(T column) {
        return ((Double) getOrDefault(column, 0.0));
    }

    public String getSring(T column) {
        return ((String) getOrDefault(column, ""));
    }

    public boolean getBoolean(T column) {
        return ((Boolean) getOrDefault(column, false));
    }

    public <R> List<R> getList(T column) {
        return ((List<R>) getOrDefault(column, Collections.emptyList()));
    }


    public @Nullable ParseFile getFile(T column) {
        Map<String, Object> map = getAsMap(column, "name", "url");
        if (map == null) {
            return null;
        } else {
            return new ParseFile(((String) map.get("name")), ((String) map.get("url")));
        }
    }

    public @Nullable Date getDate(T column) {
        Map<String, Object> map = getAsMap(column, "__type", "iso");
        if (map == null || hasNotType(map, "Date")) {
            return null;
        } else {
            String iso = (String) map.get("iso");
            return DateTime.parse(iso).toDate();
        }

    }


    public @Nullable ParsePointer getPointer(T column) {
        Map<String, Object> map = getAsMap(column, "__type", "objectId", "className");
        if (map == null || hasNotType(map, "Pointer")) {
            return null;
        } else {
            String objectId = (String) map.get("objectId");
            String className = (String) map.get("className");
            return new ParsePointer(className, objectId);
        }
    }


    public <R extends ParseColumn> @Nullable ParseObject<R> getParseObject(T column) {
        Map<String, Object> map = getAsMap(column, "__type");
        if (map == null) return null;
        else if (hasNotType(map, "Object")) return null;
        else return new ParseObject<>(map);
    }


    public @Nullable ParseObject<ParseUser> getParseUser(T column) {
        return getParseObject(column);
    }


    @Override
    public String toString() {
        return map.toString();
    }

    private Object getOrDefault(T key, Object def) {
        if (map.containsKey(key.toString())) {
            return map.get(key.toString());
        } else {
            return def;
        }
    }

    private Map<String, Object> getAsMap(T key, String... keys) {
        Object oMap = getOrDefault(key, null);
        if (!(oMap instanceof Map)) {
            return null;
        } else {
            for (String k : keys) {
                if (!((Map) oMap).containsKey(k)) {
                    return null;
                }
            }
            return (Map<String, Object>) oMap;
        }
    }

    private boolean hasNotType(Map<String, Object> map, String type) {
        return !(map.containsKey("__type") && type.equals(map.get("__type")));
    }


}
