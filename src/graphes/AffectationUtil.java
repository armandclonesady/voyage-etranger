package graphes;

import java.util.ArrayList;
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

    /** Calcule le poids de l’arête entre host et visitor dans le graphe modèle.
    * Doit faire appel à la méthode compatibleWithGuest(Teenager) de Teenager.
    * Peut avoir d’autres paramètres si nécessaire.
    */
    public static double weight (Teenager host, Teenager visitor) {
        double weight = 0;
        if (host.criterionIsProperlyDefine(CriterionName.HISTORY) && visitor.criterionIsProperlyDefine(CriterionName.HISTORY)) {
            if (host.hasLastGuest(visitor)) {
                if (!(host.getValue(CriterionName.HISTORY).isBlank() && visitor.getValue(CriterionName.HISTORY).isBlank())) {
                    return host.historyCompatibility(visitor) ? -100 : 100;
                }
            }
        }
        if (host.criterionIsProperlyDefine(CriterionName.HOST_HAS_ANIMAL) && visitor.criterionIsProperlyDefine(CriterionName.GUEST_ANIMAL_ALLERGY)) {
            if (!host.animalCompatibility(visitor)) {
                return 100;
            }
        }
        if (host.criterionIsProperlyDefine(CriterionName.HOST_FOOD) && visitor.criterionIsProperlyDefine(CriterionName.GUEST_FOOD)) {
            if (!host.foodCompatibility(visitor)) {
                return 100;
            }
        }
        if (!host.countryCompatibility(visitor)) {
            return 100;
        }
        if (host.criterionIsProperlyDefine(CriterionName.GENDER) && visitor.criterionIsProperlyDefine(CriterionName.PAIR_GENDER)) {
            if (host.getValue(CriterionName.GENDER).equals(visitor.getValue(CriterionName.PAIR_GENDER))) weight -= 1;
        }
        if (host.criterionIsProperlyDefine(CriterionName.HOBBIES) && visitor.criterionIsProperlyDefine(CriterionName.HOBBIES)) {
            weight -= (1 * Teenager.containsAllValuesCriterionName (
            host.splitValues(host.getCriterion(CriterionName.HOBBIES)),
            visitor.splitValues(visitor.getCriterion(CriterionName.HOBBIES))));
        }

        return weight;
    }

    public static GrapheNonOrienteValue<Teenager> init(List<Teenager> host, List<Teenager> guest) {
        GrapheNonOrienteValue<Teenager> g = new GrapheNonOrienteValue<Teenager>();
        for (Teenager t1 : host) {
            g.ajouterSommet(t1);
            for (Teenager t2 : guest) {
                g.ajouterSommet(t2);
                if (t1 != t2 && !g.contientArete(t2, t1)) {
                    g.ajouterArete(t1, t2, weight(t1, t2));
                }
            }
        }
        return g;
    }

    // Retourne une map du cour chemin entre deux sommet
    public static Map<Teenager, Teenager> affectation(List<Teenager> host, List<Teenager> guest) {
        CalculAffectation<Teenager> calcul = new CalculAffectation<Teenager>(init(host, guest), host, guest);
        Map<Teenager, Teenager> dico = new HashMap<Teenager, Teenager>();
        for (Arete<Teenager> a : calcul.calculerAffectation()) {
            dico.put(a.getExtremite1(), a.getExtremite2());
            a.getExtremite1().setLastguest(a.getExtremite2());
        }
        return dico;
    }
}