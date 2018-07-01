package com.github.jmfayard.internal;

import com.github.jmfayard.ParseConfig;
import com.github.jmfayard.model.ParseBatchRequest;
import com.github.jmfayard.model.ParseBatchResponse;
import com.github.jmfayard.model.ParseError;
import com.github.jmfayard.model.ParseMap;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Rfc3339DateJsonAdapter;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

import java.io.IOException;
import java.util.Date;

import static com.github.jmfayard.internal.Settings.*;

public class ParseRestClientFactory {
    private static final String JSON = "application/json";
    static Moshi moshi;
    static Interceptor clientInterceptor;
    private static ParseRestApi anonymousClient;
    private static ParseRestApi loggedinClient;
    private static ParseRestApi masterClient;

    public static String basePath() {
        return HttpUrl.parse(PARSE_URL).encodedPath();
    }
    public static void setHostedParseRestUrl(String url) {
        PARSE_URL = url;
    }


    public static ParseRestApi anonymousClient() {
        if (anonymousClient == null) {
            Retrofit retrofit = retrofit(ok(okHttpInterceptor(ParseConfig.SessionType.ANONYMOUS)));
            anonymousClient = retrofit.create(ParseRestApi.class);
        }
        return anonymousClient;
    }

    public static ParseRestApi masterClient() {
        if (masterClient == null) {
            Retrofit retrofit = retrofit(ok(okHttpInterceptor(ParseConfig.SessionType.MASTERKEY)));
            masterClient = retrofit.create(ParseRestApi.class);
        }
        return masterClient;
    }

    public static ParseRestApi loggedinClient() {
        if (PARSE_APPLICATIONID == null) {
            throw new RuntimeException("ParseRestClient.initialize(applicationId, masterKey) was not called");
        }
        if (Strings.isNullOrEmpty(PARSE_SESSION_TOKEN)) {
            throw new RuntimeException("You need a valid session token to do logged in calls");
        }
        if (loggedinClient == null) {
            Retrofit retrofit = retrofit(ok(okHttpInterceptor(ParseConfig.SessionType.SESSIONTOKEN)));
            loggedinClient = retrofit.create(ParseRestApi.class);
        }
        return loggedinClient;
    }

    private static Retrofit retrofit(OkHttpClient okHttpClient) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(PARSE_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;

    }

    private static OkHttpClient ok(Interceptor interceptor) {
        HttpLoggingInterceptor.Level level = null;
        switch (Settings.LOG_LEVEL) {
            case NONE:
                level = HttpLoggingInterceptor.Level.NONE;
                break;
            case INFO:
                level = HttpLoggingInterceptor.Level.BASIC;
                break;
            case DEBUG:
                level = HttpLoggingInterceptor.Level.BODY;
                break;
            case FULL:
                level = HttpLoggingInterceptor.Level.BODY;
                break;
        }

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(level))
                .build();
    }


    private static @NotNull Interceptor okHttpInterceptor(final ParseConfig.SessionType authentification) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("Content-Type", JSON)
                        .header("X-Parse-Application-Id", PARSE_APPLICATIONID)
                        .method(original.method(), original.body());

                switch (authentification) {
                    case ANONYMOUS:
                        requestBuilder.header("X-Parse-REST-API-Key", PARSE_RESTKEY);
                        break;
                    case SESSIONTOKEN:
                        requestBuilder.header("X-Parse-REST-API-Key", PARSE_RESTKEY);
                        requestBuilder.header("X-Parse-Session-Token", PARSE_SESSION_TOKEN);
                        break;
                    case MASTERKEY:
                        requestBuilder.header("X-Parse-Master-Key", PARSE_MASTERKEY);
                        break;
                }

                // Customize or return the response
                return chain.proceed(requestBuilder.build());
            }
        };
    }

    public static @NotNull Moshi moshi() {
        if (moshi == null) {
            moshi = new Moshi.Builder()
                    .add(new ParseMap.Adapter())
                    .add(Date.class, new Rfc3339DateJsonAdapter())
                    .build();
        }
        return moshi;
    }

    public static JsonAdapter<ParseMap> mapAdapter() {
        return moshi().adapter(ParseMap.class);
    }

    public static JsonAdapter<ParseBatchResponse> parseBatchResponseAdapter() {
        return moshi().adapter(ParseBatchResponse.class);
    }

    public static JsonAdapter<ParseBatchRequest> parseBatchAdapter() {
        return moshi().adapter(ParseBatchRequest.class);
    }

    public static JsonAdapter<ParseError> parseErrorAdapter() {
        return moshi().adapter(ParseError.class);
    }


    static void setSessionToken(@NotNull String sessionToken) {
        PARSE_SESSION_TOKEN = sessionToken;
        clientInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Customize the request
                Request request = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("Content-Type", JSON)
                        .header("X-Parse-Application-Id", PARSE_APPLICATIONID)
                        .header("X-Parse-REST-API-Key", PARSE_RESTKEY)
                        .header("X-Parse-Session-Token", PARSE_SESSION_TOKEN)
                        .method(original.method(), original.body())
                        .build();

                // Customize or return the response
                return chain.proceed(request);
            }
        };
    }


}
