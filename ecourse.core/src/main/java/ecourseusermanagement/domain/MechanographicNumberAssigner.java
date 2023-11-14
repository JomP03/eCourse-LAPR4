package ecourseusermanagement.domain;

import ecourseusermanagement.repositories.IeCourseUserRepository;

/**
 * Implementation of the mechanographic number assigner
 */
public class MechanographicNumberAssigner implements IMechanographicNumberAssigner{

    private final IeCourseUserRepository userRepository;

    /**
     * Instantiates a new Mechanographic number assigner.
     * Receives the user repository as a dependency (DI pattern)
     *
     * @param userRepository the user repository
     */
    public MechanographicNumberAssigner(IeCourseUserRepository userRepository) {
        if ( userRepository == null ) {
            throw new IllegalArgumentException("User repository must be provided.");
        }
        this.userRepository = userRepository;
    }


    @Override
    public UserMechanographicNumber newMechanographicNumber() {
        String currentYear = String.valueOf(java.time.Year.now().getValue());

        // Call the repository to get the last mechanographic number in the current year
        UserMechanographicNumber lastMechanographicNumber = userRepository.findLastMechanographicNumberInYear(currentYear).orElse(null);

        // If there is no user with a mechanographic number in the current year, return the first one
        if (lastMechanographicNumber == null) {
            return new UserMechanographicNumber(currentYear + "00001");
        }

        // Otherwise, increment the last mechanographic number and return it
        return new UserMechanographicNumber(String.valueOf(Integer.parseInt(lastMechanographicNumber.toString()) + 1));
    }
}
