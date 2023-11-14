package ecourseusermanagement.domain;

/**
 * Interface for the service responsible for assigning mechanographic numbers.
 */
public interface IMechanographicNumberAssigner {


    /**
     * Creates a new mechanographic number.
     * The number is composed by the year and a sequential number.
     * The year is the current year.
     * The sequential number is the last number used in the current year plus one.
     *
     * @return the new mechanographic number
     */
    UserMechanographicNumber newMechanographicNumber();

}
