package com.github.jmfayard;

import com.github.jmfayard.internal.ParseTableInternal;
import com.github.jmfayard.model.ParseMapBuilder;
import com.github.jmfayard.model.ParsePointer;
import com.github.jmfayard.model.ParseMap;
import org.jetbrains.annotations.NotNull;
import rx.Observable;

import java.util.HashMap;
import java.util.Map;

public class ParseTable<T extends ParseColumn> extends ParseTableInternal<T> {

    public String className() {
        return className;
    }

    private final String className;

    public ParseTable(String className) {
        super(className);
        this.className = className;
    }

    public ParseQuery.Builder<T> query() {
        return new ParseQuery.Builder<>(className);
    }

    public Observable<ParseObject<T>> findById(String id) {
        ParseQuery<T> query = query().withId(id).build();
        return query.find();
    }

    public Observable<ParseObject<T>> findById(ParsePointer id) {
        return findById(id.objectId);
    }

    public ParsePointer pointer(String objectId) {
        return new ParsePointer(className, objectId);
    }

    public Observable<ParseObject<T>> update(String objectId, Map<T, Object> updates) {
        return super.update(objectId, updates);
    }

    public Observable<ParseObject<T>> create(Map<T, Object> attributes) {
        return super.createObject(rowMapOf(attributes));
    }

    public Observable<ParseObject<T>> create(ParseMap attributes) {
        return super.createObject(rowMapOf(attributes));
    }



    @NotNull
    public Observable<ParseObject<T>> update(ParsePointer ptr, Map<T, Object> updates) {
        return this.update(ptr.objectId, updates);
    }

    public @NotNull Observable<ParseMap> delete(@NotNull String objectId) {
        return super.delete(objectId);
    }

    public @NotNull ParseMap delete(@NotNull ParsePointer pointer) {
        return this.delete(pointer);
    }

    public ParseMapBuilder<T> createMap() {
        return new ParseMapBuilder();
    }

    public ParseMapBuilder<T> createMap(Map<T, Object> from) {
        return new ParseMapBuilder(from);
    }


}
