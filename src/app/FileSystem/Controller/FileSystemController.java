package app.FileSystem.Controller;

import app.FileSystem.FileItem;
import app.FileSystem.Model.FileSystemModel;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.*;
import javafx.scene.control.TableView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Angel on 11/23/16.
 */
public class FileSystemController extends Parent{
    private String user_id;
    @FXML private Button btnCreateFile;
    @FXML private Button btnDeleteFile;
    @FXML private Button btnEditAccess;
    @FXML private Button btnEditViewAccess;
    @FXML private TableView<FileItem> fileTable;
    @FXML private TableColumn<FileItem, String> fileNameCol;
    ObservableList<FileItem> files;

    public FileSystemModel model;

    public void loadFiles(String user_id)
    {

        System.out.println("Table: " + fileTable);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                fileNameCol.setCellValueFactory(
                        new PropertyValueFactory<FileItem, String>("title"));
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

                fileTable.setItems(files);

            }
        });
    }


    @FXML protected  void handleCreateButtonAction(ActionEvent event)
    {
        System.out.println("Create file pressed");
    }

}
