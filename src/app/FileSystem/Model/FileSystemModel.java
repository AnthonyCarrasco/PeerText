package app.FileSystem.Model;

import app.FileSystem.*;
import app.FileSystem.Controller.DeleteItem;
import app.FileSystem.Controller.FileSystemController;

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

    public void getPermissions(RequestPermissionsItem pItem)
    {
        CompletableFuture<FileItem[]> f1 = new CompletableFuture();
        f1.supplyAsync(() -> { return fileSystemAPIDataManager.attemptToRetrievePermissions(pItem);}).whenCompleteAsync((a, error) -> {controller.returnPermissions(a);});
    }

    public void setPermission(RequestPChangeItem pChangeItem)
    {
        CompletableFuture<Boolean> f1 = new CompletableFuture();
        f1.supplyAsync(() -> {return fileSystemAPIDataManager.attemptToSetPermissions(pChangeItem);}).whenCompleteAsync((a, error) -> {System.out.println(a);});
    }

    public void createFile(CreateFileItem cItem)
    {
        CompletableFuture<Boolean> f1 = new CompletableFuture();
        f1.supplyAsync(() -> {return fileSystemAPIDataManager.attemptToCreateFile(cItem);}).whenCompleteAsync((a, error) -> {controller.fileCreated(a);});
    }

    public void deleteFile(DeleteItem item)
    {
        CompletableFuture<Boolean> f1 = new CompletableFuture();
        f1.supplyAsync(() -> {return fileSystemAPIDataManager.attemptToDeleteFile(item);}).whenCompleteAsync((a, error) -> {controller.fileDeleted(a);});
    }

    public void getFileAccess(FileAccessRequestItem aItem)
    {
        CompletableFuture<Boolean> f1 = new CompletableFuture();
        f1.supplyAsync(() -> {return fileSystemAPIDataManager.attemptToGetFileAccess(aItem);}).whenCompleteAsync((a, error) -> {controller.returnFileAccessResults(a);});
    }

}
