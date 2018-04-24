package com.github.jmfayard;

import com.github.jmfayard.internal.ParseRestClient;
import com.github.jmfayard.internal.Settings;
import com.github.jmfayard.model.*;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Response;
import rx.Observable;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RestInParse {

    public static String GREETING = "Hello World!";

    public static ParseConfig configure() {
        return new ParseConfig();
    }

    public static void startMasterSession() {
        Settings.setCurrentSession(ParseConfig.SessionType.MASTERKEY, null);
    }

    public static void startUserSession(String parseSessionToken) {
        Settings.setCurrentSession(ParseConfig.SessionType.SESSIONTOKEN, parseSessionToken);
    }

    public static void startAnonymousSession() {
        Settings.setCurrentSession(ParseConfig.SessionType.ANONYMOUS, null);
    }

    private static ParseRestClient currentClient() {
        return Settings.currentClient();
    }


    @NotNull
    public static Observable<ParseResultSchemas> schemas() {
        return Settings.masterClient().schemas().map(RestInParse::assertSuccessfull);
    }

    @NotNull
    public static Observable<ParseObject<ParseUser>> checkLogin(String username, String password) {
        return Settings.anonymousClient().login(username, password)
                .map(RestInParse::assertSuccessfull)
                .map(ParseObject::new);
    }

    @NotNull
    public static Observable<ParseObject<ParseUser>> checkSessionToken(String parseSessionToken) {
        return Settings.anonymousClient().me(parseSessionToken)
                .map(RestInParse::assertSuccessfull)
                .map(ParseObject::new);
    }

    @NotNull
    public static Observable<ParseFile> uploadFile(File file, String contentType) {
        RequestBody body = RequestBody.create(MediaType.parse(contentType), file);
        return currentClient().uploadFile(file.getName(), body).map(RestInParse::assertSuccessfull);
    }

    @NotNull
    public static Observable<ParseFile> uploadFile(String filename, RequestBody requestBody) {
        return currentClient().uploadFile(filename, requestBody).map(RestInParse::assertSuccessfull);
    }

    public static Observable<String> callCloudFunctionReturningString(@NotNull String functionName, @NotNull Map<String, Object> parameters) {
        Observable<Response<CloudResult<String>>> responseObservable = currentClient().callCloudFunctionReturningString(functionName, parameters);
        return responseObservable.map(RestInParse::assertSuccessfull)
                .map(r -> r.result);
    }

    public static <T> Observable<List<T>> callCloudFunctionReturningList(@NotNull String functionName, @NotNull Map<String, Object> parameters) {
        Observable<Response<CloudResult<List<ParseMap>>>> responseObservable = currentClient().callCloudFunctionReturningList(functionName, parameters);
        return responseObservable
                .map(RestInParse::assertSuccessfull)
                .map(r -> {
                    if (r == null || r.result == null) return Collections.emptyList();
                    ArrayList<T> result = new ArrayList<>();
                    for (ParseMap parseMap : r.result) {
                        result.add(((T) parseMap.map()));
                    }
                    return result;
                });
    }

//    @NotNull
//    public static Observable<File> downloadFile(@NotNull ParseFile parseFile, @NotNull File destination) {
//        return currentClient().downloadFile(parseFile, destination);
//    }

    private static <T> T assertSuccessfull(Response<T> response) {
        if (!response.isSuccessful()) {
            ParseError error = new ParseError(response.code(), response.message());
            System.err.println(error);
            System.err.println(response.errorBody());
            throw error;
        }
        return response.body();
    }

    public static Observable<List<ParseBatchResponse>> batchExecutor(@NotNull Observable<ParseBatchRequest> stream) {
        return Settings.masterClient().batchExecutor(stream)
                .map(RestInParse::assertSuccessfull);

    }


    public enum LogLevel {NONE, INFO, DEBUG, FULL}
}
