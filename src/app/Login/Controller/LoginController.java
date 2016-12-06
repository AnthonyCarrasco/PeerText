package app.Login.Controller;

import app.FileSystem.Controller.FileSystemController;
import app.FileSystem.Model.FileSystemAPIDataManager;
import app.FileSystem.Model.FileSystemModel;
import app.Login.LoginItem;
import app.Login.LoginResults;
import app.Login.Model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import java.io.IOException;

import static javafx.fxml.FXMLLoader.*;

public class LoginController extends Parent{
    @FXML private Button btnLogin;
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    public LoginModel model;
    public Stage primaryStage;

    @FXML protected  void handleLoginButtonAction(ActionEvent event){
        System.out.println("Login button pressed!");
        if (txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Login failed!");
            alert.setContentText("Please enter a username and password");
            alert.showAndWait();
        }
        else
        {
            LoginItem tempItem = new LoginItem();
            tempItem.username = txtUsername.getText();
            tempItem.password = txtPassword.getText();
            model.loginAttempt(tempItem);
        }
    }

    public void returnResultsOfLogin(LoginResults results)
    {
        System.out.println("Return results of login called!!!!");
        // Changes to the UI must be done on JavaFX Thread
        if (results.success == true) {

            try {
                FXMLLoader fileSystemLoader = new FXMLLoader();
                Parent fileSystemView = fileSystemLoader.load(getClass().getResource("/app/FileSystem/View/FileSystem.fxml").openStream());
                FileSystemController fController = (FileSystemController) fileSystemLoader.getController();
                fController.model = new FileSystemModel();
                fController.model.controller = fController;
                fController.model.fileSystemAPIDataManager = new FileSystemAPIDataManager();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        primaryStage.setTitle("File System");
                        primaryStage.setScene(new Scene(fileSystemView));
                        primaryStage.show();
                        fController.loadFiles(results.user_id);

                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else
        {

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Login failed!");
                    alert.setContentText("Invalid username or password");
                    alert.showAndWait();
                }
            });
        }
    }

}
