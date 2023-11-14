package boardlogmanagement.domain;

import boardmanagement.domain.*;
import eapli.framework.domain.model.*;
import ecourseusermanagement.domain.ECourseUser;

import javax.persistence.*;

@Entity
public class BoardLog implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Board board;

    @Embedded
    private BoardLogTimestamp timestamp;
    @Enumerated(EnumType.STRING)
    private BoardLogComponent component;
    @Enumerated(EnumType.STRING)
    private BoardOperationType operation;
    @OneToOne(cascade = CascadeType.ALL)
    private ECourseUser user;

    private int oldColumn;

    private int oldRow;

    private int newColumn;

    private int newRow;

    private String oldContent;

    private String newContent;


    public BoardLog(final Board board, final BoardLogComponent component,
                    final BoardOperationType operation, final ECourseUser user) {
        if(board == null) {
            throw new IllegalArgumentException("Board cannot be null.");
        }
        if(component == null) {
            throw new IllegalArgumentException("Component cannot be null.");
        }
        if(operation == null) {
            throw new IllegalArgumentException("Operation cannot be null.");
        }
        if(user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        this.board = board;
        this.component = component;
        this.operation = operation;
        this.user = user;
        this.timestamp = new BoardLogTimestamp();
    }

    public BoardLog(final Board board, final BoardLogComponent component,
                    final BoardOperationType operation, final ECourseUser user,  int oldColumn,
                    int oldRow, int newColumn, int newRow, String oldContent, String newContent) {

        if(board == null) {
            throw new IllegalArgumentException("Board cannot be null.");
        }
        if(component == null) {
            throw new IllegalArgumentException("Component cannot be null.");
        }
        if(operation == null) {
            throw new IllegalArgumentException("Operation cannot be null.");
        }
        if(user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        if(oldContent == null) {
            throw new IllegalArgumentException("Old content cannot be null.");
        }

        if(newContent == null) {
            throw new IllegalArgumentException("New content cannot be null.");
        }

        this.board = board;
        this.component = component;
        this.operation = operation;
        this.user = user;
        this.timestamp = new BoardLogTimestamp();
        this.oldColumn = oldColumn;
        this.oldRow = oldRow;
        this.newColumn = newColumn;
        this.newRow = newRow;
        this.oldContent = oldContent;
        this.newContent = newContent;
    }



    public BoardLog() {
        // for ORM
    }

    /**
     * Returns the user who performed the operation.
     * @return the user who performed the operation.
     */
    public ECourseUser user() {
        return user;
    }

    @Override
    public String toString() {
        return this.operation + " at " + this.timestamp + " on " + this.component;
    }

    @Override
    public Long identity() {
        return id;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof BoardLog)) {
            return false;
        }

        final BoardLog that = (BoardLog) other;
        if (this == that) {
            return true;
        }

        return identity().equals(that.identity());
    }
}
