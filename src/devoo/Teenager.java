package devoo;

import java.util.Map;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * Classe Teenager
 * @author Raphael Kiecken, Armand Sady, Antoine Gaienier
 */
public class Teenager {
    private static int count = 1;
    
    private int id;
    private String name;
    private LocalDate birth;
    private Country origin;
    private Teenager lastGuest;

    private Map<CriterionName, Criterion> requirements = new HashMap<CriterionName, Criterion>();

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
        if(this.lastGuest == t && (this.criterionIsProperlyDefine(CriterionName.HISTORY) && t.criterionIsProperlyDefine(Criterion)) {
            String historyHost = this.getValue(CriterionName.HISTORY);
            String historyGuest = t.getValue(CriterionName.HISTORY);
            if(historyHost.equals(Criterion.PREF_SAME) && historyGuest.equals(Criterion.PREF_SAME)) {
                return true;
            } else if (historyHost.equals(Criterion.PREF_OTH) || historyGuest.equals(Criterion.PREF_OTH)) {
                return false;
            }
        }
        if(this.criterionIsProperlyDefine(CriterionName.HOST_HAS_ANIMAL) && t.criterionIsProperlyDefine(CriterionName.GUEST_HAS_ALLERGY)) {
            String hostAnimal = this.getValue(CriterionName.HOST_HAS_ANIMAL);
            String guestAnimal = t.getValue(CriterionName.GUEST_HAS_ALLERGY);
            if((guestAnimal.equals(Criterion.NEG)) || (hostAnimal.equals(Criterion.NEG) && guestAnimal.equals(Criterion.POS))) {
                return true;
            }
        }
        if(this.criterionIsProperlyDefine(CriterionName.HOST_FOOD) && t.criterionIsProperlyDefine(CriterionName.GUEST_FOOD)) {
            ArrayList<String> hostRegime = new ArrayList<String>();
            hostRegime.addAll(Arrays.asList(this.getValue(CriterionName.HOST_FOOD).split(",")));
            ArrayList<String> guestRegime = new ArrayList<String>();
            guestRegime.addAll(Arrays.asList(this.getValue(CriterionName.GUEST_FOOD).split(",")));
            Collections.sort(hostRegime); Collections.sort(guestRegime);
            if((hostRegime.containsAll(guestRegime))) {return true;}
        }
        return false;
    }

    /**
     * méthode qui ajoute un critère
     * @param criterion
     */
    public void addCriterion(Criterion criterion) {
        this.requirements.put(criterion.getLabel(), criterion);
    }
    /**
     * retourne un critère
     * @param label
     * @return
     */
    public Criterion getCriterion(CriterionName label) {
        return this.requirements.get(label);
    }
    /**
     * retourne la valeur d'un critère
     * @param label
     * @return
     */
    public String getValue(CriterionName label) {
        return this.getCriterion(label).getValue();
    }

    public boolean criterionIsProperlyDefine(CriterionName label) {
        return this.requirements.containsKey(label) && this.requirements != null;
    }

    public static void main(String[] args) {
        Teenager teen1 = new Teenager("ratio", null, Country.FRANCE);
        Teenager teen2 = new Teenager("ratio2", null, Country.FRANCE);
        
        teen1.addCriterion(new Criterion("HOST_HAS_ANIMAL", "yes"));
        teen2.addCriterion(new Criterion("GUEST_HAS_ALLERGY", "yes"));

        teen1.addCriterion(new Criterion("HISTORY", "same"));
        teen1.addCriterion(new Criterion("HISTORY", "same"));

        System.out.println(teen1.compatibleWithGuest(teen2));


    }

}