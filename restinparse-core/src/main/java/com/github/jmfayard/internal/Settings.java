package com.github.jmfayard.internal;

public class Settings {
    static final String JSON = "application/json";
    static String PARSE_URL = "https://api.parse.com/1/";
    static String PARSE_RESTKEY = null;
    static String PARSE_APPLICATIONID = null;
    static String PARSE_MASTERKEY = null;
    static String PARSE_SESSION_TOKEN = null;


    public static void initialize(String applicationId, String restKey, String masterKey, String restApiUrl) {
        // checks
        PARSE_APPLICATIONID = applicationId;
        PARSE_RESTKEY = restKey;
        PARSE_MASTERKEY = masterKey;
        PARSE_URL = restApiUrl;
    }

    public static ParseRestApi masterClient() {
        check();
        return ParseRestClient.masterClient();
    }

    private static void check() {
        if (PARSE_APPLICATIONID == null) {
            throw new RuntimeException("new RestInParse.Initializer()...initialize() first");
        }
    }

    public static ParseRestApi loggedinClient(String parseSessionToken) {
        check();
        ParseRestClient.setSessionToken(parseSessionToken);
        return ParseRestClient.loggedinClient();
    }
}
