package postitmanagement.domain;

import boardmanagement.domain.*;
import eapli.framework.domain.model.*;
import ecourseusermanagement.domain.ECourseUser;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
public class PostIt implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private BoardCell boardCell;

    @Embedded
    private PostItTimestamp postItTimestamp; 

    @Embedded
    private PostItContent postItContent;

    @Embedded
    private PostItColor postItColor;

    @Embedded
    private PostItPreviousContent postItPreviousContent;

    @Column
    private String previousCoordinates;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.DETACH, CascadeType.REFRESH})
    private ECourseUser boardPostItCreator;

    protected PostIt() {
        // For ORM
    }

    /**
     * Instantiates a new Board post it.
     *
     * @param boardPostItContent the board post-it content
     * @param boardPostItColor   the board post-it color
     */
    public PostIt(BoardCell boardCell, String boardPostItContent, String boardPostItColor,
                  ECourseUser boardPostItCreator) {
        // Verify if the board post it creator is null
        if (boardCell == null) {
            throw new IllegalArgumentException("The board cell cannot be null.");
        }

        // Create the board cell
        this.boardCell = boardCell;

        // Create the board post it content
        this.postItContent = new PostItContent(boardPostItContent);

        // Create the board post it color
        this.postItColor = new PostItColor(boardPostItColor);

        // Create the board post it create date
        this.postItTimestamp = new PostItTimestamp(LocalDateTime.now());

        // Create the board post it previous content, it was assigned to empty string due to the fact that
        // the post it was just created and the ORM needs a default constructor so we could not let it null
        // (no parameters)
        this.postItPreviousContent = new PostItPreviousContent("");

        // Verify if the board post it creator is null
        if (boardPostItCreator == null) {
            throw new IllegalArgumentException("The board post it creator cannot be null.");
        }

        this.boardPostItCreator = boardPostItCreator;

        // No previous coordinates
        this.previousCoordinates = "";
    }

    /**
     * Current content in the post-it.
     */
    public PostItContent currentContent() {
        return this.postItContent;
    }


    /**
     * Returns the cell of the post-it.
     * @return the cell
     */
    public BoardCell cell() {
        return this.boardCell;
    }

    /**
     * Previous content in the post-it.
     */
    public Optional<PostItPreviousContent> previousContent() {
        return Optional.ofNullable(this.postItPreviousContent)
                .filter(content -> !content.previousContent().equals(""))
                .map(content -> this.postItPreviousContent);
    }

    /**
     * Check if the user is the owner of the post-it.
     * @param user the user
     * @return true if the user is the owner, false otherwise
     */
    public boolean isOwner(ECourseUser user) {
        return this.boardPostItCreator.equals(user);
    }


    /**
     * Change the content of the post-it.
     *
     * @param content         the new content
     * @param previousContent the previous content
     */
    public void changeContent(ECourseUser user, String previousContent, String content, String color) {
        if (!isOwner(user))
            throw new IllegalStateException("You are not the owner of the post-it.");

        if (content == null || content.trim().isEmpty())
            throw new IllegalArgumentException("The content cannot be empty.");

        this.postItPreviousContent = new PostItPreviousContent(previousContent);
        this.postItContent = new PostItContent(content);
        this.postItColor = new PostItColor(color);

        // The post-it was edited
        this.postItTimestamp = new PostItTimestamp(LocalDateTime.now());
    }

    /**
     * Moves the post-it to a new cell.
     */
    public void replaceCell(ECourseUser user, BoardCell newCell) {
        if (!isOwner(user))
            throw new IllegalStateException("You are not the owner of the post-it.");

        // Save the previous coordinates
        this.previousCoordinates = this.boardCell.coordinates();

        this.boardCell = newCell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostIt)) return false;

        PostIt postIt = (PostIt) o;

        return postItContent.content().equals(postIt.postItContent.content()) &&
                postItPreviousContent.previousContent().equals(postIt.postItPreviousContent.previousContent())
                && boardPostItCreator.equals(postIt.boardPostItCreator);
    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public Long identity() {
        return this.id;
    }

    public PostIt undoLastChange(ECourseUser boardUser) {
        // Verify if the user is the post-it owner
        if (!isOwner(boardUser)) {
            throw new IllegalStateException("You are not the post-it owner!");
        }

        // Verify if the post-it has a previous content
        if (this.postItPreviousContent.previousContent().isEmpty()) {
            throw new IllegalStateException("No previous content!");
        }

        // Swap the previous and current content
        swapContent();

        // The post-it was undone
        this.postItTimestamp = new PostItTimestamp(LocalDateTime.now());

        return this;
    }

    private void swapContent() {
        // Aux variable
        String holder = this.postItContent.content();

        // Swap contents
        this.postItContent = new PostItContent(this.postItPreviousContent.previousContent());

        this.postItPreviousContent = new PostItPreviousContent(holder);
    }
}
