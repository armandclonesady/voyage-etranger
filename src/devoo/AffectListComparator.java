package devoo;

import java.util.Comparator;
import java.util.Map;

public class AffectListComparator implements Comparator<Map.Entry<Teenager,Teenager>> {
    
    public int compare(Map.Entry<Teenager,Teenager> t1, Map.Entry<Teenager,Teenager> t2) {
        return t1.getKey().getId() - t2.getKey().getId();
    }
    
}
