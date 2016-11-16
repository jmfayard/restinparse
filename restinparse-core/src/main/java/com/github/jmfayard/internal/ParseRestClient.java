package com.github.jmfayard.internal;

import com.github.jmfayard.model.Something;
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

import static com.github.jmfayard.internal.Settings.*;

public class ParseRestClient {
    private static final String JSON = "application/json";
    private static ParseRestApi loggedinClient;
    private static ParseRestApi masterClient;
    static Moshi moshi;
    static Interceptor clientInterceptor;


    public static void setHostedParseRestUrl(String url) {
        PARSE_URL = url;
    }

    public static ParseRestApi masterClient() {

        if (masterClient != null) {
            return masterClient;
        }

        Interceptor headersInterceptor = okHttpInterceptor(Authentification.MASTERKEY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(headersInterceptor)
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build();


        Retrofit retrofit = new Retrofit.Builder().baseUrl(PARSE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi()))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

        masterClient = retrofit.create(ParseRestApi.class);

        return masterClient;
    }

    public static ParseRestApi loggedinClient() {
        if (PARSE_APPLICATIONID == null) {
            throw new RuntimeException("ParseRestClient.initialize(applicationId, masterKey) was not called");
        }
        if (Strings.isNullOrEmpty(PARSE_SESSION_TOKEN)) {
            throw new RuntimeException("You need a valid session token to do logged in calls");
        }
        if (loggedinClient != null) {
            return loggedinClient;
        }

        Interceptor headersInterceptor = okHttpInterceptor(Authentification.SESSIONTOKEN);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(headersInterceptor)
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(PARSE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi()))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

        loggedinClient = retrofit.create(ParseRestApi.class);

        return loggedinClient;
    }

    private static @NotNull Interceptor okHttpInterceptor(final Authentification authentification) {
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
                    .add(new Something.Adapter())
                    .build();
        }
        return moshi;
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
