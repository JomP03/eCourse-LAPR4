package classmanagement.domain;

import eapli.framework.strings.util.StringPredicates;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ClassTitle {

    @Column(unique = true)
    private String title;


    /**
     * Instantiates a new Class title.
     *
     * @param title the title
     */
    public ClassTitle(String title) {
        if(StringPredicates.isNullOrEmpty(title))
            throw new IllegalArgumentException("Title must not be empty");
        this.title = title;
    }

    protected ClassTitle() {
        // for ORM
    }

    /**
     * Gets class title.
     *
     * @return the class title
     */
    public String getClassTitle() {
        return title;
    }


}
