package app.FileSystem.Controller;

/**
 * Created by Angel on 12/5/16.
 */
public class DeleteItem {
    public String textfile_id;
    public String user_id;

    public DeleteItem(String textfile_id, String user_id)
    {
        this.textfile_id = textfile_id;
        this.user_id = user_id;
    }
}
