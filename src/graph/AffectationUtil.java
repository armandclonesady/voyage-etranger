package graph;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import devoo.Country;
import devoo.Criterion;
import devoo.Teenager;
import devoo.Platform;
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
        if (host.getCountry() != visitor.getCountry()) {
            poids += 10000;
        }
        if( !host.animalCompatibility(visitor) ) {
            poids += 100;
        }
        return poids;
    }

    public static GrapheNonOrienteValue<Teenager> init(Set<Teenager> promo) {
        GrapheNonOrienteValue<Teenager> g = new GrapheNonOrienteValue<Teenager>();
        for (Teenager t : promo) {
            g.ajouterSommet(t);
        }
        for (Teenager t1 : promo) {
            for (Teenager t2 : promo) {
                if (t1 != t2) {
                    g.ajouterArete(t1, t2, weight(t1, t2));
                }
            }
        }
        System.out.println(g);
        return g;
    }

    public static void main(String[] args) {
        Set<Teenager> promo = new HashSet<Teenager>();
        Teenager t1, t2, t3, t4, t5, t6;

        t1= new Teenager("A", LocalDate.of(2000, 1, 1),Country.FRANCE);
        t2= new Teenager("B", LocalDate.of(2000, 1, 1),Country.FRANCE);
        t3= new Teenager("C", LocalDate.of(2000, 1, 1),Country.FRANCE);
        t4= new Teenager("X", LocalDate.of(2000, 1, 1),Country.ITALY);
        t5= new Teenager("Y", LocalDate.of(2000, 1, 1),Country.ITALY);
        t6= new Teenager("Z", LocalDate.of(2000, 1, 1),Country.ITALY);

        t1.updateCriterion(new Criterion("GUEST_HAS_ALLERGY", "no"));
        t2.updateCriterion(new Criterion("GUEST_HAS_ALLERGY", "yes"));
        t3.updateCriterion(new Criterion("GUEST_HAS_ALLERGY", "no"));
        t4.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "no"));
        t5.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "yes"));
        t6.updateCriterion(new Criterion("HOST_HAS_ANIMAL", "no"));

        promo.add(t1);
        promo.add(t2);
        promo.add(t3);
        promo.add(t4);
        promo.add(t5);
        promo.add(t6);

        System.out.println(init(promo));
        }
    }

    /*
     * revoie une arrayList contenant tout les host et le visitor le plus compatible avec lui; 
     */

    /*public ArrayList<Teenager> meilleur_poid(ArrayList<Teenager> host, ArrayList<Teenager> guest){
        ArrayList<Teenager> meilleur_poid = new ArrayList<Teenager>();
        for (Teenager h : host) {
            for (Teenager g : guest) {
                if( weight(h, g)<100000){
                    meilleur_poid.add(h,g,weight(h,g));
                }
                else if(weight(h,g)< meilleur_poid){
                    meilleur_poid.remove(-1);
                    meilleur_poid.add(h,g,weight(h,g));
                }

                        
        }
        return meilleur_poid;
    }*/
        

    // ... ajouter toutes autres méthodes jugées nécessaires
}