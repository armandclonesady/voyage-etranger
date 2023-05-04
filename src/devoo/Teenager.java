package devoo;

import java.util.Map;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * Classe Teenager
 */
public class Teenager {
    private static int count = 1;
    
    private int id;
    private String name;
    private LocalDate birth;
    private Country origin;
    private Teenager lastGuest;

    private Map<String, Criterion> requirements = new HashMap<String, Criterion>();

    /**
     * Constructeur de Teenager
     * @param name
     * @param birth
     * @param origin
     * @param lastGuest
     */
    public Teenager(String name, LocalDate birth, Country origin, Teenager lastGuest) {
        this.id = Teenager.count;
        Teenager.count++;
        this.name = name;
        this.birth = birth;
        this.origin = origin;
        this.lastGuest = lastGuest;
    }
    /**
     * Constructeur de Teenager chaîner
     * @param name
     * @param birth
     * @param origin
     */
    public Teenager(String name, LocalDate birth, Country origin) {
        this(name, birth, origin, null);
    }

    /**
     * Constructeur de Teenager chaîner
     * @param name
     * @param birth
     * @param origin
     * @param lastGuest
     */
    public Teenager(String name, LocalDate birth, String origin, Teenager lastGuest) {
        this(name, birth, Country.valueOf(origin), lastGuest);
    }

    /**
     * Constructeur de Teenager chaîner
     * @param name
     * @param birth
     * @param origin
     */
    public Teenager(String name, LocalDate birth, String origin) {
        this(name, birth, Country.valueOf(origin), null);
    }

    /**
     * Vérifie si le Teenager est compatible avec un autre Teenager
     * @param t
     * @return
     */
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
    /**
     * méthode qui ajoute un critère
     * @param criterion
     */
    public void addCriterion(Criterion criterion) {
        this.requirements.put(criterion.getLabel().getName(), criterion);
    }
    /**
     * retourne un critère
     * @param label
     * @return
     */
    public Criterion getCriterion(String label) {
        return this.requirements.get(label);
    }
    /**
     * retourne la valeur d'un critère
     * @param label
     * @return
     */
    public String getValue(String label) {
        return this.getCriterion(label).getValue();
    }

}
