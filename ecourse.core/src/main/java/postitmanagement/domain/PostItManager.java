package postitmanagement.domain;
import boardmanagement.domain.Board;
import boardmanagement.domain.BoardCell;
import boardmanagement.domain.BoardLineNumber;
import ecourseusermanagement.domain.ECourseUser;
import postitmanagement.application.IPostItProvider;

import java.util.Optional;

public class PostItManager implements IPostItManager {

    private final IPostItProvider postItProvider;

    /**
     * Instantiates a new PostItManager.
     * @param postItProvider the post-it provider
     */
    public PostItManager(IPostItProvider postItProvider) {
        // Verify if the post-it provider is null
        if (postItProvider == null) {
            throw new IllegalArgumentException("The post-it provider cannot be null.");
        }

        this.postItProvider = postItProvider;
    }

    @Override
    public PostIt createPostIt(Board board, BoardLineNumber row, BoardLineNumber column, ECourseUser postItCreator,
                               String postItContent, String postItColor) {
        // Retrieve the cell in which the post-it will be created
        BoardCell cell = board.findCell(row, column);

        // Verify if there is already a post-it in the given coordinates
        if (postItProvider.hasPostIt(cell)) {
            throw new IllegalStateException("There is already a post-it in the given cell.");
        }

        // return the created post-it
        return new PostIt(cell, postItContent, postItColor, postItCreator);
    }

    @Override
    public PostIt changePostItContent(ECourseUser user, Board board,
                                      int oldRow, int oldColumn,
                                      String oldContent, String newContent, String color) {
        // Retrieve the cell in which the post-it will be changed
        BoardCell oldCell = board.findCell(BoardLineNumber.valueOf(oldRow), BoardLineNumber.valueOf(oldColumn));

        Optional<PostIt> postIt = postItProvider.providePostItByCell(oldCell);
        if (postIt.isEmpty())
            throw new IllegalStateException("There is no post-it in the cell.");

        PostIt obtainedPostIt = postIt.get();
        obtainedPostIt.changeContent(user, oldContent, newContent, color);

        return obtainedPostIt;
    }


    @Override
    public PostIt movePostIt(ECourseUser user, Board board, PostIt postIt, int newRow, int newColumn) {
        BoardCell newCell = board.findCell(BoardLineNumber.valueOf(newRow), BoardLineNumber.valueOf(newColumn));
        if (!postItProvider.hasPostIt(newCell))
            postIt.replaceCell(user, newCell);
        else
            throw new IllegalStateException("There is already a post-it in the given cell.");

        return postIt;
    }

    @Override
    public PostIt undoPostItChange(Board board, ECourseUser boardUser, BoardLineNumber row, BoardLineNumber column) {
        // Retrieve the cell in which the post-it is placed
        BoardCell cell = board.findCell(row, column);

        // Retrieve the post-it to undo
        Optional<PostIt> postIt = postItProvider.providePostItByCell(cell);

        // Verify if the post-it exists
        if (postIt.isEmpty()) {
            throw new IllegalStateException("There is no post-it in the cell.");
        }

        // Undo the last change
        return postIt.get().undoLastChange(boardUser);
    }
}
