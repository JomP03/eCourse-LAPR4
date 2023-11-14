package ecourseusermanagement.domain;


import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;

import javax.persistence.*;

@Embeddable
public class UserTaxNumber implements ValueObject {

    // For serialization purposes
    private static final long serialVersionUID = 1L;

    private static final int PORTUGUESE_TAX_NUMBER_LENGTH = 9;

    @Column
    private String taxNumber;


    /**
     * Instantiates a new User tax number.
     *
     * @param taxNumber the tax number
     *
     * @throws IllegalArgumentException if the taxNumber is null or empty
     * @throws IllegalArgumentException if the taxNumber is not a number
     * @throws IllegalArgumentException if the taxNumber length is not correct
     * @throws IllegalArgumentException if the taxNumber is not valid according to the Portuguese tax number algorithm
     */
    public UserTaxNumber(final String taxNumber) {
        // Check if the taxNumber is null or empty
        if (StringPredicates.isNullOrEmpty(taxNumber)) {
            throw new IllegalArgumentException(
                    "A Tax Number must be provided!");
        }

        // Check if the taxNumber is a number
        if (!taxNumber.matches("[0-9]+")) {
            throw new IllegalArgumentException(
                    "Tax Number can only contain numbers");
        }

        // Check if the taxNumber length is correct
        if (taxNumber.length() != PORTUGUESE_TAX_NUMBER_LENGTH) {
            throw new IllegalArgumentException(
                    "Tax Number must have " + PORTUGUESE_TAX_NUMBER_LENGTH + " digits");
        }

        // Check if the taxNumber is valid according to the Portuguese tax number algorithm
        if (!respectsPortugueseTaxNumberRules(taxNumber)) {
            throw new IllegalArgumentException(
                    "That's not a valid Portuguese Tax Number!");
        }

        this.taxNumber = taxNumber;
    }

    // This is a bad implementation of this process
    // If the system was to support more countries, it would be a good idea to have a validator class for each country
    private boolean respectsPortugueseTaxNumberRules(String taxNumber) {
        int digitsSum = 0;
        for (int i = 0; i < taxNumber.length() - 1; i++) {
            digitsSum += Character.getNumericValue(taxNumber.charAt(i)) * (taxNumber.length() - i);
        }

        int checkDigit = 11 - digitsSum % 11;

        if (checkDigit >= 10) {
            checkDigit = 0;
        }

        return checkDigit == Character.getNumericValue(taxNumber.charAt(taxNumber.length() - 1));
    }

    protected UserTaxNumber() {
        // for ORM purposes
    }

    @Override
    public String toString() {
        return this.taxNumber;
    }


}
