package devoo;

import java.util.Comparator;
import java.util.Map;

/**
 * Classe AffectListComparator.
 * @author Raphael KIECKEN, Armand SADY, Antoine GAIENIER
 */
public class AffectListComparator implements Comparator<Map.Entry<Teenager,Teenager>> {
    
    /**
     * Compare deux Map entry selon l'id du Teenager clef.
     * @param t1 : Map entry 1
     * @param t2 : Map entry 2
     * @return : int
     */
    @Override
    public int compare(Map.Entry<Teenager,Teenager> t1, Map.Entry<Teenager,Teenager> t2) {
        return t1.getKey().getId() - t2.getKey().getId();
    }
    
}
