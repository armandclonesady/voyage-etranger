package devoo;

import java.util.Map;
import java.time.LocalDate;
import java.util.HashMap;

public class Teenager {
    private static int count = 1;
    
    private int id;
    private String name;
    private LocalDate birth;
    private Country origin;
    private Teenager lastGuest;

    private Map<String, Criterion> requirements = new HashMap<String, Criterion>();

    public Teenager(String name, LocalDate birth, Country origin, Teenager lastGuest) {
        this.id = Teenager.count;
        Teenager.count++;
        this.name = name;
        this.birth = birth;
        this.origin = origin;
        this.lastGuest = lastGuest;
    }

    public Teenager(String name, LocalDate birth, Country origin) {
        this(name, birth, origin, null);
    }

    public Teenager(String name, LocalDate birth, String origin, Teenager lastGuest) {
        this(name, birth, Country.valueOf(origin), lastGuest);
    }

    public Teenager(String name, LocalDate birth, String origin) {
        this(name, birth, Country.valueOf(origin), null);
    }

    public boolean compatibleWithGuest(Teenager t) {
        if(this.lastGuest == t) {
            String historyHost = this.getValue("HISTORY");
            String historyGuest = t.getValue("HISTORY");
            if(historyHost.equals(Criterion.PREF_SAME) && historyGuest.equals(Criterion.PREF_SAME)) {
                return true;
            } else if (historyHost.equals(Criterion.PREF_OTH) || historyGuest.equals(Criterion.PREF_OTH)) {
                return false;
            }
        }

        return false;
    }

    public void addCriterion(Criterion criterion) {
        this.requirements.put(criterion.getLabel().getName(), criterion);
    }

    public Criterion getCriterion(String label) {
        return this.requirements.get(label);
    }

    public String getValue(String label) {
        return this.getCriterion(label).getValue();
    }

}
