package com.github.jmfayard;

import com.github.jmfayard.internal.ParseRestClient;
import com.github.jmfayard.internal.Settings;
import com.github.jmfayard.model.*;
import org.jetbrains.annotations.NotNull;
import retrofit2.Response;
import rx.Observable;

public class RestInParse {

    public static String GREETING = "Hello World!";

    static ParseRestClient masterClient() {
        return Settings.masterClient();
    }

    static ParseRestClient userClient(String parseSessionToken) {
        return Settings.userClient(parseSessionToken);
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

    public enum LogLevel {NONE, INFO, DEBUG}


    public static class Initializer {
        private String applicationId;
        private String restKey;
        private String masterKey;
        private String restApiUrl;
        private LogLevel logLevel = LogLevel.INFO;

        public @NotNull Initializer logLevel(@NotNull LogLevel logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public @NotNull Initializer applicationId(@NotNull String applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public @NotNull Initializer restKey(@NotNull String restKey) {
            this.restKey = restKey;
            return this;
        }

        public @NotNull Initializer masterKey(@NotNull String masterKey) {
            this.masterKey = masterKey;
            return this;
        }

        public @NotNull Initializer restApiParseDotCom() {
            this.restApiUrl = "http://api.parse.com/v1";
            return this;
        }

        public @NotNull Initializer restApiUrl(@NotNull String restApiUrl) {
            this.restApiUrl = restApiUrl;
            return this;
        }

        @NotNull
        public void initialize() {
            Settings.initialize(applicationId, restKey, masterKey, restApiUrl, logLevel);
        }

        private void checkAll() {
            //DO: verify something
        }
    }


    private static  <T> T assertSuccessfull(Response<T> response) {
        if (!response.isSuccessful()) {
            throw new ParseError(response.code(), response.message());
        }
        return response.body();
    }
}
