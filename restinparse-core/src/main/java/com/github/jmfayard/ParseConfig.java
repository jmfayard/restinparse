package com.github.jmfayard;

import com.github.jmfayard.internal.Settings;
import org.jetbrains.annotations.NotNull;

public class ParseConfig {
    private String applicationId;
    private String restKey;
    private String masterKey;
    private String restApiUrl;
    private RestInParse.LogLevel logLevel = RestInParse.LogLevel.INFO;

    public @NotNull ParseConfig logLevel(RestInParse.@NotNull LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public @NotNull ParseConfig applicationId(@NotNull String applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    public @NotNull ParseConfig restKey(@NotNull String restKey) {
        this.restKey = restKey;
        return this;
    }

    public @NotNull ParseConfig masterKey(@NotNull String masterKey) {
        this.masterKey = masterKey;
        return this;
    }

    public @NotNull ParseConfig restApiUrlOfParseDotCom() {
        this.restApiUrl = "https://api.parse.com/1/";
        return this;
    }

    public @NotNull ParseConfig restApiUrl(@NotNull String restApiUrl) {
        this.restApiUrl = restApiUrl;
        return this;
    }


    public void apply() {
        Settings.initialize(applicationId, restKey, masterKey, restApiUrl, logLevel);
    }

    private void checkAll() {
        //DO: verify something
    }

    public enum SessionType {
        ANONYMOUS,
        SESSIONTOKEN,
        MASTERKEY
    }
}
