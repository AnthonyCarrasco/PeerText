package app.FileSystem;

/**
 * Created by Angel on 12/6/16.
 */
public class FileAccessRequestItem {
    public String textfile_id;
    public String user_id;

    public FileAccessRequestItem(String textfile_id, String user_id)
    {
        this.textfile_id = textfile_id;
        this.user_id = user_id;
    }
}
