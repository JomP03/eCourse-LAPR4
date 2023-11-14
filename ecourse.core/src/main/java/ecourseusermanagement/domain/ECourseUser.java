package ecourseusermanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import usermanagement.domain.ECourseRoles;

import javax.persistence.*;
import java.util.Objects;

/**
 * The type E course user.
 */
@Entity
public class ECourseUser implements AggregateRoot<Long> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Just because SystemUser's email has protected access
    @Column(unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private SystemUser systemUser;
    @Embedded
    UserTaxNumber userTaxNumber;
    @Embedded
    UserBirthdate userBirthDate;
    @Enumerated(EnumType.STRING)
    private UserState userState;
    // Field only used by student
    @Embedded
    private UserMechanographicNumber userMechanographicNumber;
    // Field only used by teacher
    @Embedded
    private UserAcronym userAcronym;


    /**
     * Instantiates a new Student
     *
     * @param user                     the user
     * @param userTaxNumber            the user tax number
     * @param userBirthDate            the user birthdate
     * @param userMechanographicNumber the user mechanographic number
     * @throws IllegalArgumentException if the user is null
     * @throws IllegalArgumentException if the user doesn't have the role of student
     * @throws IllegalArgumentException if the userTaxNumber is null
     * @throws IllegalArgumentException if the userBirthDate is null
     * @throws IllegalArgumentException if the userMechanographicNumber is null
     */
    public ECourseUser(final SystemUser user, final UserTaxNumber userTaxNumber, final UserBirthdate userBirthDate, final UserMechanographicNumber userMechanographicNumber) {
        // Check if the systemUser is not null
        if (user == null) {
            throw new IllegalArgumentException("SystemUser can't be null");
        }
        this.systemUser = user;
        this.email = systemUser.email().toString();

        // Check if the system user is valid (has the role of the student)
        if (!user.hasAny(ECourseRoles.STUDENT)) {
            throw new IllegalArgumentException("SystemUser must have the role of student");
        }

        // Check if the userTaxNumber is not null
        if (userTaxNumber == null) {
            throw new IllegalArgumentException("UserTaxNumber can't be null");
        }
        this.userTaxNumber = userTaxNumber;

        // Check if the userBirthDate is not null
        if (userBirthDate == null) {
            throw new IllegalArgumentException("UserBirthDate can't be null");
        }
        this.userBirthDate = userBirthDate;

        // Check if the userMechanographicNumber is not null
        if (userMechanographicNumber == null) {
            throw new IllegalArgumentException("UserMechanographicNumber can't be null");
        }
        this.userMechanographicNumber = userMechanographicNumber;

        this.userState = UserState.ENABLED;
    }


    /**
     * Instantiates a new Teacher
     *
     * @param user          the user
     * @param userTaxNumber the user tax number
     * @param userBirthDate the user birthdate
     * @param userAcronym   the user acronym
     * @throws IllegalArgumentException if the user is null
     * @throws IllegalArgumentException if the user doesn't have the role of teacher
     * @throws IllegalArgumentException if the userTaxNumber is null
     * @throws IllegalArgumentException if the userBirthDate is null
     * @throws IllegalArgumentException if the userAcronym is null
     */
    public ECourseUser(final SystemUser user, final UserTaxNumber userTaxNumber, final UserBirthdate userBirthDate, final UserAcronym userAcronym) {
        // Check if the systemUser is not null
        if (user == null) {
            throw new IllegalArgumentException("SystemUser can't be null");
        }
        this.systemUser = user;
        this.email = systemUser.email().toString();

        // Check if the system user is valid (has the role of the teacher)
        if (!user.hasAny(ECourseRoles.TEACHER)) {
            throw new IllegalArgumentException("SystemUser must have the role of teacher");
        }

        // Check if the userTaxNumber is not null
        if (userTaxNumber == null) {
            throw new IllegalArgumentException("UserTaxNumber can't be null");
        }
        this.userTaxNumber = userTaxNumber;

        // Check if the userBirthDate is not null
        if (userBirthDate == null) {
            throw new IllegalArgumentException("UserBirthDate can't be null");
        }
        this.userBirthDate = userBirthDate;

        // Check if the userAcronym is not null
        if (userAcronym == null) {
            throw new IllegalArgumentException("UserAcronym can't be null");
        }
        this.userAcronym = userAcronym;

        this.userState = UserState.ENABLED;
    }


    /**
     * Instantiates a new Manager
     *
     * @param user the user
     * @throws IllegalArgumentException if the user is null
     * @throws IllegalArgumentException if the user doesn't have the role of manager
     */
    public ECourseUser(final SystemUser user) {
        // Check if the systemUser is not null
        if (user == null) {
            throw new IllegalArgumentException("SystemUser can't be null");
        }
        this.systemUser = user;
        this.email = systemUser.email().toString();

        // Check if the system user is valid (has the role of the student)
        if (!user.hasAny(ECourseRoles.MANAGER)) {
            throw new IllegalArgumentException("SystemUser must have the role of manager");
        }

        this.userState = UserState.ENABLED;
    }

    /**
     * Returns the user's name.
     *
     * @return the username
     */
    public String name() {
        return this.systemUser.name().toString();
    }

    /**
     * Returns the user's username.
     *
     * @return the username
     */
    public String username() {
        return this.systemUser.username().toString();
    }

    /**
     * Disable user.
     *
     * @throws IllegalStateException if the user is already disabled
     */
    public void disable() {
        if (this.userState == UserState.DISABLED) {
            throw new IllegalStateException("User is already disabled");
        }
        this.userState = UserState.DISABLED;
    }

    /**
     * Enable user.
     *
     * @throws IllegalStateException if the user is already enabled
     */
    public void enable() {
        if (this.userState == UserState.ENABLED) {
            throw new IllegalStateException("User is already enabled");
        }
        this.userState = UserState.ENABLED;
    }

    /**
     * Checks if the user is enabled.
     *
     * @return true, if is enabled
     */
    public boolean isEnabled() {
        return this.userState == UserState.ENABLED;
    }

    /**
     * Tells if the user is a teacher.
     *
     * @return true if the user is a teacher
     */
    public boolean isTeacher() {
        return this.systemUser.hasAny(ECourseRoles.TEACHER);
    }

    /**
     * Tells if the user is a student.
     *
     * @return the true if the user is a student
     */
    public boolean isStudent() {
        return this.systemUser.hasAny(ECourseRoles.STUDENT);
    }

    /**
     * Returns the user's email.
     *
     * @return the email
     */
    public String email() {
        return this.email;
    }

    @Override
    public String toString() {
        if (this.systemUser.hasAny(ECourseRoles.STUDENT)) {
            return String.format("Student: %s | %s | %s | %s| %s | %s | %s", this.systemUser.username(),
                    this.userMechanographicNumber, this.systemUser.name(), this.userTaxNumber, this.userBirthDate,
                    this.systemUser.email(), this.userState);
        } else if (this.systemUser.hasAny(ECourseRoles.TEACHER)) {
            return String.format("Teacher: %s | %s | %s | %s | %s | %s | %s", this.systemUser.username(),
                    this.userAcronym, this.systemUser.name(), this.userTaxNumber, this.userBirthDate,
                    this.systemUser.email(), this.userState);
        } else if (this.systemUser.hasAny(ECourseRoles.MANAGER)) {
            return String.format("Manager: %s | %s | %s | %s", this.systemUser.username(),
                    this.systemUser.name(), this.systemUser.email(), this.userState);
        }
        // It only happens if the user is a superuser
        return "";
    }

    protected ECourseUser() {
        // for ORM purposes only
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public Long identity() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ECourseUser user = (ECourseUser) o;
        return Objects.equals(email, user.email);
    }
}
