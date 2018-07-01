package com.github.jmfayard.internal;


import com.github.jmfayard.model.*;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Response;
import rx.Observable;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ParseRestClient {
    final ParseRestApi parseRestApi;


    public ParseRestClient(ParseRestApi parseRestApi) {
        this.parseRestApi = parseRestApi;
    }


    public Observable<Response<CloudResult<List<ParseMap>>>> callCloudFunctionReturningList(String functionName, Map<String, Object> body) {
        return parseRestApi.callCloudFunctionReturningListOfObjects(functionName, body);
    }


    public Observable<Response<CloudResult<ParseMap>>> callCloudFunctionReturningMap(String functionName, Map<String, Object> emptyBody) {
        return parseRestApi.callCloudFunctionReturningMap(functionName, emptyBody);
    }


    public Observable<Response<CloudResult<String>>> callCloudFunctionReturningString(String functionName, Map<String, Object> emptyBody) {
        return parseRestApi.callCloudFunctionReturningString(functionName, emptyBody);
    }


    public Observable<Response<ParseMap>> deleteObject(String className, String objectId) {
        return parseRestApi.deleteObject(className, objectId);
    }


    public Observable<Response<ParseMap>> fetchUser(String id) {
        return parseRestApi.fetchUser(id);
    }


    public Observable<Response<ParseMap>> launchBackgroundJob(String name, Map<String, Object> emptyBody) {
        return parseRestApi.launchBackgroundJob(name, emptyBody);
    }


    public Observable<Response<ParseMap>> login(String username, String password) {
        return parseRestApi.login(username, password);
    }


    public Observable<Response<ParseMap>> logout() {
        return parseRestApi.logout();
    }


    public Observable<Response<ParseMap>> me(String sessionToken) {
        return parseRestApi.me(sessionToken);
    }


    public Observable<Response<ParseResultSchemas>> schemas() {
        return parseRestApi.schemas();
    }


    public Observable<Response<ParseMap>> storeObject(String className, Map<String, Object> object) {
        return parseRestApi.storeObject(className, object);
    }


    public Observable<Response<ParseMap>> storeUser(Map<String, Object> object) {
        return parseRestApi.storeUser(object);
    }


    public Observable<Response<ParseMap>> updateObject(String className, String objectId, Map<String, Object> object) {
        return parseRestApi.updateObject(className, objectId, object);
    }


    public Observable<Response<ParseFile>> uploadFile(String filename, RequestBody file) {
        return parseRestApi.uploadFile(filename, file);
    }


    public Observable<File> downloadFile(@NotNull ParseFile parseFile, @NotNull File destination) {
        return null;
    }

    public Observable<Response<List<ParseBatchResponse>>> batchExecutor(@NotNull Observable<ParseBatchRequest> stream, long delay, TimeUnit timeUnit, int itemsPerBatch) {
        return stream
                .buffer(Math.min(50, itemsPerBatch))
                .concatMap(it -> Observable.just(it).delay(delay, timeUnit))
                .flatMap(list -> parseRestApi.batchRequests(new ParseBatch(list)));
    }
}
