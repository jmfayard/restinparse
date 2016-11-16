package com.github.jmfayard;

import com.github.jmfayard.internal.ParseRestApi;
import com.github.jmfayard.internal.Settings;
import org.jetbrains.annotations.NotNull;

public class RestInParse {

    public static String GREETING = "Hello World!";

    public static ParseRestApi masterClient() {
        return Settings.masterClient();
    }

    public static ParseRestApi loggedinClient(String parseSessionToken) {
        return Settings.loggedinClient(parseSessionToken);
    }


    public static class Initializer {
        private String applicationId;
        private String restKey;
        private String masterKey;
        private String restApiUrl;

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
            Settings.initialize(applicationId, restKey, masterKey, restApiUrl);
        }

        private void checkAll() {
            //DO: verify something
        }
    }
}
