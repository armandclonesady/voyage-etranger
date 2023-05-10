package devoo;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe Criterion
 * @author : Raphael Kiecken, Armand Sady, Antoine Gaienier
 */
public class Criterion {

    private CriterionName label;
    private String value;

    /* Valeurs possible pour les critéres booléens */
    final static public String POS = "yes"; 
    final static public String NEG = "no";
    final static private ArrayList<String> B_VALUES = new ArrayList<String>(Arrays.asList(POS, NEG));

    /* Valeurs possible pour les critéres lié au Genre */
    final static public String F = "female"; 
    final static public String M = "male";
    final static public String OTH = "other";
    final static private ArrayList<String> GENDER_VALUES = new ArrayList<String>(Arrays.asList(F, M, OTH));

    /** Valeurs possible pour le critéres lié à l'Historique*/
    final static public String PREF_SAME = "same";
    final static public String PREF_OTH = "other";
    final static private ArrayList<String> HISTORY_VALUES = new ArrayList<String>(Arrays.asList(PREF_SAME, PREF_OTH));

    /* Valeurs possible pour les critéres lié au Regime alimentaire */
    
    final static public String FOOD_N = "nonuts";
    final static public String FOOD_V = "vegetarian";
    final static private ArrayList<String> FOOD_VALUES = new ArrayList<String>(Arrays.asList(FOOD_N, FOOD_V));

    /** Constructeur de Criterion.
     * @param label 
     * @param value 
     */
    public Criterion(CriterionName label, String value) {
        this.label = label;
        this.value = value;
    }

    /** Constructeur de Criterion.
     * @param label 
     * @param value 
     */
    public Criterion(String label, String value) {
        this(CriterionName.valueOf(label), value);
    }   

    /**
     * Méthode qui vérifie si le critère est valide
     * @return
     */ 
    public boolean isValid() {
        switch (this.label.getType()) {
            case 'B':
                try {
                    checkBoolean(this.value);
                }
                return B_VALUES.contains(this.value);
            case 'T':
                if(this.label.equals(CriterionName.GENDER)) {
                    return GENDER_VALUES.contains(this.value);
                } 
                else if(this.label.equals(CriterionName.PAIR_GENDER)) {
                    return GENDER_VALUES.contains(this.value) || this.value.isEmpty();
                }
                else if(this.label.equals(CriterionName.HISTORY)) {
                    return HISTORY_VALUES.contains(this.value);
                }
                else if(this.label.equals(CriterionName.HOST_FOOD) || this.label.equals(CriterionName.GUEST_FOOD)) {
                    return FOOD_VALUES.containsAll(Arrays.asList(this.value.split(","))) || this.value.isEmpty();
                }
                return true;
            default:
                return false;
        }
    }

    public void checkBoolean (String str) throws BadBooleanCriterionExeption {
        if (!B_VALUES.contains(str)) {
            throw new BadBooleanCriterionExeption(String.format("La valeur \"%s\" n'est pas une valeur possible : %s", str, Arrays.toString(B_VALUES.toArray())));
        }
    }

    /**
     * Méthode qui renvoie le label du critère
     * @return
     */

    public CriterionName getLabel() {
        return this.label;
    }

    /**
     * Méthode qui renvoie la value
     * @return
     */
    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.label + " " + this.value;
    }
}
