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
        initRequirements();
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

    public void initRequirements() {
        for(CriterionName c : CriterionName.values()) {
            this.requirements.put(c, null);
        }
    }

    /**
     * Vérifie si le Teenager est compatible avec un autre Teenager
     * @param t
     * @return Un boolean qui indique si le Teenager est compatible
     */
    public boolean compatibleWithGuest(Teenager t) {
        /*if((this.criterionIsProperlyDefine(CriterionName.HISTORY) && t.criterionIsProperlyDefine(CriterionName.HISTORY))) {
            int historyCompatibility = historyCompatibility(t);
            if(historyCompatibility != -1) {
                return historyCompatibility == 1 ? true : false;
            }
        }*/
        if(this.criterionIsProperlyDefine(CriterionName.HOST_HAS_ANIMAL) && t.criterionIsProperlyDefine(CriterionName.GUEST_HAS_ALLERGY)) {
            boolean animalCompatibility = animalCompatibility(t);
            if(!animalCompatibility) {
                return animalCompatibility;
            }
        }
        if(this.criterionIsProperlyDefine(CriterionName.HOST_FOOD) && t.criterionIsProperlyDefine(CriterionName.GUEST_FOOD)) {
            boolean foodCompatibility = foodCompatibility(t);
            if(!foodCompatibility) {
                return foodCompatibility;
            }
        }
        return true;
    }
    /**
     * Vérifie si le Teenager est compatible avec un autre Teenager sur le critère de l'historique
     */
    /*
    public int historyCompatibility(Teenager t) {
        if(this.lastGuest == t) {
            String historyHost = this.getValue(CriterionName.HISTORY);
            String historyGuest = t.getValue(CriterionName.HISTORY);
            if(historyHost.equals(Criterion.PREF_SAME) && historyGuest.equals(Criterion.PREF_SAME)) {
                return 1;
            } else if (historyHost.equals(Criterion.PREF_OTH) || historyGuest.equals(Criterion.PREF_OTH)) {
                return 0;
            }
        }
        return -1;
    }
    */

    /**
     * Vérifie si le Teenager est compatible avec un autre Teenager sur le critère des animaux
     * @param t
     */
    public boolean animalCompatibility(Teenager t) {
        String hostAnimal = this.getValue(CriterionName.HOST_HAS_ANIMAL);
        String guestAnimal = t.getValue(CriterionName.GUEST_HAS_ALLERGY);
        return !(hostAnimal.equals("yes") && hostAnimal == guestAnimal);
    }

    /**
     * Vérifie si le Teenager est compatible avec un autre Teenager sur le critère du régime alimentaire
     * @param t
     */
    public boolean foodCompatibility(Teenager t) {
        if(t.getValue(CriterionName.GUEST_FOOD).equals("")) return true;
        ArrayList<String> hostRegime = new ArrayList<String>();
        hostRegime.addAll(Arrays.asList(this.getValue(CriterionName.HOST_FOOD).split(",")));

        ArrayList<String> guestRegime = new ArrayList<String>();
        guestRegime.addAll(Arrays.asList(t.getValue(CriterionName.GUEST_FOOD).split(",")));

        Collections.sort(hostRegime); Collections.sort(guestRegime);

        if(!hostRegime.containsAll(guestRegime)) return false;
        return true;
    }

    /**
    * Permet de mettre ajour un critère
    * @param label
    * @param criterion
    */
    public void updateCriterion(CriterionName label, Criterion criterion) {
        this.requirements.put(label, criterion);
    }

    /**
     * méthode de surchargée de updateCriterion
     * @param criterion
     */
    public void updateCriterion(Criterion criterion) {
        this.updateCriterion(criterion.getLabel(), criterion);;
    }

    /**
     * retourne un critère
     * @param label
     * @return
     */
    public Criterion getCriterion(CriterionName label) {
        return this.requirements.get(label);
    }

    public Map<CriterionName, Criterion> getRequirements() {
        return this.requirements;
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
        return this.requirements.containsKey(label) && this.getCriterion(label) != null;
    }

    /**
     * Si le Criterion n'est pas valable selon Criterion.isValid(), sa valuer dans la Map deviens null.
     * Si il est déjà null, on l'ignore.
     */
    public void purgeCriterion() {
        for (Map.Entry<CriterionName, Criterion> critere : this.requirements.entrySet()) {
            if (critere.getValue() != null && !critere.getValue().isValid()) {
                this.updateCriterion(critere.getValue().getLabel(), null);
            }
        }
    }

    /**
     * Retourne l'origine du Teenager
     * @return
     */

    public Country getCountry() {
         return this.origin;
    }
    /*
     * Retourne l'id du Teenager
     * @return
     */
    public int getId() {
        return this.id;
    

}