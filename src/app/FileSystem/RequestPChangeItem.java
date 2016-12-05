package app.FileSystem;

/**
 * Created by Angel on 12/5/16.
 */
public class RequestPChangeItem {
    public String textfile_id;
    public String user_id;
    public String type;

    public RequestPChangeItem(String textfile_id, String user_id, String type)
    {
        this.textfile_id = textfile_id;
        this.user_id = user_id;
        this.type = type;
    }
}
