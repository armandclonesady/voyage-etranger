package devoo;

import java.util.ArrayList;
import java.util.Arrays;
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

    /**
     * Méthode retournant tous les 
     * @return Une String qui contient toutes les valeurs possibles pour le critérion dans la variable this.label
     */
    public ArrayList<String> getRightValues() {
        return possibleValues.get(this.label);
    }

    public String errorMsg() {
        return String.format("Valeur \"%s\" incorrect | Valeurs possibles (%s) : %s", this.value, this.label.getName(), Arrays.toString(getRightValues().toArray()));
    }
    
    public void isValid() throws CriterionException {
        switch (this.label.getType()) {
            case 'B':
                if (!getRightValues().contains(this.value)) {
                    throw new CriterionException(String.format("Valeur \"%s\" incorrect | Valeurs possibles (%s) : %s", this.value, this.label.getName(), Arrays.toString(possibleValues.get(this.label).toArray())));
                }
            case 'T':
                if(!getRightValues().contains(this.value)) {
                    throw new CriterionException(errorMsg());
                }
                else if((this.label.equals(CriterionName.HOST_FOOD) || this.label.equals(CriterionName.GUEST_FOOD)) && !getRightValues().containsAll(Arrays.asList(this.value.split(",")))) {  
                    throw new CriterionException(errorMsg());
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
