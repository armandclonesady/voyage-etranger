package devoo;

import java.util.Map;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * Classe Teenager
 * 
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
     * @return Un boolean qui indique si le Teenager est compatible
     */
    public boolean compatibleWithGuest(Teenager t) {
        if(this.lastGuest == t) {
            if(historyCompatibilty(t) != -1) {
                return historyCompatibilty(t) == 0 ? true : false;
            }
        }
        String hostAnimal = this.getValue("HOST_HAS_ANIMAL");
        String guestAnimal = this.getValue("GUEST_HAS_ALLERGY");
        if((guestAnimal.equals(Criterion.NEG)) || (hostAnimal.equals(Criterion.NEG) && guestAnimal.equals(Criterion.POS))) {
            return true;
        }
        ArrayList<String> hostRegime = new ArrayList<String>();
        hostRegime.addAll(Arrays.asList(this.getValue("HOST_FOOD").split(",")));

        ArrayList<String> guestRegime = new ArrayList<String>();
        guestRegime.addAll(Arrays.asList(this.getValue("GUEST_FOOD").split(",")));

        Collections.sort(hostRegime);
        Collections.sort(guestRegime);

        if((hostRegime.containsAll(guestRegime))) {return true;}
        return false;
    }

    public int historyCompatibilty(Teenager t) {
        String historyHost = this.getValue("HISTORY");
        String historyGuest = t.getValue("HISTORY");
        if(historyHost.equals(Criterion.PREF_SAME) && historyGuest.equals(Criterion.PREF_SAME)) {
            return 1;
        } else if (historyHost.equals(Criterion.PREF_OTH) || historyGuest.equals(Criterion.PREF_OTH)) {
            return 0;
        }
        return -1;
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

    public static void main(String[] args) {
        Teenager teen1 = new Teenager("ratio", null, Country.FRANCE);
        Teenager teen2 = new Teenager("ratio2", null, Country.FRANCE);
        
        teen1.addCriterion(new Criterion("HOST_HAS_ANIMAL", "yes"));
        teen2.addCriterion(new Criterion("GUEST_HAS_ALLERGY", "no"));

        System.out.println(teen1.compatibleWithGuest(teen2));


    }

}