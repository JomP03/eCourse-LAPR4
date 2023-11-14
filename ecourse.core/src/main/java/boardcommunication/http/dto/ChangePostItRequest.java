package boardcommunication.http.dto;

public class ChangePostItRequest {

    public long boardId;
    public int oldRow;
    public int oldColumn;
    public int newRow;
    public int newColumn;
    public String oldContent;
    public String newContent;
    public String color;
}
