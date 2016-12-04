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
public class TextAPIDataManager {
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
}
