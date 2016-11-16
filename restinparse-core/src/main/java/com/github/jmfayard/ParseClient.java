package com.github.jmfayard;

import com.github.jmfayard.model.QueryResults;
import com.github.jmfayard.model.Something;
import retrofit2.Response;
import retrofit2.http.*;
import rx.Observable;

import java.util.Map;

public interface ParseClient {
    /**
     * USERS
     **/
    @GET("login")
    Observable<Response<Something>> login(@Query("username") String username, @Query("password") String password);

    @POST("logout")
    Observable<Response<Something>> logout();


    @GET("users/me")
    Observable<Response<Something>> me(@Header("X-Parse-Session-Token") String sessionToken);

    @GET("classes/{className}/{id}")
    Observable<Response<Something>> fetch(@Path("className") String className, @Path("id") String id);

    @GET("users/{id}")
    Observable<Response<Something>> fetchUser(@Path("id") String id);


    @GET("classes/{className}")
    Observable<Response<QueryResults>> query(@Path("className") String className, @QueryMap Map<String, String> queryOptions);

//    /** Cloud Code **/
//    @POST("functions/{name}")
//    Call<CloudResultMap> callCloudFunctionReturningMap(@NotNull @Path("name") String name, @NotNull @Body Map<String, Object> emptyBody);
//
//    @POST("functions/{name}")
//    Call<CloudResultListOfObjects> callCloudFunctionReturningListOfObjects(
//            @NotNull @Path("name") String name, @NotNull @Body Map<String, Object> emptyBody);
//
//    @POST("functions/{name}")
//    Call<CloudResultString> callCloudFunctionReturningScalar(
//            @NotNull @Path("name") String name, @NotNull @Body Map<String, Object> emptyBody);
//
//    @POST("jobs/{name}")
//    Call<JSONObject> launchBackgroundJob(@NotNull @Path("name") String name, @NotNull @Body Map<String, Object> emptyBody);
//
//    @GET("schemas")
//    Observable<Response<ParseResultSchemas>> schemas();
//
//
//    @POST("files/{name}")
//    Call<ParseUploadFile> uploadFile(@Path("name") String filename, @Body RequestBody file);
//
//    @POST("classes/{class}")
//    Call<Map<String, Object>> storeObject(@Path("class") String className, @Body Map<String, Object> object);
//
//    @POST("users")
//    Call<Map<String, Object>> storeUser(@Body Map<String, Object> object);
//
//    @PUT("classes/{class}/{id}")
//    Call<Map<String, Object>> updateObject(@Path("class") String className, @Path("id") String objectId, @Body Map<String, Object> object);
//
//    @DELETE("classes/{class}/{id}")
//    Call<Map<String, Object>> deleteObject(@Path(("class")) String className, @Path("id") String objectId);

}
