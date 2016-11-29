package app;

import app.Login.Model.LoginAPIDataManager;
import app.Login.Model.LoginModel;
import com.sun.deploy.util.FXLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import app.Login.Controller.*;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loginLoader = new FXMLLoader();
        Parent root = loginLoader.load(getClass().getResource("Login/View/LoginView.fxml").openStream());
        LoginController lController = (LoginController) loginLoader.getController();
        LoginModel lModel = new LoginModel();
        lController.model = lModel;
        lModel.loginAPIDataManager = new LoginAPIDataManager();
        lModel.controller = lController;
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 600, 400));
        lController.primaryStage = primaryStage;
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
