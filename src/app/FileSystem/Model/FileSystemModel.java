package app.FileSystem.Model;

import app.FileSystem.Controller.FileSystemController;
import app.FileSystem.FileItem;
import java.util.concurrent.CompletableFuture;
import javafx.collections.ObservableList;
/**
 * Created by Angel on 11/23/16.
 */
public class FileSystemModel {
    public FileSystemController controller;
    public FileSystemAPIDataManager fileSystemAPIDataManager;

    public void getFiles(String user_id)
    {
        CompletableFuture<FileItem[]> f2 = new CompletableFuture();
        f2.supplyAsync(() -> { return fileSystemAPIDataManager.attemptToRetrieveFiles(user_id);}).whenCompleteAsync((a, error) -> {controller.returnFiles(a);});

    }
}
