package devoo;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

/**
 * Classe Criterion
 * @autor : Raphael Kiecken, Armand Sady, Antoine Gaienier
 */
public class Criterion {
    /**
     * Attributes
     */
    private CriterionName label;
    private String value;

    /**
     * Valeurs pour les booléens
     */
    final static public String POS = "yes"; 
    final static public String NEG = "no";
    final static private ArrayList<String> B_VALUES = new ArrayList<String>(Arrays.asList(POS, NEG));
    /**
     * Valeurs pour le genre
     */
    final static public String F = "female"; 
    final static public String M = "male";
    final static public String OTH = "other";
    final static private ArrayList<String> GENDER_VALUES = new ArrayList<String>(Arrays.asList(F, M, OTH));

    /**
     * Valeurs pour l'Histoire
     */
    final static public String PREF_SAME = "same";
    final static public String PREF_OTH = "other";
    final static private ArrayList<String> HISTORY_VALUES = new ArrayList<String>(Arrays.asList(PREF_SAME, PREF_OTH));

    final static public String FOOD_N = "nonut";
    final static public String FOOD_V = "vegetarian";
    final static private ArrayList<String> FOOD_VALUES = new ArrayList<String>(Arrays.asList(FOOD_N, FOOD_V));


    /**
     * Constructeur de Criterion
     * @param value 
     * @param label
     */
    public Criterion(CriterionName label, String value) {
        this.label = label;
        this.value = value;
    }
    /*
     * constructeur de Criterion chaîner
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
                return B_VALUES.contains(this.value);
            case 'T':
                if(this.label.equals(CriterionName.GENDER) || this.label.equals(CriterionName.PAIR_GENDER)) {
                    return GENDER_VALUES.contains(this.value);
                } 
                else if(this.label.equals(CriterionName.HISTORY)) {
                    return HISTORY_VALUES.contains(this.value);
                }
                else if(this.label.equals(CriterionName.HOST_FOOD) || this.label.equals(CriterionName.GUEST_FOOD)) {
                    return FOOD_VALUES.contains(this.value);
                }
                return false;
            default:
                return true;
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
}
