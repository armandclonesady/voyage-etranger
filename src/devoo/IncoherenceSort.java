package devoo;

import java.util.Comparator;

/**
 * Classe IncoherenceSort.
 * @author : Raphael KIECKEN, Armand SADY, Antoine GAIENIER
 */
public class IncoherenceSort implements Comparator<Teenager> {

    /**
     * Compare deux Teenager selon leur nombre d'incoherence.
     * @param t1 : Teenager 1
     * @param t2 : Teenager 2
     * @return : int
     */
    @Override
    public int compare(Teenager t1, Teenager t2) {
        return t1.numIncoherence() - t2.numIncoherence();
    }
    
}
