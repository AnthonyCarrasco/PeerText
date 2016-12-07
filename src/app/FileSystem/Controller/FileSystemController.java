package app.FileSystem.Controller;

import app.FileSystem.*;
import app.TextEditor.*;
import app.FileSystem.Model.FileSystemAPIDataManager;
import app.FileSystem.Model.FileSystemModel;
import app.TextEditor.Controller.TextEditorController;
import app.TextEditor.Model.TextEditorAPIDataManager;
import app.TextEditor.Model.TextEditorModel;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by Angel on 11/23/16.
 */
public class FileSystemController extends Parent{
    public Stage primaryStage;
    private String user_id;
    @FXML private Button btnCreateFile;
    @FXML private Button btnDeleteFile;
    @FXML private Button btnEditAccess;
    @FXML private Button btnEditViewAccess;
    @FXML private Button btnRefresh;
    @FXML private TableView<FileItem> fileTable;
    @FXML private TableColumn<FileItem, String> fileNameCol;
    @FXML private TableView<PermissionItem> pTable;
    @FXML private TableColumn<PermissionItem, String> userCol;
    @FXML private TableColumn<PermissionItem, String> accessCol;
    private FileItem selectedOwnedFile;
    private FileItem selectedFile;
    ObservableList<FileItem> files;
    ObservableList<PermissionItem> pItems;
    ObservableList<String> permissions = FXCollections.observableArrayList(
            "e", "v", "n");

    public FileSystemModel model;

    public void loadFiles(String user_id)
    {

        //System.out.println("Table: " + fileTable);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                pTable.setEditable(true);
                accessCol.setEditable(true);
                userCol.setEditable(false);
                fileNameCol.setCellValueFactory(
                        new PropertyValueFactory<FileItem, String>("title"));
                fileTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        //System.out.println("Selected Item: " + newSelection.title);
                        if (newSelection.owner_id.equals(user_id))
                        {
                            RequestPermissionsItem requestPItem = new RequestPermissionsItem(user_id, newSelection.textfile_id);
                            selectedOwnedFile = newSelection;
                            model.getPermissions(requestPItem);
                        }
                        else
                        {
                            pTable.getItems().clear();
                        }

                        selectedFile = newSelection;

                    }
                });

                userCol.setCellValueFactory(
                        new PropertyValueFactory<PermissionItem, String>("username"));

                accessCol.setCellFactory(
                        ChoiceBoxTableCell.forTableColumn(new DefaultStringConverter(), permissions)
                );

                accessCol.setCellValueFactory(
                        new PropertyValueFactory<PermissionItem, String>("type"));

                accessCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<PermissionItem, String>>() {

                    @Override
                    public void handle(TableColumn.CellEditEvent<PermissionItem, String> t) {
                        ((PermissionItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).type = t.getNewValue();
                        RequestPChangeItem modifyedPItem = new RequestPChangeItem(selectedOwnedFile.textfile_id, ((PermissionItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).user_id, t.getNewValue());
                        model.setPermission(modifyedPItem);
                    }

                });

                fileTable.setRowFactory( tv -> {
                    TableRow<FileItem> row = new TableRow<>();

                    row.setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                            FileItem rowData = row.getItem();
                            FileAccessRequestItem aItem = new FileAccessRequestItem(rowData.textfile_id, user_id);
                            model.getFileAccess(aItem);

                        }
                    });
                    return row ;
                });
            }

        });

        this.user_id = user_id;
        model.getFiles(user_id);
    }

    public void returnFiles(FileItem[] data)
    {
        files = FXCollections.observableArrayList(data);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //selectedFile = null;
                fileTable.setItems(files);

            }
        });
    }

    public void returnPermissions(PermissionItem[] data)
    {
        pItems = FXCollections.observableArrayList(data);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                pTable.setItems(pItems);

            }
        });
    }


    @FXML protected  void handleCreateButtonAction(ActionEvent event)
    {
        //System.out.println("Create file pressed");



        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Creating new file");
                dialog.setHeaderText("Please enter a filename");

                Optional<String> result = dialog.showAndWait();
                String fileName= "none.";

                if (result.isPresent() && !result.equals("")) {

                    fileName = result.get();
                    if (fileName.equals(""))
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error!");
                        alert.setHeaderText("File creation failed!");
                        alert.setContentText("Invalid filename.");
                        alert.showAndWait();
                    }

                    else
                    {
                        //System.out.println("Filename: "+ fileName);
                        CreateFileItem cFileItem = new CreateFileItem(fileName, user_id);
                        model.createFile(cFileItem);
                    }

                }
            }
        });
    }

    @FXML protected void handleRefreshButtonAction(ActionEvent event)
    {
        model.getFiles(user_id);
    }


    @FXML protected void handleDeleteButtonAction(ActionEvent event)
    {

        if(selectedFile != null && selectedFile.owner_id.equals(user_id))
        {
            //System.out.println("Deleting file: " + selectedFile.title);
            //System.out.println("Owner ID: " + selectedFile.owner_id + " My user id: "+ user_id);
            DeleteItem dItem = new DeleteItem(selectedFile.textfile_id, selectedFile.owner_id);
            model.deleteFile(dItem);

        }
        else
        {

            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                   if (selectedFile != null &&!selectedFile.owner_id.equals(user_id))
                   {
                       Alert alert = new Alert(Alert.AlertType.ERROR);
                       alert.setTitle("Error!");
                       alert.setHeaderText("Deletion failed!");
                       alert.setContentText("You don't own this file.");
                       alert.showAndWait();
                   }
                   else
                   {
                       Alert alert = new Alert(Alert.AlertType.ERROR);
                       alert.setTitle("Error!");
                       alert.setHeaderText("Deletion failed!");
                       alert.setContentText("Please select file to delete.");
                       alert.showAndWait();
                   }

                }
            });
        }
    }
    public void fileCreated(Boolean results)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (results == true)
                {
                    model.getFiles(user_id);
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("File creation failed!");
                    alert.setContentText("Server error.");
                    alert.showAndWait();
                }
            }
        });
    }

    public void fileDeleted(Boolean results)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (results == true)
                {
                    model.getFiles(user_id);
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("File deletion failed!");
                    alert.setContentText("Server error.");
                    alert.showAndWait();
                }
            }
        });
    }

    public void returnFileAccessResults(FileAccessItem aItem)
    {
         if(aItem != null && !aItem.type.equals("n") && aItem.available.equals("t"))
         {


             try{
                 FXMLLoader teLoader = new FXMLLoader();
                 System.out.println("textEditorView: " + teLoader);
                 Parent textEditorView = teLoader.load(getClass().getResource("/app/TextEditor/View/TextEditor.fxml").openStream());
                 System.out.println("textEditorView: " + textEditorView);
                 TextEditorController tController = (TextEditorController) teLoader.getController();
                 tController.model = new TextEditorModel();
                 tController.model.textEditorController = tController;
                 tController.model.textEditorAPIDataManager = new TextEditorAPIDataManager();
                 tController.user_id = user_id;
                 tController.primaryStage = primaryStage;
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            primaryStage.setTitle("TextEditor");
                            primaryStage.setScene(new Scene(textEditorView));
                            primaryStage.show();
                            tController.prepareTextEditor(aItem);

                        }
                     });
                 }
                 catch (IOException e)
                 {
                     System.out.println("Thrown!!!!!!!!!!");
                 e.printStackTrace();
                }

         }

         else
         {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error!");
             alert.setHeaderText("Unable to open file!");
             alert.setContentText("File is not available or no longer exists.");
             alert.showAndWait();
         }
    }

}
