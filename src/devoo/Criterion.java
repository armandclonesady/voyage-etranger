package devoo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Classe Criterion.
 * @author : Raphael KIECKEN, Armand SADY, Antoine GAIENIER
 */
public class Criterion implements Serializable {

    /**
     * Nom du Criterion.
     */
    private CriterionName label;
    /**
     * Valeur du Criterion.
     */
    private String value;

    /**
     * Valeurs yes pour les Criterion HOST_HAS_ANIMAL et GUEST_ANIMAL_ALLERGY.
     */
    final static public String POS = "yes"; 
    /**
     * Valeurs no pour les Criterion HOST_HAS_ANIMAL et GUEST_ANIMAL_ALLERGY.
     */
    final static public String NEG = "no";

    /**
     * Valeurs female pour les Criterion GENDER, PAIR_GENDER.
     */
    final static public String F = "female"; 
    /**
     * Valeurs male pour les Criterion GENDER, PAIR_GENDER.
     */
    final static public String M = "male";
    /**
     * Valeurs other pour les Criterion GENDER, PAIR_GENDER.
     */
    final static public String OTH = "other";

    /**
     *  Valeurs same pour les Criterion HISTORY.
     */
    final static public String PREF_SAME = "same";
    /**
     * Valeurs other pour les Criterion HISTORY.
     */
    final static public String PREF_OTH = "other";

    /**
     * Valeurs nonuts pour les Criterion HOST_FOOD et GUEST_FOOD. 
     */
    final static public String FOOD_N = "nonuts";
    /**
     * Valeurs vegetarian pour les Criterion HOST_FOOD et GUEST_FOOD.
     */
    final static public String FOOD_V = "vegetarian";

    /**
     * Valeurs vide pour les Crierion PAIR_GENDER, HISTORY, HOST_FOOD et GUEST_FOOD.
     */
    final static public String BLANK = "";

    /**
     * Map contenant les valeurs possibles pour chaque Criterion (Criterion, Valeurs possibles)
     */
    final static private Map<CriterionName, ArrayList<String>> POSSIBLE_VALUES = Map.of(
        CriterionName.GUEST_ANIMAL_ALLERGY, new ArrayList<String>(Arrays.asList(POS,NEG)),
        CriterionName.HOST_HAS_ANIMAL, new ArrayList<String>(Arrays.asList(POS,NEG)),
        CriterionName.GENDER, new ArrayList<String>(Arrays.asList(F,M,OTH)),
        CriterionName.PAIR_GENDER, new ArrayList<String>(Arrays.asList(F,M,OTH,BLANK)),
        CriterionName.HISTORY, new ArrayList<String>(Arrays.asList(PREF_SAME,PREF_OTH)),
        CriterionName.GUEST_FOOD, new ArrayList<String>(Arrays.asList(FOOD_N,FOOD_V,BLANK)),
        CriterionName.HOST_FOOD, new ArrayList<String>(Arrays.asList(FOOD_N,FOOD_V,BLANK)));

    /**
     * Constructeur de Criterion (avec un CriterionName en paramètre).
     * @param label : nom du Criterion.
     * @param value : valeur du Criterion.
     */
    public Criterion(CriterionName label, String value) {
        this.label = label;
        this.value = value;
    }

    /**
     * Constructeur de Criterion (avec une String en paramètre). 
     * @param label : nom du Criterion.
     * @param value : valeur du Criterion.
     */
    public Criterion(String label, String value) {
        try {
            this.label = CriterionName.valueOf(label);
            this.value = value;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }  

    /**
     * Méthode retournant la liste des valeurs possibles pour un Criterion donné. 
     * @return ArrayList de String : liste des valeurs possibles.
     */
    public ArrayList<String> getRightValues() {
        return Criterion.POSSIBLE_VALUES.get(this.label);
    }

    /**
     * Méthode qui créé un message d'erreur selon le Criterion.
     * @return String : message d'erreur. 
     */
    public String errorMsg() {
        return String.format("Valeur \"%s\" incorrect | Valeurs possibles (%s) : %s", this.value, this.label.getName(), Arrays.toString(getRightValues().toArray()));
    }
    
    /**
     * Méthode qui vérifie si le Criterion est valide. 
     * @throws CriterionException : exception si le Criterion n'est pas valide.
     */
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

    /**
     * Accesseur qui renvoie le nom du Criterion. 
     * @return CriterionName : nom du Criterion.
     */
    public CriterionName getLabel() {return this.label;}

    /**
     * Accesseur qui renvoie la valeur du Criterion.
     * @return String : valeur du Criterion.
     */
    public String getValue() {return this.value;}

    /**
     * Méthode qui renvoie une String contenant le nom et la valeur du Criterion.
     * @return String : nom et valeur du Criterion.
     */
    @Override
    public String toString() {
        return this.label + " " + this.value;
    }
}
