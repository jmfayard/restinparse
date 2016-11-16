package com.github.jmfayard.internal;

import com.github.jmfayard.RestInParse;

public class Settings {
    static final String JSON = "application/json";
    static String PARSE_URL = "https://api.parse.com/1/";
    static String PARSE_RESTKEY = null;
    static String PARSE_APPLICATIONID = null;
    static String PARSE_MASTERKEY = null;
    static String PARSE_SESSION_TOKEN = null;
    static RestInParse.LogLevel LOG_LEVEL;


    public static void initialize(String applicationId, String restKey, String masterKey, String restApiUrl, RestInParse.LogLevel logLevel) {
        // checks
        PARSE_APPLICATIONID = applicationId;
        PARSE_RESTKEY = restKey;
        PARSE_MASTERKEY = masterKey;
        PARSE_URL = restApiUrl;
        LOG_LEVEL = logLevel;
    }

    public static ParseRestApi masterRestApi() {
        check();
        return ParseRestClientFactory.masterClient();
    }

    private static void check() {
        if (PARSE_APPLICATIONID == null) {
            throw new RuntimeException("new RestInParse.Initializer()...initialize() first");
        }
    }

    public static ParseRestApi userRestApi(String parseSessionToken) {
        check();
        ParseRestClientFactory.setSessionToken(parseSessionToken);
        return ParseRestClientFactory.loggedinClient();
    }

    public static ParseRestClient masterClient() {
        return new ParseRestClient(masterRestApi());
    }

    public static ParseRestClient userClient(String parseSessionToken) {
        return new ParseRestClient(userRestApi(parseSessionToken));
    }

    public static ParseRestClient anonymousClient() {
        return new ParseRestClient(ParseRestClientFactory.anonymousClient());
    }
}
