package boardcommunication.http.dto;

public class ShareBoardRequest {
    public String boardId;
    public User[] users;

    public static class User {
        public String userId;
        public boolean read;
        public boolean write;
    }
}
