package app.TextEditor.Model;
import app.*;
import com.google.gson.*;
import java.lang.reflect.*;
import com.google.gson.reflect.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.Response;
import static org.asynchttpclient.Dsl.asyncHttpClient;
import java.util.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import app.TextEditor.*;


/**
 * Created by anthonycarrasco on 12/3/16.
 */
public class TextEditorAPIDataManager {
    @FXML TextArea textArea;


    public String retrieveText(){
        return "connectionLogic";

    };

    public void sendText(TextFile file)
    {
        Gson JsonSerializer = new Gson();
        JsonSerializer.toJson(file);
        String json = JsonSerializer.toString();
        System.out.println(json);
    };

    public Boolean attemptToSaveFile(TextFile file)
    {
        Gson jsonSeralizer = new Gson();
        String jSONItem = jsonSeralizer.toJson(file);
        //System.out.println(jSONItem);
        //System.out.println("Setting permissions!!!");
        Boolean success = false;
        try (AsyncHttpClient asyncHttpClient = asyncHttpClient()) {
            Future<Boolean> results = asyncHttpClient
                    .preparePost("http://lowCost-env.kydrpmgvhm.us-east-1.elasticbeanstalk.com/save_file.php")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .setBody(jSONItem)
                    .execute(
                            new AsyncCompletionHandler<Boolean>() {
                                @Override
                                public Boolean onCompleted(Response response) throws Exception {
                                    // Was the login successful
                                    //System.out.println(response.getResponseBody());
                                    Type type = new TypeToken<Map<String, String>>() {
                                    }.getType();
                                    Map<String, String> jsonMap = jsonSeralizer.fromJson(response.getResponseBody(), type);
                                    //System.out.println("Results of Permssions set: "+jsonMap);
                                    if (!jsonMap.get("success").equals(1)) {
                                        System.out.println("File save was successful.");
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

    public Boolean attemptToMakeTextFileAvailable(TextFile file)
    {
        Gson jsonSeralizer = new Gson();
        String jSONItem = "{\"textfile_id\" : \""+ file.textfile_id +"\"}";
        //System.out.println(jSONItem);
        //System.out.println("Setting permissions!!!");
        Boolean success = false;
        try (AsyncHttpClient asyncHttpClient = asyncHttpClient()) {
            Future<Boolean> results = asyncHttpClient
                    .preparePost("http://lowCost-env.kydrpmgvhm.us-east-1.elasticbeanstalk.com/make_file_available.php")
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
                                        System.out.println("File has been made available.");
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
