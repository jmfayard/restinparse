package com.github.jmfayard.internal;

import com.github.jmfayard.ParseColumn;
import com.github.jmfayard.ParseObject;
import com.github.jmfayard.model.ParseError;
import com.github.jmfayard.model.QueryResults;
import com.github.jmfayard.model.ParseMap;
import org.jetbrains.annotations.NotNull;
import retrofit2.Response;
import rx.Observable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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


    public Observable<ParseObject<T>> find(Map<String, String> params) {
        ParseRestApi api = ParseRestClientFactory.masterClient();
        Observable<Response<QueryResults>> response = api.query(className, params);
        return response.flatMap(r -> {
            List<ParseObject<T>> objects = new ArrayList<ParseObject<T>>();
            if (r.isSuccessful()) {
                List<ParseMap> found = r.body().results;
                if (found == null) {
                    found = Collections.emptyList();
                }
                for (ParseMap parseMap : found) {
                    ParseObject<T> o = new ParseObject<T>(parseMap);
                    objects.add(o);
                }
            }
            return Observable.from(objects);
        });
    }

    @NotNull
    public Observable<ParseObject<T>> update(String objectId, Map<String, Object> map) {
        ParseRestApi api = ParseRestClientFactory.masterClient();
        Observable<Response<ParseMap>> response = api.updateObject(className, objectId, map);
        Observable<ParseObject<T>> result = response
                .map(ParseTableInternal::assertSuccessfull)
                .map(ParseObject::new);
        return result;

    }

    public <T extends ParseColumn> Observable<ParseObject<T>> create(Map<String, Object> map) {
        ParseRestApi api = Settings.currentClient().parseRestApi;
        Observable<Response<ParseMap>> response = api.storeObject(className, map);
        Observable<ParseObject<T>> result = response
                .map(ParseTableInternal::assertSuccessfull)
                .map(ParseObject::new);
        return result;

    }

    public Observable<ParseMap> delete(String objectId) {
        ParseRestApi api = Settings.currentClient().parseRestApi;
        Observable<Response<ParseMap>> response = api.deleteObject(className, objectId);
        Observable<ParseMap> result = response.map(ParseTableInternal::assertSuccessfull);
        return result;

    }
}
