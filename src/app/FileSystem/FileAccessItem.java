package app.FileSystem;

/**
 * Created by Angel on 12/6/16.
 */
public class FileAccessItem {
    public String textfile_id;
    public String type;
    public String title;
    public String text;
    public String available;

    public FileAccessItem(String textfile_id, String type, String title, String text, String available)
    {
        this.textfile_id = textfile_id;
        this.type = type;
        this.title = title;
        this.text = text;
        this.available = available;
    }

    public void setAvailable(String value)
    {
        this.available = value;
    }

    public String getAvailable()
    {
        return available;
    }
}
