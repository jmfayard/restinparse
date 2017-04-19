package com.github.jmfayard.model;

import com.github.jmfayard.ParseColumn;
import com.github.jmfayard.internal.ParseRestClientFactory;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;

import java.util.*;

public class ParseMapBuilder<T extends ParseColumn> {

    private final Map<String, Object> map = new HashMap<>();
    private final String className;

    public ParseMapBuilder(String className) {

        this.className = className;
    }

    public ParseMapBuilder(String className, Map<String, Object> from) {
        this.className = className;
        map.putAll(from);
    }

    @NotNull
    public ParseMapBuilder<T> set(@NotNull T column, boolean value) {
        map.put(column.toString(), value);
        return this;
    }

    @NotNull
    public ParseMapBuilder<T> set(@NotNull T column, int value) {
        map.put(column.toString(), value);
        return this;
    }

    @NotNull
    public ParseMapBuilder<T> set(@NotNull T column, String value) {
        map.put(column.toString(), value);
        return this;
    }

    @NotNull
    public ParseMapBuilder<T> set(@NotNull T column, List value) {
        map.put(column.toString(), value);
        return this;
    }

    @NotNull
    public ParseMapBuilder<T> set(@NotNull T column, ParsePointer value) {
        map.put(column.toString(), value);
        return this;
    }

    @NotNull
    public ParseMapBuilder<T> set(@NotNull T column, Map<String, Object> value) {
        map.put(column.toString(), value);
        return this;
    }

    @NotNull
    public ParseMapBuilder<T> set(@NotNull T column, Date date) {
        HashMap<String, String> dateMap = new HashMap<>();
        dateMap.put("__type", "Date");
        String dateString = new Instant(date.getTime()).toDateTime(DateTimeZone.UTC).toString();
        dateMap.put("iso", dateString);
        map.put(column.toString(), dateMap);
        return this;
    }

    @NotNull
    public ParseMapBuilder<T>  set(@NotNull T column, ParseFile parseFile) {
        map.put(column.toString(), parseFile.map());
        return this;
    }

    @NotNull
    public ParseMap build() {
        return new ParseMap(map);
    }


    @NotNull
    public ParseBatchRequest batchUpdate(String id) {
        String path = String.format("%sclasses/%s/%s",
                ParseRestClientFactory.basePath(), className, id);
        return new ParseBatchRequest("PUT", map, path);
    }

    public ParseBatchRequest batchCreate() {
        String path = String.format("%sclasses/%s",
                ParseRestClientFactory.basePath(), className);
        return new ParseBatchRequest("POST", map, path);
    }
}
