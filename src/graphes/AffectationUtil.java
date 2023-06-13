package graphes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import devoo.*;
import fr.ulille.but.sae2_02.graphes.*;

/*
 *  Régle de calcul V1 :
 * 
 * - Si les deux ados ne sont pas compatibles sur le critère des animaux, le poids est de 100.
 * - Si les deux ados on 1 passe temps en commun, le poids est de -1.
 * 
 * @author Raphael Kieken, Armand Sady, Antoine Gaienier
 */

public class AffectationUtil {
    static int weightHistory = 100;
    static int weightAllergy = 100;
    static int weightFood = 100;
    static int weightCountry = 100;
    static int weightGender = 1;
    static int weightHobbies = 1;

    final static int PAIR_FIXED_VALUE = -50000;
    final static int PAIR_AVOIDED_VALUE = 50000;

    final static int DEFAULT_HISTORY_VALUE = 100;
    final static int DEFAULT_ALLERGY_VALUE = 100;
    final static int DEFAULT_FOOD_VALUE = 100;
    final static int DEFAULT_COUNTRY_VALUE = 100;
    final static int DEFAULT_GENDER_VALUE = 1;
    final static int DEFAULT_HOBBIES_VALUE = 1;


    /** Calcule le poids de l’arête entre host et visitor dans le graphe modèle.
    * Doit faire appel à la méthode compatibleWithGuest(Teenager) de Teenager.
    * Peut avoir d’autres paramètres si nécessaire.
    */
    public static double weight (Teenager host, Teenager visitor, Map<Teenager,Teenager> pairFixed, Map<Teenager,Teenager> pairAvoided) {
        double weight = 0;
        if (!pairFixed.isEmpty()){
            if (pairFixed.containsKey(host) && pairFixed.get(host).equals(visitor)){
                return AffectationUtil.PAIR_FIXED_VALUE;
            }
        }
        if (!pairAvoided.isEmpty()){
            if (pairAvoided.containsKey(host) && pairAvoided.get(host).equals(visitor)){
                return AffectationUtil.PAIR_AVOIDED_VALUE;
            }
        }
        if (host.criterionIsProperlyDefine(CriterionName.HISTORY) && visitor.criterionIsProperlyDefine(CriterionName.HISTORY)) {
            if (host.hasLastGuest(visitor)) {
                if (host.historyCompatibility(visitor) != -1) {
                    return host.historyCompatibility(visitor) == 1 ? -AffectationUtil.weightHistory : AffectationUtil.weightHistory;
                }
            }
        }
        if (host.criterionIsProperlyDefine(CriterionName.HOST_HAS_ANIMAL) && visitor.criterionIsProperlyDefine(CriterionName.GUEST_ANIMAL_ALLERGY)) {
            if (!host.animalCompatibility(visitor)) {
                return AffectationUtil.weightAllergy;
            }
        }
        if (host.criterionIsProperlyDefine(CriterionName.HOST_FOOD) && visitor.criterionIsProperlyDefine(CriterionName.GUEST_FOOD)) {
            if (!host.foodCompatibility(visitor)) {
                return AffectationUtil.weightFood;
            }
        }
        if (host instanceof FrenchTeenager) {
            if (!((FrenchTeenager) host).countryCompatibility(visitor)) {
                weight += AffectationUtil.weightCountry;
            }
        }
        if (host.criterionIsProperlyDefine(CriterionName.GENDER) && visitor.criterionIsProperlyDefine(CriterionName.PAIR_GENDER)) {
            if (host.getValue(CriterionName.GENDER).equals(visitor.getValue(CriterionName.PAIR_GENDER))) weight -= AffectationUtil.weightGender;
            else weight += AffectationUtil.weightGender;
        }
        if (host.criterionIsProperlyDefine(CriterionName.HOBBIES) && visitor.criterionIsProperlyDefine(CriterionName.HOBBIES)) {
            weight -= (AffectationUtil.weightHobbies * Teenager.containsAllValuesCriterionName (
            host.splitValues(host.getCriterion(CriterionName.HOBBIES)),
            visitor.splitValues(visitor.getCriterion(CriterionName.HOBBIES))));
        }

        return weight;
    }

    /** Calcule le poids de l’arête entre host et visitor dans le graphe modèle de manière avencer.
    * Doit faire appel à la méthode compatibleWithGuest(Teenager) de Teenager.
    * Peut avoir d’autres paramètres si nécessaire.
    */

    public static double advanceWeight (Teenager host, Teenager visitor, Map<Teenager,Teenager> pairFixed, Map<Teenager,Teenager> pairAvoided) {
        double weight = 0;
        if(!pairFixed.isEmpty()){
            if (pairFixed.containsKey(host) && pairFixed.get(host).equals(visitor)){
                weight += AffectationUtil.PAIR_FIXED_VALUE;
            }
        }
        if (!pairAvoided.isEmpty()){
            if (pairAvoided.containsKey(host) && pairAvoided.get(host).equals(visitor)){
                weight += AffectationUtil.PAIR_AVOIDED_VALUE;
            }
        }
        if (host.criterionIsProperlyDefine(CriterionName.HISTORY)) {
            if (host.hasLastGuest(visitor)) {
                if (host.historyCompatibility(visitor) != -1) {
                    weight += host.historyCompatibility(visitor) == 1 ? -AffectationUtil.weightHistory : AffectationUtil.weightHistory;
                }
            }
        }
        if (host.criterionIsProperlyDefine(CriterionName.HOST_HAS_ANIMAL) && visitor.criterionIsProperlyDefine(CriterionName.GUEST_ANIMAL_ALLERGY)) {
            if (!host.animalCompatibility(visitor)) {
                weight += AffectationUtil.weightAllergy;
            }
        }
        if (host.criterionIsProperlyDefine(CriterionName.HOST_FOOD) && visitor.criterionIsProperlyDefine(CriterionName.GUEST_FOOD)) {
            if (!host.foodCompatibility(visitor)) {
                weight += AffectationUtil.weightFood;
            }
        }
        if (host instanceof FrenchTeenager) {
            if (!((FrenchTeenager) host).countryCompatibility(visitor)) {
                weight += AffectationUtil.weightCountry;
            }
        }
        if(host.genderPref(visitor)== -1){
            weight += AffectationUtil.weightGender;
        }
        if(host.genderPref(visitor)== 1){
            weight -= AffectationUtil.weightGender *2;
        }
        if( host.genderPref(visitor)== 0){
            weight -= AffectationUtil.weightGender;
        }
        if (host.criterionIsProperlyDefine(CriterionName.HOBBIES) && visitor.criterionIsProperlyDefine(CriterionName.HOBBIES)) {
            weight -= (AffectationUtil.weightHobbies * Teenager.containsAllValuesCriterionName (
            host.splitValues(host.getCriterion(CriterionName.HOBBIES)),
            visitor.splitValues(visitor.getCriterion(CriterionName.HOBBIES))));
        }
        return weight;
    }

    // Création du graphe
    public static GrapheNonOrienteValue<Teenager> init(List<Teenager> host, List<Teenager> guest, Map<Teenager,Teenager> pairFixed, Map<Teenager,Teenager> pairAvoided) {
        GrapheNonOrienteValue<Teenager> g = new GrapheNonOrienteValue<Teenager>();      
        for (Teenager t1 : host) {
            g.ajouterSommet(t1);
            for (Teenager t2 : guest) {
                g.ajouterSommet(t2);
                if (t1 != t2 && !g.contientArete(t2, t1)) {
                    g.ajouterArete(t1, t2, advanceWeight(t1, t2, pairFixed, pairAvoided));
                }
            }
        }
        return g;
    }

    // Retourne une map du cour chemin entre deux sommet
    public static Map<Teenager, Teenager> affectation(List<Teenager> host, List<Teenager> guest, Map<Teenager,Teenager> pairFixed, Map<Teenager,Teenager> pairAvoided) {
        CalculAffectation<Teenager> calcul = new CalculAffectation<Teenager>(init(host, guest, pairFixed, pairAvoided), host, guest);
        Map<Teenager, Teenager> dico = new HashMap<Teenager, Teenager>();
        for (Arete<Teenager> a : calcul.calculerAffectation()) {
            dico.put(a.getExtremite1(), a.getExtremite2());
        }
        return dico;
    }

    /*
     * Setters/Getters/resetter pour les poids
     */

    // retourne le poids de l'historique
    public static int getWeightHistory() {
        return AffectationUtil.weightHistory;
    }
    // modifie le poids de l'historique
    public static void setWeightHistory(int weightHistory) {
        AffectationUtil.weightHistory = weightHistory;
    }
    // reset le poids de l'historique
    public static void resetWeightHistory() {
        AffectationUtil.weightHistory = AffectationUtil.DEFAULT_HISTORY_VALUE;
    }

    // retourne le poids des animaux
    public static int getWeightAllergy() {
        return weightAllergy;
    }
    // modifie le poids des allérgies
    public static void setWeightAllergy(int weightAllergy) {
        AffectationUtil.weightAllergy = weightAllergy;
    }
    // reset le poids des allérgies
    public static void resetWeightAllergy() {
        AffectationUtil.weightAllergy = AffectationUtil.DEFAULT_ALLERGY_VALUE;
    }

    // retourne le poids du pays
    public static int getWeightCountry() {
        return weightCountry;
    }
    // modifie le poids du pays
    public static void setWeightCountry(int weightCountry) {
        AffectationUtil.weightCountry = weightCountry;
    }
    // reset le poids du pays
    public static void resetWeightCountry() {
        AffectationUtil.weightCountry = AffectationUtil.DEFAULT_COUNTRY_VALUE;
    }

    // retourne le poids du regime alimentaire
    public static int getWeightFood() {
        return weightFood;
    }
    // modifie le poids du regime alimentaire
    public static void setWeightFood(int weightFood) {
        AffectationUtil.weightFood = weightFood;
    }
    // reset le poids du regime alimentaire
    public static void resetWeightFood() {
        AffectationUtil.weightFood = AffectationUtil.DEFAULT_FOOD_VALUE;
    }
    // retourne le poids du genre
    public static int getWeightGender() {
        return weightGender;
    }
    // modifie le poids du genre
    public static void setWeightGender(int weightGender) {
        AffectationUtil.weightGender = weightGender;
    }
    // reset le poids du genre
    public static void resetWeightGender() {
        AffectationUtil.weightGender = AffectationUtil.DEFAULT_GENDER_VALUE;
    }
    // retourne le poids des hobbies
    public static int getWeightHobbies() {
        return weightHobbies;
    }
    // modifie le poids des hobbies
    public static void setWeightHobbies(int weightHobbies) {
        AffectationUtil.weightHobbies = weightHobbies;
    }
    // reset le poids des hobbies
    public static void resetWeightHobbies() {
        AffectationUtil.weightHobbies = AffectationUtil.DEFAULT_HOBBIES_VALUE;
    }

    // reset tout
    public static void allReset(){
        resetWeightHistory();
        resetWeightAllergy();
        resetWeightCountry();
        resetWeightFood();
        resetWeightGender();
        resetWeightHobbies();
    }

    
}