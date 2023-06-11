package devoo;

import java.util.Comparator;

public class IdComparator implements Comparator<Teenager> {

    public int compare(Teenager t1, Teenager t2) {
        return t1.getId() - t2.getId();
    }
    
}
