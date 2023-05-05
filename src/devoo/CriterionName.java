package devoo;

/*
    La classe contient une liste de constantes énumérées, chacune étant un critère, qui peut être de trois types différents représentés par des lettres :

    'B' pour un critère binaire, c'est-à-dire qui a deux valeurs possibles (vrai ou faux),
    'T' pour un critère textuel, c'est-à-dire qui a une valeur textuelle,
   
    La classe a deux méthodes publiques :

    getType() qui renvoie le type du critère (B, T),
    getName() qui renvoie le nom de la constante énumérée correspondante
    
    @autor : Raphael Kiecken, Armand Sady, Antoine Gaienier
*/
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
     * Attributs qui représentent le type du critère.
     */
    private final char TYPE;

    /**
     * Constructeurs pour CriterionName.
     * @param type 
     */
    CriterionName(char type) {
        this.TYPE = type;
    }

    /**
     * Getter pour TYPE.
     * @return The type.
     */
    public char getType() {
        return this.TYPE;
    }

    public String getName() {
        return this.name();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }

}
