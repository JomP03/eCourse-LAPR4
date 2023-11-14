package boardcommunication.http.dto;

import java.util.List;

public class BoardCreationRequest {

    public String boardTitle;

    public List<String> boardColumns;

    public List<String> boardRows;
}
