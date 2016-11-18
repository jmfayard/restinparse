package com.github.jmfayard.internal;

import com.github.jmfayard.model.*;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Response;
import retrofit2.http.*;
import rx.Observable;

import java.util.List;
import java.util.Map;

interface ParseRestApi {
    /**
     * USERS
     **/
    @GET("login")
    Observable<Response<ParseMap>> login(@Query("username") String username, @Query("password") String password);

    @POST("logout")
    Observable<Response<ParseMap>> logout();


    @GET("users/me")
    Observable<Response<ParseMap>> me(@Header("X-Parse-Session-Token") String sessionToken);

    @GET("classes/{className}/{id}")
    Observable<Response<ParseMap>> fetch(@Path("className") String className, @Path("id") String id);

    @GET("users/{id}")
    Observable<Response<ParseMap>> fetchUser(@Path("id") String id);


    @GET("classes/{className}")
    Observable<Response<QueryResults>> query(@Path("className") String className, @QueryMap Map<String, String> queryOptions);

    /**
     * Cloud Code
     **/
    @POST("functions/{name}")
    Observable<Response<CloudResult<ParseMap>>> callCloudFunctionReturningMap(@NotNull @Path("name") String name, @NotNull @Body Map<String, Object> emptyBody);

    @POST("functions/{name}")
    Observable<Response<CloudResult<List<ParseMap>>>> callCloudFunctionReturningListOfObjects(
            @NotNull @Path("name") String name, @NotNull @Body Map<String, Object> emptyBody);

    @POST("functions/{name}")
    Observable<Response<CloudResult<String>>> callCloudFunctionReturningString(
            @NotNull @Path("name") String name, @NotNull @Body Map<String, Object> emptyBody);

    @POST("jobs/{name}")
    Observable<Response<ParseMap>> launchBackgroundJob(@NotNull @Path("name") String name, @NotNull @Body Map<String, Object> emptyBody);

    @GET("schemas")
    Observable<Response<ParseResultSchemas>> schemas();


    @POST("files/{name}")
    Observable<Response<ParseFile>> uploadFile(@Path("name") String filename, @Body RequestBody file);

    @POST("classes/{class}")
    Observable<Response<ParseMap>> storeObject(@Path("class") String className, @Body Map<String, Object> object);

    @POST("users")
    Observable<Response<ParseMap>> storeUser(@Body Map<String, Object> object);

    @PUT("classes/{class}/{id}")
    Observable<Response<ParseMap>> updateObject(@Path("class") String className, @Path("id") String objectId, @Body Map<String, Object> object);

    @DELETE("classes/{class}/{id}")
    Observable<Response<ParseMap>> deleteObject(@Path(("class")) String className, @Path("id") String objectId);

}
