package graph;

import devoo.Country;
import devoo.Teenager;
import devoo.Platform;
import fr.ulille.grapp.graph.ImplicitGraph;

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

public class AffectationUtil implements ImplicitGraph {

    public String type(){
        return "digraph";
    }
    public String name(Country host, Country visitor){
        return "Affectation " + host + " " + visitor;
    }
    /* 
     * retourne la liste des sommets du graphe modèle.
     * Chaque sommet est un objet de type Teenager.
     * qui vienne du hasmap de Teenager de Platform
     */

    public List<Teenager> intialNodes(){
        List<Teenager> list = new ArrayList<Teenager>();
        for (Teenager t : Platform.getTeenagers().values()) {
            int id = t.getId();
            list.add(id);
        }
        return list;
    }


    /** Calcule le poids de l’arête entre host et visitor dans le graphe modèle.
    * Doit faire appel à la méthode compatibleWithGuest(Teenager) de Teenager.
    * Peut avoir d’autres paramètres si nécessaire.
    */
    public static double weight (Teenager host, Teenager visitor) {
        int poids= 0;
        if (host.getCountry() != visitor.getCountry()) {
            poids += 10000;
        }
        if( !host.animalCompatibility(visitor) ) {
            poids += 100;
        }

        return 0;
    }
    // ... ajouter toutes autres méthodes jugées nécessaires
}