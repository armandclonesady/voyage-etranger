package devoo;

import java.util.Comparator;

/**
 * Classe IdComparator.
 * @author : Raphael KIECKEN, Armand SADY, Antoine GAIENIER
 */
public class IdComparator implements Comparator<Teenager> {

    /**
     * Compare deux Teenager selon leur id.
     * @param t1 : Teenager 1
     * @param t2 : Teenager 2
     * @return : int
     */
    @Override
    public int compare(Teenager t1, Teenager t2) {
        return t1.getId() - t2.getId();
    }
    
}
