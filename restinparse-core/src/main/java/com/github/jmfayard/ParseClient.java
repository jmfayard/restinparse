package com.github.jmfayard;

import com.github.jmfayard.model.ParseResultSchemas;
import com.github.jmfayard.model.CloudResult;
import com.github.jmfayard.model.ParseFile;
import com.github.jmfayard.model.Something;
import okhttp3.RequestBody;
import retrofit2.Response;
import rx.Observable;

import java.util.List;
import java.util.Map;

public interface ParseClient {
    Observable<Response<CloudResult<List<Something>>>> callCloudFunctionReturningList(String functionName, Map<String, Object> body);

    Observable<Response<CloudResult<Something>>> callCloudFunctionReturningMap(String functionName, Map<String, Object> emptyBody);

    Observable<Response<CloudResult<String>>> callCloudFunctionReturningString(String functionName, Map<String, Object> emptyBody);

    Observable<Response<Something>> deleteObject(String className, String objectId);

    Observable<Response<Something>> fetchUser(String id);

    Observable<Response<Something>> launchBackgroundJob(String name, Map<String, Object> emptyBody);

    Observable<Response<Something>> login(String username, String password);

    Observable<Response<Something>> logout();

    Observable<Response<Something>> me(String sessionToken);

    Observable<Response<ParseResultSchemas>> schemas();

    Observable<Response<Something>> storeObject(String className, Map<String, Object> object);

    Observable<Response<Something>> storeUser(Map<String, Object> object);

    Observable<Response<Something>> updateObject(String className, String objectId, Map<String, Object> object);

    Observable<Response<ParseFile>> uploadFile(String filename, RequestBody file);
}
