package app.FileSystem.Model;

import app.FileSystem.*;
import app.FileSystem.Controller.DeleteItem;
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
                                    //System.out.println(response.getResponseBody());
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

    public Boolean attemptToSetPermissions(RequestPChangeItem pItem)
    {
        Gson jsonSeralizer = new Gson();
        String jSONItem = jsonSeralizer.toJson(pItem);
        //System.out.println(jSONItem);
        //System.out.println("Setting permissions!!!");
        Boolean success = false;
        try (AsyncHttpClient asyncHttpClient = asyncHttpClient()) {
            Future<Boolean> results = asyncHttpClient
                    .preparePost("http://lowCost-env.kydrpmgvhm.us-east-1.elasticbeanstalk.com/set_permissions.php")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .setBody(jSONItem)
                    .execute(
                            new AsyncCompletionHandler<Boolean>() {
                                @Override
                                public Boolean onCompleted(Response response) throws Exception {
                                    // Was the login successful
                                    System.out.println(response.getResponseBody());
                                    Type type = new TypeToken<Map<String, String>>() {
                                    }.getType();
                                    Map<String, String> jsonMap = jsonSeralizer.fromJson(response.getResponseBody(), type);
                                    //System.out.println("Results of Permssions set: "+jsonMap);
                                    if (!jsonMap.get("success").equals(1)) {
                                        System.out.println("Permissions set was successful.");
                                        return true;
                                    } else {
                                        System.out.println(jsonMap.get("error_message"));
                                        return false;

                                    }
                                }
                            }
                    )
                    .toCompletableFuture();
            return results.get();
        }
         catch (IOException e) {
            e.printStackTrace();
             return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean attemptToCreateFile(CreateFileItem cItem)
    {
        Gson jsonSeralizer = new Gson();
        String jSONItem = jsonSeralizer.toJson(cItem);
        //System.out.println(jSONItem);
        //System.out.println("Setting permissions!!!");
        Boolean success = false;
        try (AsyncHttpClient asyncHttpClient = asyncHttpClient()) {
            Future<Boolean> results = asyncHttpClient
                    .preparePost("http://lowCost-env.kydrpmgvhm.us-east-1.elasticbeanstalk.com/create_file.php")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .setBody(jSONItem)
                    .execute(
                            new AsyncCompletionHandler<Boolean>() {
                                @Override
                                public Boolean onCompleted(Response response) throws Exception {
                                    // Was the login successful
                                    System.out.println(response.getResponseBody());
                                    Type type = new TypeToken<Map<String, String>>() {
                                    }.getType();
                                    Map<String, String> jsonMap = jsonSeralizer.fromJson(response.getResponseBody(), type);
                                    //System.out.println("Results of Permssions set: "+jsonMap);
                                    if (!jsonMap.get("success").equals(1)) {
                                        System.out.println("File creation was successful.");
                                        return true;
                                    } else {
                                        System.out.println(jsonMap.get("error_message"));
                                        return false;

                                    }
                                }
                            }
                    )
                    .toCompletableFuture();
            return results.get();
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean attemptToDeleteFile(DeleteItem item)
    {
        Gson jsonSeralizer = new Gson();
        String jSONItem = jsonSeralizer.toJson(item);
        //System.out.println(jSONItem);
        //System.out.println("Setting permissions!!!");
        Boolean success = false;
        try (AsyncHttpClient asyncHttpClient = asyncHttpClient()) {
            Future<Boolean> results = asyncHttpClient
                    .preparePost("http://lowCost-env.kydrpmgvhm.us-east-1.elasticbeanstalk.com/delete_file.php")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .setBody(jSONItem)
                    .execute(
                            new AsyncCompletionHandler<Boolean>() {
                                @Override
                                public Boolean onCompleted(Response response) throws Exception {
                                    // Was the login successful
                                    System.out.println(response.getResponseBody());
                                    Type type = new TypeToken<Map<String, String>>() {
                                    }.getType();
                                    Map<String, String> jsonMap = jsonSeralizer.fromJson(response.getResponseBody(), type);
                                    //System.out.println("Results of Permssions set: "+jsonMap);
                                    if (!jsonMap.get("success").equals(1)) {
                                        System.out.println("File was deletion successful.");
                                        return true;
                                    } else {
                                        System.out.println(jsonMap.get("error_message"));
                                        return false;

                                    }
                                }
                            }
                    )
                    .toCompletableFuture();
            return results.get();
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

}
