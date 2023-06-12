package graphes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import devoo.*;
import fr.ulille.but.sae2_02.graphes.*;
import javafx.util.Pair;

/*
 *  Régle de calcul V1 :
 * 
 * - Si les deux ados ne sont pas compatibles sur le critère des animaux, le poids est de 100.
 * - Si les deux ados on 1 passe temps en commun, le poids est de -1.
 * 
 * @author Raphael Kieken, Armand Sady, Antoine Gaienier
 */

public class AffectationUtil {
    static int weightHistori = 100;
    static int weightAlergi = 100;
    static int weightFood = 100;
    static int weightCountry = 100;
    static int weightGender = 1;
    static int weightHobbies = 1;


    /** Calcule le poids de l’arête entre host et visitor dans le graphe modèle.
    * Doit faire appel à la méthode compatibleWithGuest(Teenager) de Teenager.
    * Peut avoir d’autres paramètres si nécessaire.
    */
    public static double weight (Teenager host, Teenager visitor, Map<Teenager,Teenager> pairFixed) {
        double weight = 0;
        if(!pairFixed.isEmpty()){
            for( Map.Entry<Teenager, Teenager> e : pairFixed.entrySet()){
                if( host.equals(e.getKey()) && visitor.equals(e.getValue())){
                    return -10000;
                }
            }
        }
        
        if (host.criterionIsProperlyDefine(CriterionName.HISTORY) && visitor.criterionIsProperlyDefine(CriterionName.HISTORY)) {
            if (host.hasLastGuest(visitor)) {
                if (!(host.getValue(CriterionName.HISTORY).isBlank() && visitor.getValue(CriterionName.HISTORY).isBlank())) {
                    return host.historyCompatibility(visitor) ? -AffectationUtil.weightHistori : AffectationUtil.weightHistori;
                }
            }
        }
        if (host.criterionIsProperlyDefine(CriterionName.HOST_HAS_ANIMAL) && visitor.criterionIsProperlyDefine(CriterionName.GUEST_ANIMAL_ALLERGY)) {
            if (!host.animalCompatibility(visitor)) {
                return AffectationUtil.weightAlergi;
            }
        }
        if (host.criterionIsProperlyDefine(CriterionName.HOST_FOOD) && visitor.criterionIsProperlyDefine(CriterionName.GUEST_FOOD)) {
            if (!host.foodCompatibility(visitor)) {
                return AffectationUtil.weightFood;
            }
        }
        if (!host.countryCompatibility(visitor)) {
            return AffectationUtil.weightCountry;
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

    public static double advencedWeight (Teenager host, Teenager visitor) {
        double weight = 0;
        if (host.criterionIsProperlyDefine(CriterionName.HISTORY) && visitor.criterionIsProperlyDefine(CriterionName.HISTORY)) {
            if (host.hasLastGuest(visitor)) {
                if (!(host.getValue(CriterionName.HISTORY).isBlank() && visitor.getValue(CriterionName.HISTORY).isBlank())) {
                    weight += host.historyCompatibility(visitor) ? -AffectationUtil.weightHistori : AffectationUtil.weightHistori;
                }
            }
        }
        if (host.criterionIsProperlyDefine(CriterionName.HOST_HAS_ANIMAL) && visitor.criterionIsProperlyDefine(CriterionName.GUEST_ANIMAL_ALLERGY)) {
            if (!host.animalCompatibility(visitor)) {
                weight += AffectationUtil.weightAlergi;
            }
        }
        if (host.criterionIsProperlyDefine(CriterionName.HOST_FOOD) && visitor.criterionIsProperlyDefine(CriterionName.GUEST_FOOD)) {
            if (!host.foodCompatibility(visitor)) {
                weight += AffectationUtil.weightFood;
            }
        }
        if (!host.countryCompatibility(visitor)) {
            weight += AffectationUtil.weightCountry;
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
    public static GrapheNonOrienteValue<Teenager> init(List<Teenager> host, List<Teenager> guest, Map<Teenager,Teenager> pairFixed) {
        GrapheNonOrienteValue<Teenager> g = new GrapheNonOrienteValue<Teenager>();
        for (Teenager t1 : host) {
            g.ajouterSommet(t1);
            for (Teenager t2 : guest) {
                g.ajouterSommet(t2);
                if (t1 != t2 && !g.contientArete(t2, t1)) {
                    g.ajouterArete(t1, t2, weight(t1, t2, pairFixed));
                }
            }
        }
        return g;
    }

    // Retourne une map du cour chemin entre deux sommet
    public static Map<Teenager, Teenager> affectation(List<Teenager> host, List<Teenager> guest, Map<Teenager,Teenager> pairFixed) {
        CalculAffectation<Teenager> calcul = new CalculAffectation<Teenager>(init(host, guest, pairFixed), host, guest);
        Map<Teenager, Teenager> dico = new HashMap<Teenager, Teenager>();
        for (Arete<Teenager> a : calcul.calculerAffectation()) {
            dico.put(a.getExtremite1(), a.getExtremite2());
            a.getExtremite1().setLastguest(a.getExtremite2());
        }
        return dico;
    }



    /*
     * Setters/Getters/resetter pour les poids
     */

    // retourne le poids de l'historique
    public static int getWeightHistori() {
        return AffectationUtil.weightHistori;
    }
    // modifie le poids de l'historique
    public static void setWeightHistori(int weightHistori) {
        AffectationUtil.weightHistori = weightHistori;
    }
    // reset le poids de l'historique
    public static void resetWeightHistori() {
        AffectationUtil.weightHistori = 100;
    }

    // retourne le poids des animaux
    public static int getWeightAlergi() {
        return weightAlergi;
    }
    // modifie le poids des allérgies
    public static void setWeightAlergi(int weightAlergi) {
        AffectationUtil.weightAlergi = weightAlergi;
    }
    // reset le poids des allérgies
    public static void resetWeightAlergi() {
        AffectationUtil.weightAlergi = 100;
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
        AffectationUtil.weightFood = 100;
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
        AffectationUtil.weightCountry = 100;
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
        AffectationUtil.weightGender = 1;
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
        AffectationUtil.weightHobbies = 1;
    }

    // reset tout
    public static void allReset(){
        resetWeightAlergi();
        resetWeightCountry();
        resetWeightFood();
        resetWeightGender();
        resetWeightHistori();
        resetWeightHobbies();
    }

    
}