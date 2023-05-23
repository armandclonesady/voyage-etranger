package graphes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import devoo.Country;
import devoo.Criterion;
import devoo.Teenager;
import fr.ulille.but.sae2_02.graphes.*;

/*
 *  Régle de calcul V1 :
 * 
 * - Si les deux ados ne sont pas compatibles sur le critère des animaux, le poids est de 100.
 * - Si les deux ados on 1 passe temps en commun, le poids est de -1.
 * 
 * 
 * 
 * @author Raphael Kieken, Armand Sady, Antoine Gaienier
 */

public class AffectationUtil {

    /** Calcule le poids de l’arête entre host et visitor dans le graphe modèle.
    * Doit faire appel à la méthode compatibleWithGuest(Teenager) de Teenager.
    * Peut avoir d’autres paramètres si nécessaire.
    */
    public static double weight (Teenager host, Teenager visitor) {
        int poids= 10;
        if (host.getCountry() == visitor.getCountry()) {
            poids += 10000;
        }
        if(host.historyCompatibility(visitor) == 1){
            return 0;
        }
        if(host.historyCompatibility(visitor) == 0){
            poids += 999;
        }
        poids -= (1 * Teenager.containsAllValuesCriterionName(
            host.splitValues(host.getCriterion(CriterionName.HOBBIES)),
            visitor.splitValues(visitor.getCriterion(CriterionName.HOBBIES))));
        return poids;
    }

    public static GrapheNonOrienteValue<Teenager> init(List<Teenager> host, List<Teenager> guest) {
        GrapheNonOrienteValue<Teenager> g = new GrapheNonOrienteValue<Teenager>();
        for (Teenager t : host) {
            g.ajouterSommet(t);
        }
        for (Teenager t : guest) {
            g.ajouterSommet(t);
        }
        for (Teenager t1 : host) {
            for (Teenager t2 : guest) {
                if (t1 != t2 && !g.contientArete(t2, t1)) {
                    g.ajouterArete(t1, t2, weight(t1, t2));
                }
            }
        }
        return g;
    }

    // Retourne une map du cour chemin entre deux sommets

    public static Map<Teenager, Teenager> compatibilityMap(List<Teenager> host, List<Teenager> guest) {
        CalculAffectation<Teenager> calcul = new CalculAffectation<Teenager>(init(host, guest), host, guest);
        Map<Teenager, Teenager> dico = new HashMap<Teenager, Teenager>();
        
        for (Arete<Teenager> a : calcul.calculerAffectation()) {
            dico.put(a.getExtremite1(), a.getExtremite2());
        }
        return dico;
    }
}