package app.Login.Model;

import app.Login.*;
import com.google.gson.*;
import java.lang.reflect.*;
import com.google.gson.reflect.*;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import static org.asynchttpclient.Dsl.asyncHttpClient;
import java.util.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Angel on 11/26/16.
 */
public class LoginAPIDataManager {
    public LoginResults attemptLogin(LoginItem loginItem) {

        Gson jsonSeralizer = new Gson();
        String loginJSON = jsonSeralizer.toJson(loginItem);
        LoginResults failed = new LoginResults();
        failed.success = false;

        try (AsyncHttpClient asyncHttpClient = asyncHttpClient()) {
            Future<LoginResults> success = asyncHttpClient
                    .preparePost("http://lowCost-env.kydrpmgvhm.us-east-1.elasticbeanstalk.com/login.php")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .setBody(loginJSON)
                    .execute(
                            new AsyncCompletionHandler<LoginResults>() {
                                @Override
                                public LoginResults onCompleted(Response response) throws Exception {
                                    // Was the login successful
                                    Type type = new TypeToken<Map<String, String>>() {
                                    }.getType();
                                    Map<String, String> jsonMap = jsonSeralizer.fromJson(response.getResponseBody(), type);
                                    System.out.println("JSONMAP "+jsonMap);
                                    if (!jsonMap.get("user_id").isEmpty()) {
                                        LoginResults results = new LoginResults();
                                        results.success = true;
                                        results.user_id = jsonMap.get("user_id");
                                        return results;
                                    } else {
                                        System.out.println(jsonMap.get("error_message"));
                                        LoginResults results = new LoginResults();
                                        results.success = false;
                                        return results;
                                    }
                                }
                            }
                    )
                    .toCompletableFuture();
            return success.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
            return failed;
        } catch (ExecutionException e) {
            //e.printStackTrace();
            return failed;
        } catch (IOException e) {
            e.printStackTrace();
            return failed;
        }

    }

}
