package app.FileSystem;

/**
 * Created by Angel on 12/5/16.
 */
public class PermissionItem {
    public String user_id;
    public String username;
    public String type;

    public PermissionItem(String user_id, String username, String type)
    {
        this.user_id = user_id;
        this.username = username;
        this.type = type;
    }

    public String getUsername()
    {
        return username;
    }

    public String getType()
    {
        return type;
    }
}
