package com.github.jmfayard;

import com.squareup.moshi.Moshi;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

import java.io.IOException;

public class ParseRestClient {
//    private static final String JSON = "application/json";
//    private static String PARSE_URL = "https://api.parse.com/1/";
//    private static String PARSE_SESSION_TOKEN = null;
//    private static String PARSE_RESTKEY = null;
//    private static String PARSE_APPLICATIONID = null;
//    private static String PARSE_MASTERKEY = null;
//    private static ParseRestApi masterClient;
//    private static Moshi moshi;
//    private static Interceptor clientInterceptor;
//    private static ParseRestApi loggedinClient;
//
//    public static void setHostedParseRestUrl(String url) {
//        PARSE_URL = url;
//    }
//
//    public static ParseRestApi masterClient() {
//        if (PARSE_APPLICATIONID == null) {
//            throw new RuntimeException("ParseRestClient.initialize(applicationId, masterKey) was not called");
//        }
//        if (masterClient != null) {
//            return masterClient;
//        }
//
//        Interceptor headersInterceptor = okHttpInterceptor(Authentification.MASTERKEY);
//
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(headersInterceptor)
//            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
//            .build();
//
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(PARSE_URL)
//            .client(client)
//            .addConverterFactory(MoshiConverterFactory.create(moshi()))
//            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//            .build();
//
//        masterClient = retrofit.create(ParseRestApi.class);
//
//        return masterClient;
//    }
//
//    public static ParseRestApi loggedinClient() {
//        if (PARSE_APPLICATIONID == null) {
//            throw new RuntimeException("ParseRestClient.initialize(applicationId, masterKey) was not called");
//        }
//        if (PARSE_SESSION_TOKEN == null || "".equals(PARSE_SESSION_TOKEN)) {
//            throw new RuntimeException("You are not logged in");
//        }
//        if (loggedinClient != null) {
//            return loggedinClient;
//        }
//
//        Interceptor headersInterceptor = okHttpInterceptor(Authentification.SESSIONTOKEN);
//
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(headersInterceptor)
//            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
//            .build();
//
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(PARSE_URL)
//            .client(client)
//            .addConverterFactory(MoshiConverterFactory.create(moshi()))
//            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//            .build();
//
//        loggedinClient = retrofit.create(ParseRestApi.class);
//
//        return loggedinClient;
//    }
//
//    private static @NotNull Interceptor okHttpInterceptor(final Authentification authentification) {
//        return new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request original = chain.request();
//
//                Request.Builder requestBuilder = original.newBuilder()
//                    .header("Accept", "application/json")
//                    .header("Content-Type", JSON)
//                    .header("X-Parse-Application-Id", PARSE_APPLICATIONID)
//                    .method(original.method(), original.body());
//
//                switch (authentification) {
//                    case ANONYMOUS:
//                        requestBuilder.header("X-Parse-REST-API-Key", PARSE_RESTKEY);
//                        break;
//                    case SESSIONTOKEN:
//                        requestBuilder.header("X-Parse-REST-API-Key", PARSE_RESTKEY);
//                        requestBuilder.header("X-Parse-Session-Token", PARSE_SESSION_TOKEN);
//                        break;
//                    case MASTERKEY:
//                        requestBuilder.header("X-Parse-Master-Key", PARSE_MASTERKEY);
//                        break;
//                }
//
//                // Customize or return the response
//                return chain.proceed(requestBuilder.build());
//            }
//        };
//    }
//
//    public static @NotNull Moshi moshi() {
//        if (moshi == null) {
//            //moshi = new Moshi.Builder().add(new ParseQuery.Adapter()).initialize();
//            moshi = new Moshi.Builder().build();
//        }
//        return moshi;
//    }
//
//    public static void initialize(@NotNull String applicationId, @NotNull String masterKey, @NotNull String restKey) {
//        PARSE_APPLICATIONID = applicationId;
//        PARSE_MASTERKEY = masterKey;
//        PARSE_RESTKEY = restKey;
//    }
//
//    public static void setSessionToken(@NotNull String sessionToken) {
//        PARSE_SESSION_TOKEN = sessionToken;
//        clientInterceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request original = chain.request();
//
//                // Customize the request
//                Request request = original.newBuilder()
//                    .header("Accept", "application/json")
//                    .header("Content-Type", JSON)
//                    .header("X-Parse-Application-Id", PARSE_APPLICATIONID)
//                    .header("X-Parse-REST-API-Key", PARSE_RESTKEY)
//                    .header("X-Parse-Session-Token", PARSE_SESSION_TOKEN)
//                    .method(original.method(), original.body())
//                    .build();
//
//                // Customize or return the response
//                return chain.proceed(request);
//            }
//        };
//    }
//
//    enum Authentification {
//        ANONYMOUS,
//        SESSIONTOKEN,
//        MASTERKEY
//    }
}
