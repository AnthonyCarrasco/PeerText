package app.Login.Model;

import app.Login.*;
import com.google.gson.*;
import java.lang.reflect.*;
import com.google.gson.reflect.*;
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

/**
 * Created by Angel on 11/26/16.
 */
public class LoginAPIDataManager {
    public Boolean attemptLogin(LoginItem loginItem) {

        Gson jsonSeralizer = new Gson();
        String loginJSON = jsonSeralizer.toJson(loginItem);

        try (AsyncHttpClient asyncHttpClient = asyncHttpClient()) {
            Future<Boolean> success = asyncHttpClient
                    .preparePost("http://lowCost-env.kydrpmgvhm.us-east-1.elasticbeanstalk.com/login.php")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .setBody(loginJSON)
                    .execute(
                            new AsyncCompletionHandler<Boolean>() {
                                @Override
                                public Boolean onCompleted(Response response) throws Exception {
                                    // Was the login successful
                                    Type type = new TypeToken<Map<String, String>>() {
                                    }.getType();
                                    Map<String, String> jsonMap = jsonSeralizer.fromJson(response.getResponseBody(), type);
                                    System.out.println("JSONMAP "+jsonMap);
                                    if (!jsonMap.get("user_id").isEmpty()) {
                                        return true;
                                    } else {
                                        System.out.println(jsonMap.get("error_message"));
                                        return false;
                                    }
                                }
                            }
                    )
                    .toCompletableFuture();
            return success.get();

        } catch (InterruptedException e) {
            //e.printStackTrace();
            return false;
        } catch (ExecutionException e) {
            //e.printStackTrace();
            return false;
        } catch (IOException e) {
            //e.printStackTrace();
            return false;
        }

    }

}
