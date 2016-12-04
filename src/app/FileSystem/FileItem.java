package app.FileSystem;

/**
 * Created by Angel on 12/4/16.
 */
public class FileItem {
    public String textfile_id;
    public String owner_id;
    public String title;

    public FileItem(String textfile_id, String owner_id, String title)
    {
        this.textfile_id = textfile_id;
        this.owner_id = owner_id;
        this.title = title;
    }

    public String getTitle()
    {
        return  this.title;
    }
}
