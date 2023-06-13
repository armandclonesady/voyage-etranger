package devoo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Classe Criterion.
 * @author : Raphael Kiecken, Armand Sady, Antoine Gaienier
 */
public class Criterion implements Serializable {

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

    /* Valeurs possible pour le critéres lié à l'Historique */
    final static public String PREF_SAME = "same";
    final static public String PREF_OTH = "other";

    /* Valeurs possible pour les critéres lié au Regime alimentaire */
    final static public String FOOD_N = "nonuts";
    final static public String FOOD_V = "vegetarian";
    final static public String FOOD_BLANK = "";

    /* Dictionnaire des valeurs possibles pour chaque critéres (Critére, Valeurs possibles) */
    final static private Map<CriterionName, ArrayList<String>> possibleValues = Map.of(
        CriterionName.GUEST_ANIMAL_ALLERGY, new ArrayList<String>(Arrays.asList(POS,NEG)),
        CriterionName.HOST_HAS_ANIMAL, new ArrayList<String>(Arrays.asList(POS,NEG)),
        CriterionName.GENDER, new ArrayList<String>(Arrays.asList(F,M,OTH)),
        CriterionName.PAIR_GENDER, new ArrayList<String>(Arrays.asList(F,M,OTH,BLANK)),
        CriterionName.HISTORY, new ArrayList<String>(Arrays.asList(PREF_SAME,PREF_OTH)),
        CriterionName.GUEST_FOOD, new ArrayList<String>(Arrays.asList(FOOD_N,FOOD_V,FOOD_BLANK)),
        CriterionName.HOST_FOOD, new ArrayList<String>(Arrays.asList(FOOD_N,FOOD_V,FOOD_BLANK)));

    /* Constructeur de Criterion (avec un CriterionName en paramètre). */
    public Criterion(CriterionName label, String value) {
        this.label = label;
        this.value = value;
    }

    /* Constructeur de Criterion (avec une String en paramètre). */
    public Criterion(String label, String value) {
        this(CriterionName.valueOf(label), value);
    }  

    /* Méthode retournant la liste des valeurs possibles pour un critère donné. */
    public ArrayList<String> getRightValues() {
        return possibleValues.get(this.label);
    }

    /* Méthode qui créé un message d'erreur et le renvoie. */
    public String errorMsg() {
        return String.format("Valeur \"%s\" incorrect | Valeurs possibles (%s) : %s", this.value, this.label.getName(), Arrays.toString(getRightValues().toArray()));
    }
    
    /* Méthode qui vérifie si le critère est valide. */
    public void isValid() throws CriterionException {
        switch (this.label.getType()) {
            case 'B':
                if (!getRightValues().contains(this.value)) {
                    throw new CriterionException(errorMsg());
                }
            case 'T':
                if ((this.label == CriterionName.HOBBIES)) {
                    return;
                }
                if((this.label == CriterionName.HOST_FOOD) || this.label == CriterionName.GUEST_FOOD) {
                    if (!getRightValues().containsAll(Arrays.asList(this.value.split(",")))) {
                        throw new CriterionException(errorMsg());
                    }
                }
                else if (!getRightValues().contains(this.value)) {
                    throw new CriterionException(errorMsg());
                }
        }
    }

    /* Méthode qui renvoie le label du critère. */
    public CriterionName getLabel() {
        return this.label;
    }

    /* Méthode qui renvoie la value. */
    public String getValue() {
        return this.value;
    }

    /* Méthode qui renvoie une String contenant le label et la value du critère. */
    public String toString() {
        return this.label + " " + this.value;
    }
}
