package boardmanagement.application;

import boardmanagement.domain.Board;
import boardmanagement.domain.BoardPermission;
import boardmanagement.domain.BoardPermissionType;
import ecourseusermanagement.application.IUsersProvider;

public class BoardPermissionHandler {
    private final IUsersProvider usersProvider;
    public BoardPermissionHandler(IUsersProvider usersProvider) {
        if (usersProvider == null) {
            throw new IllegalArgumentException("The usersProvider cannot be null.");
        }
        this.usersProvider = usersProvider;
    }

    public void handleBoardSharing(Board board, Long userId, boolean read, boolean write) {
        usersProvider.retrieveUserById(userId).ifPresent(user -> {

            if (write) {
                board.users().add(new BoardPermission(user, BoardPermissionType.WRITE));
            }

            if (read) {
                board.users().add(new BoardPermission(user, BoardPermissionType.READ));
            }

        });
    }
}
