package app.FileSystem;

/**
 * Created by Angel on 12/5/16.
 */
public class RequestPermissionsItem {
    public String user_id;
    public String textfile_id;

    public RequestPermissionsItem(String user_id, String textfile_id)
    {
        this.user_id = user_id;
        this.textfile_id = textfile_id;
    }
}
