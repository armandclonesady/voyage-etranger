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
        if( !host.animalCompatibility(visitor) ) {
            poids += 100;
        }
        // poids -= host.numberOfCommonHobbies(visitor);
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

    public static void main(String[] args) {
        Teenager t1, t2, t3, t4, t5, t6;

        t1= new Teenager("A", LocalDate.of(2000, 1, 1),Country.FRANCE);
        t2= new Teenager("B", LocalDate.of(2000, 1, 1),Country.FRANCE);
        t3= new Teenager("C", LocalDate.of(2000, 1, 1),Country.FRANCE);
        t4= new Teenager("X", LocalDate.of(2000, 1, 1),Country.ITALY);
        t5= new Teenager("Y", LocalDate.of(2000, 1, 1),Country.ITALY);
        t6= new Teenager("Z", LocalDate.of(2000, 1, 1),Country.ITALY);

        t1.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "no"));
        t2.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "yes"));
        t3.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "no"));
        t1.updateCriterion(new Criterion("GUEST_HAS_ALLERGY", "no"));
        t2.updateCriterion(new Criterion("GUEST_HAS_ALLERGY", "yes"));
        t3.updateCriterion(new Criterion("GUEST_HAS_ALLERGY", "no"));
        t4.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "no"));
        t5.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "yes"));
        t6.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "no"));
        t4.updateCriterion(new Criterion("GUEST_HAS_ALLERGY", "no"));
        t5.updateCriterion(new Criterion("GUEST_HAS_ALLERGY", "yes"));
        t6.updateCriterion(new Criterion("GUEST_HAS_ALLERGY", "no"));

        List<Teenager> host = new ArrayList<Teenager>();
        host.add(t1);
        host.add(t2);
        host.add(t3);

        List<Teenager> guest = new ArrayList<Teenager>();
        guest.add(t4);
        guest.add(t5);
        guest.add(t6);

        System.out.println(init(host, guest)+ "\n");
        System.out.println(compatibilityMap(host,guest));
    }
}