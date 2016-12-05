package app.FileSystem.Model;

import app.FileSystem.FileItem;
import app.FileSystem.PermissionItem;
import app.FileSystem.RequestPermissionsItem;
import app.Login.LoginResults;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;
import java.util.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.asynchttpclient.Dsl.asyncHttpClient;

/**
 * Created by Angel on 12/4/16.
 */
public class FileSystemAPIDataManager {
    public FileItem[] attemptToRetrieveFiles(String user_id)
    {

        Gson jsonSeralizer = new Gson();
        String userIDJSON = "{\"user_id\" : " + "\"" + user_id + "\"}";
        System.out.println(userIDJSON);
        FileItem[]  failed = null;
        try (AsyncHttpClient asyncHttpClient = asyncHttpClient()) {
            Future<FileItem[] > fList = asyncHttpClient
                    .preparePost("http://lowCost-env.kydrpmgvhm.us-east-1.elasticbeanstalk.com/request_files.php")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .setBody(userIDJSON)
                    .execute(
                            new AsyncCompletionHandler<FileItem[] >() {
                                @Override
                                public FileItem[]  onCompleted(Response response) throws Exception {
                                    // Was the login successful
                                    FileItem[] filesArray = jsonSeralizer.fromJson(response.getResponseBody(), FileItem[].class);
                                    if (filesArray.length != 0) {
                                        for(FileItem file: filesArray)
                                        {
                                            file.title = file.title.trim();
                                        }
                                        return filesArray;
                                    } else {
                                        FileItem[] results = new FileItem[0];
                                        return results;
                                    }
                                }
                            }
                    )
                    .toCompletableFuture();
            return fList.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
            return failed;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return failed;
        } catch (IOException e) {
            e.printStackTrace();
            return failed;
        }
    }

    public PermissionItem[] attemptToRetrievePermissions(RequestPermissionsItem pItem)
    {
        Gson jsonSeralizer = new Gson();
        String jSONItem = jsonSeralizer.toJson(pItem);
        System.out.println(jSONItem);
        PermissionItem[]  failed = null;
        try (AsyncHttpClient asyncHttpClient = asyncHttpClient()) {
            Future<PermissionItem[] > fList = asyncHttpClient
                    .preparePost("http://lowCost-env.kydrpmgvhm.us-east-1.elasticbeanstalk.com/request_permissions.php")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .setBody(jSONItem)
                    .execute(
                            new AsyncCompletionHandler<PermissionItem[] >() {
                                @Override
                                public PermissionItem[]  onCompleted(Response response) throws Exception {
                                    // Was the login successful
                                    System.out.println(response.getResponseBody());
                                    PermissionItem[] permissionsArray = jsonSeralizer.fromJson(response.getResponseBody(), PermissionItem[].class);
                                    if (permissionsArray.length != 0) {
                                        for(PermissionItem pItems: permissionsArray)
                                        {
                                            pItems.username = pItems.username.trim();
                                        }
                                        return permissionsArray;
                                    } else {
                                        PermissionItem[] results = new PermissionItem[0];
                                        return results;
                                    }
                                }
                            }
                    )
                    .toCompletableFuture();
            return fList.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
            return failed;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return failed;
        } catch (IOException e) {
            e.printStackTrace();
            return failed;
        }
    }
}
