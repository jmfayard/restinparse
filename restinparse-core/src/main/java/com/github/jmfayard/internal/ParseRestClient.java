package com.github.jmfayard.internal;


import com.github.jmfayard.model.CloudResult;
import com.github.jmfayard.model.ParseFile;
import com.github.jmfayard.model.ParseResultSchemas;
import com.github.jmfayard.model.Something;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Response;
import rx.Observable;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ParseRestClient {
    final ParseRestApi parseRestApi;


    public ParseRestClient(ParseRestApi parseRestApi) {
        this.parseRestApi = parseRestApi;
    }


    public Observable<Response<CloudResult<List<Something>>>> callCloudFunctionReturningList(String functionName, Map<String, Object> body) {
        return parseRestApi.callCloudFunctionReturningListOfObjects(functionName, body);
    }


    public Observable<Response<CloudResult<Something>>> callCloudFunctionReturningMap(String functionName, Map<String, Object> emptyBody) {
        return parseRestApi.callCloudFunctionReturningMap(functionName, emptyBody);
    }


    public Observable<Response<CloudResult<String>>> callCloudFunctionReturningString(String functionName, Map<String, Object> emptyBody) {
        return parseRestApi.callCloudFunctionReturningString(functionName, emptyBody);
    }


    public Observable<Response<Something>> deleteObject(String className, String objectId) {
        return parseRestApi.deleteObject(className, objectId);
    }


    public Observable<Response<Something>> fetchUser(String id) {
        return parseRestApi.fetchUser(id);
    }


    public Observable<Response<Something>> launchBackgroundJob(String name, Map<String, Object> emptyBody) {
        return parseRestApi.launchBackgroundJob(name, emptyBody);
    }


    public Observable<Response<Something>> login(String username, String password) {
        return parseRestApi.login(username, password);
    }


    public Observable<Response<Something>> logout() {
        return parseRestApi.logout();
    }


    public Observable<Response<Something>> me(String sessionToken) {
        return parseRestApi.me(sessionToken);
    }


    public Observable<Response<ParseResultSchemas>> schemas() {
        return parseRestApi.schemas();
    }


    public Observable<Response<Something>> storeObject(String className, Map<String, Object> object) {
        return parseRestApi.storeObject(className, object);
    }


    public Observable<Response<Something>> storeUser(Map<String, Object> object) {
        return parseRestApi.storeUser(object);
    }


    public Observable<Response<Something>> updateObject(String className, String objectId, Map<String, Object> object) {
        return parseRestApi.updateObject(className, objectId, object);
    }


    public Observable<Response<ParseFile>> uploadFile(String filename, RequestBody file) {
        return parseRestApi.uploadFile(filename, file);
    }


    public Observable<File> downloadFile(@NotNull ParseFile parseFile, @NotNull File destination) {
        return null;
    }
}
