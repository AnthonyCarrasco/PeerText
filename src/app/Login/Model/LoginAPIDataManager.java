package app.Login.Model;

import app.Login.*;
import com.google.gson.*;


/**
 * Created by Angel on 11/26/16.
 */
public class LoginAPIDataManager {
    public Boolean attemptLogin(LoginItem loginItem)
    {

        Gson test = new Gson();
        String value = test.toJson(loginItem);


        if (loginItem.username.toLowerCase().equals("angel") && loginItem.password.equals("password"))
        {
            System.out.println(value);
            return true;
        }

        else
        {
            //System.out.println("Failed to login!!!!");
            return false;
        }
    }
}
