package app.TextEditor.Model;

import app.TextEditor.Controller.TextEditorController;
import app.TextEditor.*;

import java.util.concurrent.CompletableFuture;

/**
 * Created by anthonycarrasco on 12/3/16.
 */
public class TextEditorModel {
    public TextEditorAPIDataManager textEditorAPIDataManager;
    public TextEditorController textEditorController;


    public void saveFile(TextFile file)
    {
        CompletableFuture<Boolean> f1 = new CompletableFuture();
        f1.supplyAsync(() -> {return textEditorAPIDataManager.attemptToSaveFile(file);});//.whenCompleteAsync((a, error) -> {if (a){System.out.println("Save was successful!");}});
    }

    public void makeTextFileAvailable(TextFile file)
    {
        CompletableFuture<Boolean> f1 = new CompletableFuture();
        f1.supplyAsync(() -> {return textEditorAPIDataManager.attemptToMakeTextFileAvailable(file);}).whenCompleteAsync(
                (a, error) ->
                {
                    System.out.println("Make available was successful!");
                });
    }

}
