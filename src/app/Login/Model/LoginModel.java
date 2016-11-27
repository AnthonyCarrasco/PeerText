package app.Login.Model;

import app.Login.Controller.LoginController;
import app.Login.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.*;


/**
 * Created by Angel on 11/23/16.
 */
public class LoginModel {
    public LoginController controller;
    public LoginAPIDataManager loginAPIDataManager;
    private static ExecutorService service = Executors.newCachedThreadPool();

    public void loginAttempt(LoginItem loginItem)
    {
        System.out.println("Username: " + loginItem.username + " Password: " + loginItem.password);

        //Boolean success = loginAPIDataManager.attemptLogin(loginItem);
        //controller.returnResultsOfLogin(success);

        CompletableFuture<Boolean> f1 = new CompletableFuture();
        f1.supplyAsync(() -> { return loginAPIDataManager.attemptLogin(loginItem);}).whenCompleteAsync((a, error) -> {controller.returnResultsOfLogin(a);});

    }
}
