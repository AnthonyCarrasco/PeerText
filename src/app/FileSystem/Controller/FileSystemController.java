package app.FileSystem.Controller;

import app.FileSystem.FileItem;
import app.FileSystem.Model.FileSystemModel;
import app.FileSystem.PermissionItem;
import app.FileSystem.RequestPChangeItem;
import app.FileSystem.RequestPermissionsItem;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.collections.*;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.DefaultStringConverter;

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
    @FXML private TableView<PermissionItem> pTable;
    @FXML private TableColumn<PermissionItem, String> userCol;
    @FXML private TableColumn<PermissionItem, String> accessCol;
    private String selectedFileID;
    ObservableList<FileItem> files;
    ObservableList<PermissionItem> pItems;
    ObservableList<String> permissions = FXCollections.observableArrayList(
            "e", "v", "n");

    public FileSystemModel model;

    public void loadFiles(String user_id)
    {

        System.out.println("Table: " + fileTable);

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
                        System.out.println("Selected Item: " + newSelection.title);
                        if (newSelection.owner_id.equals(user_id))
                        {
                            RequestPermissionsItem requestPItem = new RequestPermissionsItem(user_id, newSelection.textfile_id);
                            selectedFileID = newSelection.textfile_id;
                            model.getPermissions(requestPItem);
                        }
                        else
                        {
                            pTable.getItems().clear();
                        }

                        model.getFiles(user_id);
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
                        RequestPChangeItem modifyedPItem = new RequestPChangeItem(selectedFileID, ((PermissionItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).user_id, t.getNewValue());
                        model.setPermission(modifyedPItem);
                    }

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
        System.out.println("Create file pressed");
    }

}
