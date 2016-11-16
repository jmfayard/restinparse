package com.github.jmfayard;

import com.github.jmfayard.internal.Settings;
import org.jetbrains.annotations.NotNull;

public class RestInParse {

    public static String GREETING = "Hello World!";

    public static ParseClient masterClient() {
        return Settings.masterClient();
    }

    public static ParseClient userClient(String parseSessionToken) {
        return Settings.userClient(parseSessionToken);
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
}
