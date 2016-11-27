package app.Login.Model;

import app.Login.*;

/**
 * Created by Angel on 11/26/16.
 */
public class LoginAPIDataManager {
    public Boolean attemptLogin(LoginItem loginItem)
    {
        if (loginItem.username.toLowerCase().equals("angel") && loginItem.password.equals("password"))
        {
            //System.out.println("Sucessful login!!!!");
            return true;
        }

        else
        {
            //System.out.println("Failed to login!!!!");
            return false;
        }
    }
}
