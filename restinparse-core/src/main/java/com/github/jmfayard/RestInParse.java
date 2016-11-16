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
import java.io.IOException;
import java.nio.file.Files;

public class RestInParse {

    public static String GREETING = "Hello World!";

    public static ParseConfig configure() {
        return new ParseConfig();
    }


    static ParseRestClient currentClient() {
        return Settings.currentClient();
    }
    static ParseRestClient masterClient() {
        return Settings.masterClient();
    }

    static ParseRestClient userClient() {
        return Settings.userClient();
    }

    static ParseRestClient anonymousclient() {
        return Settings.anonymousClient();
    }


    @NotNull
    public static Observable<ParseResultSchemas> schemas() {
        return masterClient().schemas().map(RestInParse::assertSuccessfull);
    }

    @NotNull
    public static Observable<ParseObject<ParseUser>> checkLogin(String username, String password) {
        return anonymousclient().login(username, password)
                .map(RestInParse::assertSuccessfull)
                .map(it -> (ParseObject<ParseUser>) ParseObject.from(it));
    }

    @NotNull
    public static Observable<ParseObject<ParseUser>> checkSessionToken(String parseSessionToken) {
        return anonymousclient().me(parseSessionToken)
                .map(RestInParse::assertSuccessfull)
                .map(it -> (ParseObject<ParseUser>) ParseObject.from(it));
    }

    @NotNull
    public static Observable<ParseFile> uploadFile(File file) {
        String contentType = null;
        try {
            contentType = Files.probeContentType(file.toPath());
        } catch (IOException e) {

        }
        contentType = "application/octet-stream";
        RequestBody body = RequestBody.create(MediaType.parse(contentType), file);
        return currentClient().uploadFile(file.getName(), body).map(RestInParse::assertSuccessfull);
    }

//    @NotNull
//    public static Observable<File> downloadFile(@NotNull ParseFile parseFile, @NotNull File destination) {
//        return currentClient().downloadFile(parseFile, destination);
//    }

    public enum LogLevel {NONE, INFO, DEBUG}


    private static  <T> T assertSuccessfull(Response<T> response) {
        if (!response.isSuccessful()) {
            throw new ParseError(response.code(), response.message());
        }
        return response.body();
    }
}
