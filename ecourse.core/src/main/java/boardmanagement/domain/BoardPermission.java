package boardmanagement.domain;

import ecourseusermanagement.domain.ECourseUser;

import javax.persistence.*;

@Entity
public class BoardPermission{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private ECourseUser eCourseUser;

    @Enumerated(EnumType.STRING)
    private BoardPermissionType boardPermissionType;

    public BoardPermission(final ECourseUser eCourseUser, final BoardPermissionType boardPermission) {
        if(eCourseUser == null) {
            throw new IllegalArgumentException("eCourseUser cannot be null.");
        }
        this.eCourseUser = eCourseUser;
        this.boardPermissionType = boardPermission;
    }

    public BoardPermission(){
        // for ORM
    }

    /**
     * Owner e course user.
     *
     * @return the e course user
     */
    public ECourseUser owner() {
        return this.eCourseUser;
    }

    /**
     * Has write permission boolean.
     *
     * @return the boolean
     */
    public boolean hasWritePermission() {
        return (this.boardPermissionType == BoardPermissionType.WRITE) ||
                (this.boardPermissionType == BoardPermissionType.OWNER);
    }

    @Override
    public String toString() {
        return "User: " + this.eCourseUser.email() + " | Permission: " + this.boardPermissionType.toString();
    }
}
