package devoo;

/**
    La classe contient une liste de constantes énumérées, chacune étant un critère, ils peuvent être de 2 types différents représentés par des lettres :

    'B' pour un critère booleén, c'est-à-dire qui a deux valeurs possibles (vrai ou faux),
    'T' pour un critère textuel, c'est-à-dire qui a une valeur textuelle,
   
    L'enum a deux méthodes publiques :

    getType() qui renvoie le type du critère (B, T),
    getName() qui renvoie le nom de la constante énumérée correspondante
    
    @autor : Raphael Kiecken, Armand Sady, Antoine Gaienier
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

    /* Attributs qui représentent le type du critère. */
    private final char TYPE;

    /* Constructeur de CriterionName. */
    CriterionName(char type) {
        this.TYPE = type;
    }

    /* Getter pour le type du critère. */
    public char getType() {
        return this.TYPE;
    }

    /* Getter pour le nom de la constante énumérée. */
    public String getName() {
        return this.name();
    }

    /* Méthode toShortString. */
    public String toString() {
        return this.getName();
    }

    /* Méthode toExtendString. */
    public String toExtendString() {
        return this.getName() + " (" + this.getType() + ")";
    }
}
