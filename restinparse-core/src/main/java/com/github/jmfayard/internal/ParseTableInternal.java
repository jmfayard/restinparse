package com.github.jmfayard.internal;

import com.github.jmfayard.ParseColumn;
import com.github.jmfayard.ParseObject;
import com.github.jmfayard.ParseQuery;
import com.github.jmfayard.model.ParseError;
import com.github.jmfayard.model.QueryResults;
import com.github.jmfayard.model.ParseMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.Response;
import rx.Observable;
import rx.subjects.BehaviorSubject;

import java.io.IOException;
import java.util.*;

public class ParseTableInternal<T extends ParseColumn> {

    private final String className;

    public ParseTableInternal(String className) {
        this.className = className;
    }

    private static <T> T assertSuccessfull(Response<T> response) {
        if (!response.isSuccessful()) {
            throw errorFrom(response);
        }
        return response.body();
    }

    private static  ParseError errorFrom(Response response) {
        try {
            String errorBody = response.errorBody().string();
            ParseError copy = ParseRestClientFactory.parseErrorAdapter().fromJson(errorBody);
            return new ParseError(copy.code, copy.error);
        } catch (IOException e) {
            return new  ParseError(response.code(), response.message());
        }
    }


    protected Observable<ParseObject<T>> find(Map<String, String> params) {
        ParseRestApi api = ParseRestClientFactory.masterClient();
        Observable<Response<QueryResults>> response = api.query(className, params);
        return response.flatMap((r) -> flatMapResponse(r, null));
    }

    protected  Observable<ParseObject<T>> findAll(ParseQuery.Builder<DefaultParseColumn> queryBuilder) {
        ParseRestApi api = ParseRestClientFactory.masterClient();

        BehaviorSubject<Date> relay = BehaviorSubject.create();
        relay.onNext(new Date());
        return relay.flatMap(date -> {
            ParseQuery<DefaultParseColumn> queryOlders = queryBuilder.olderThan(DefaultParseColumn.createdAt, date).build();
            Observable<Response<QueryResults>> response = api.query(className, queryOlders.params);
            return response;
        }).flatMap((r) -> flatMapResponse(r, relay));

    }


    protected final int QUERY_ALL_LIMIT = 100;

    protected Observable<ParseObject<T>> flatMapResponse(Response<QueryResults> r, @Nullable BehaviorSubject<Date> relay) {
        if (!r.isSuccessful()) {
            if (relay!= null) { relay.onCompleted();}
            throw errorFrom(r);
        }
        List<ParseObject<T>> objects = new ArrayList<>();
        List<ParseMap> found = r.body().results;
        if (found == null) {
            found = Collections.emptyList();
        }
        for (ParseMap parseMap : found) {
            ParseObject<T> o = new ParseObject<T>(parseMap);
            objects.add(o);
        }
        if (relay != null) {
            if (found.size() == QUERY_ALL_LIMIT) {
                ParseObject<DefaultParseColumn> object = new ParseObject<>(found.get(QUERY_ALL_LIMIT - 1));
                Date createdAt = object.createdAt();
                relay.onNext(createdAt);
            } else {
                relay.onCompleted();
            }
        }
        return Observable.from(objects);
    }

    @NotNull
    protected Observable<ParseObject<T>> update(String objectId, Map<T, Object> updates) {
        ParseRestApi api = ParseRestClientFactory.masterClient();
        Observable<Response<ParseMap>> response = api.updateObject(className, objectId, rowMapOf(updates));
        return response
                .map(ParseTableInternal::assertSuccessfull)
                .map(ParseObject::new);

    }

    @NotNull
    protected Map<String, Object> rowMapOf(Map<T, Object> updates) {
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry entry : updates.entrySet()) {
            map.put(entry.getKey().toString(), entry.getValue());
        }
        return map;
    }

    @NotNull
    protected Map<String, Object> rowMapOf(ParseMap updates) {
        Map<String, Object> map = new HashMap<>();
        map.putAll(updates.map());
        return map;
    }


    protected  Observable<ParseObject<T>> createObject(Map<String, Object> map ) {
        ParseRestApi api = Settings.currentClient().parseRestApi;
        Observable<Response<ParseMap>> response = api.storeObject(className, map);
        Observable<ParseObject<T>> result = response
                .map(ParseTableInternal::assertSuccessfull)
                .map(ParseObject::new);
        return result;

    }

    protected Observable<ParseMap> delete(String objectId) {
        ParseRestApi api = Settings.currentClient().parseRestApi;
        Observable<Response<ParseMap>> response = api.deleteObject(className, objectId);
        Observable<ParseMap> result = response.map(ParseTableInternal::assertSuccessfull);
        return result;

    }


}
