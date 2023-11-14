package boardcommunication.http.dto;

/**
 * Acts as a "bag" of data for the request body of a POST request to the /postit endpoint.
 */
public class CreatePostItRequest {

    public int row;

    public int column;

    public String content;

    public String color;

    public String boardId;

}
