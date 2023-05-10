package devoo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    /* Valeurs possible pour les critéres lié au Genre */
    final static public String F = "female"; 
    final static public String M = "male";
    final static public String OTH = "other";
    final static public String BLANK = "";

    /** Valeurs possible pour le critéres lié à l'Historique*/
    final static public String PREF_SAME = "same";
    final static public String PREF_OTH = "other";

    /* Valeurs possible pour les critéres lié au Regime alimentaire */
    
    final static public String FOOD_N = "nonuts";
    final static public String FOOD_V = "vegetarian";
    final static public String FOOD_BLANK = "";

    final static private Map<CriterionName, ArrayList<String>> possibleValues = Map.of(
        CriterionName.GUEST_HAS_ALLERGY, new ArrayList<String>(Arrays.asList(POS, NEG)),
        CriterionName.HOST_HAS_ANIMAL, new ArrayList<String>(Arrays.asList(POS, NEG)),
        CriterionName.GENDER, new ArrayList<String>(Arrays.asList(F, M, OTH)),
        CriterionName.PAIR_GENDER, new ArrayList<String>(Arrays.asList(F, M, OTH, BLANK)),
        CriterionName.HISTORY, new ArrayList<String>(Arrays.asList(PREF_SAME, PREF_OTH)),
        CriterionName.GUEST_FOOD, new ArrayList<String>(Arrays.asList(FOOD_N, FOOD_V, FOOD_BLANK)),
        CriterionName.HOST_FOOD, new ArrayList<String>(Arrays.asList(FOOD_N, FOOD_V, FOOD_BLANK)));

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
    public void isValid() throws CriterionException {
        switch (this.label.getType()) {
            case 'B':
                if (!possibleValues.get(this.label).contains(this.value)) {
                    throw new CriterionException(String.format("Valeur \"%s\" incorrect | Valeurs possibles (%s) : %s", this.value, this.label.getName(), Arrays.toString(B_VALUES.toArray())));
                }
            case 'T':
                if(this.label.equals(CriterionName.GENDER) && !GENDER_VALUES.contains(this.value)) {
                    throw new CriterionException(String.format("Valeur \"%s\" incorrect | Valeurs possibles (%s) : %s", this.value, this.label.getName(), Arrays.toString(GENDER_VALUES.toArray())));
                } 
                else if(this.label.equals(CriterionName.PAIR_GENDER) && !(PAIR_GENDER_VALUES.contains(this.value))) {
                    throw new CriterionException(String.format("Valeur \"%s\" incorrect | Valeurs possibles (%s) : %s", this.value, this.label.getName(), Arrays.toString(PAIR_GENDER_VALUES.toArray())));
                }
                else if(this.label.equals(CriterionName.HISTORY) && !HISTORY_VALUES.contains(this.value)) {
                    throw new CriterionException(String.format("Valeur \"%s\" incorrect | Valeurs possibles (%s) : %s", this.value, this.label.getName(), Arrays.toString(PAIR_GENDER_VALUES.toArray())));
                }
                else if((this.label.equals(CriterionName.HOST_FOOD) || this.label.equals(CriterionName.GUEST_FOOD)) && !FOOD_VALUES.containsAll(Arrays.asList(this.value.split(",")))) {
                    throw new CriterionException(String.format("Valeur \"%s\" incorrect | Valeurs possibles (%s) : %s", this.value, this.label.getName(), Arrays.toString(FOOD_VALUES.toArray())));
                }
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
