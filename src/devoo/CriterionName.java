package devoo;

/**
 * La classe contient une liste de constantes énumérées, chacune étant un critère, ils peuvent être de 2 types différents représentés par des lettres : <br>
 * <br>
 * B pour un critère booleén, c'est-à-dire qui a deux valeurs possibles (vrai ou faux).<br>
 * T pour un critère textuel, c'est-à-dire qui a une valeur textuelle.
 * @author : Raphael KIECKEN, Armand SADY, Antoine GAIENIER
*/
public enum CriterionName {
    GUEST_ANIMAL_ALLERGY('B'),
    HOST_HAS_ANIMAL('B'),
    GUEST_FOOD('T'),
    HOST_FOOD('T'),
    HOBBIES('T'),
    GENDER('T'),
    PAIR_GENDER('T'),
    HISTORY('T');

    /**
     * Type du CriterionName.
     */
    private final char TYPE;

    /**
     * Constructeur de CriterionName.
     * @param type : char
     */
    CriterionName(char type) {
        this.TYPE = type;
    }

    /**
     * Accesseur pour le type du CriterionName.
     * @return char : type du CriterionName.
     */
    public char getType() {
        return this.TYPE;
    }

    /**
     * Accesseur pour le nom du CriterionName.
     * @return String : nom du CriterionName.
     */
    public String getName() {
        return this.name();
    }

    /**
     * Méthode toString.
     * @return String : nom du CriterionName.
     */
    @Override
    public String toString() {
        return this.getName();
    }

    /**
     * Méthode qui retourne le CriterionName sous forme de String avec son type.
     * @return String : CriterionName avec son type.
     */
    public String toExtendString() {
        return this.toString() + " (" + this.getType() + ")";
    }
}
