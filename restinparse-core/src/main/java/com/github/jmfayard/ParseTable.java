package com.github.jmfayard;

import com.github.jmfayard.internal.ParseTableInternal;
import com.github.jmfayard.model.ParsePointer;
import org.jetbrains.annotations.NotNull;
import rx.Observable;

import java.util.HashMap;
import java.util.Map;

public class ParseTable<T extends ParseColumn> {

    private final String className;

    public ParseTable(String className) {
        this.className = className;
    }

    public ParseQuery.Builder<T> query() {
        return new ParseQuery.Builder<>(className);
    }

    public Observable<ParseObject<T>> findById(String id) {
        ParseQuery<T> query = query().withId(id).build();
        return new ParseTableInternal<T>(query.className).find(query.params);
    }

    public ParsePointer pointer(String objectId) {
        return new ParsePointer(className, objectId);
    }

    public Observable<ParseObject<T>> update(String objectId, Map<T, Object> updates) {
        ParseTableInternal<T> internal = new ParseTableInternal<>(this.className);
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry entry : updates.entrySet()) {
            map.put(entry.getKey().toString(), entry.getValue());
        }
        return internal.update(objectId, map);
    }

    @NotNull
    public Observable<ParseObject<T>> update(ParsePointer ptr, Map<T, Object> updates) {
        return this.update(ptr.objectId, updates);
    }
}
