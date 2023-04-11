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
     * Attributes
     */
    private final char TYPE;


    /**
     * Constructeur de CriterionName
     * @param type
     */
    CriterionName(char type) {
        this.TYPE = type;
    }


    /**
     * getter du type du Cr
     * @return
     */
    public char getType() {
        return this.TYPE;
    }
}
