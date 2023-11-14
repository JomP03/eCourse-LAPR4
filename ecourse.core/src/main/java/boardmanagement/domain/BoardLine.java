package boardmanagement.domain;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // Used to separate the subclasses in different tables
public class BoardLine{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // The AUTO is used instead of the IDENTITY to allow the use of the TABLE_PER_CLASS inheritance
    private Long id;
    @Column
    private BoardLineTitle boardLineTitle;
    @Column
    private int boardLineNumber;

    protected BoardLine(final BoardLineTitle boardLineTitle, final int boardLineNumber) {
        if (boardLineTitle == null) {
            throw new IllegalArgumentException("Board Line Title can't have a null value");
        }

        if(boardLineNumber < 0){
            throw new IllegalArgumentException("Board Line Number shouldn't be negative");
        }

        this.boardLineTitle = boardLineTitle;

        this.boardLineNumber = boardLineNumber;
    }

    protected BoardLine() {
        // for ORM
    }

    public int number(){
        return this.boardLineNumber;
    }

    public String title(){
        return this.boardLineTitle.toString();
    }

    /**
     * @return the boardLineTitle as String
     */
    @Override
    public String toString() {
        return "Title: " + title() + " | Number: " + number();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardLine)) return false;

        BoardLine postIt = (BoardLine) o;

        return this.boardLineTitle.equals(postIt.boardLineTitle) && this.boardLineNumber == postIt.boardLineNumber;
    }
}
