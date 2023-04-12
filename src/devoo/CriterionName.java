package devoo;

public enum CriterionName {
    GUEST_HAS_ALLERGY('B'),
    HOST_HAS_ANIMAL('B'),
    GUEST_FOOD('T'),
    HOST_FOOD('T'),
    HOBBIES('T'),
    GENDER('T'),
    PAIR_GENDER('T'),
    HISTORY('T');

    /**
     * Attributes which represent the type of the criterion.
     */
    private final char TYPE;


    /**
     * Constructors for CriterionName.
     * @param type 
     */
    CriterionName(char type) {
        this.TYPE = type;
    }


    /**
     * Getter for TYPE.
     * @return The type.
     */
    public char getType() {
        return this.TYPE;
    }
}
