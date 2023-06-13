package devoo;

import java.util.Comparator;

public class IncoherenceSort implements Comparator<Teenager> {

    @Override
    public int compare(Teenager t1, Teenager t2) {
        return t1.numIncoherence() - t2.numIncoherence();
    }
    
}
