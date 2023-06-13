package devoo;

import java.util.Map;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Classe Teenager
 * @author Raphael Kiecken, Armand Sady, Antoine Gaienier
 */
public class Teenager implements Serializable {
    private static int count = 1;
    
    private int id;
    private String forename;
    private String name;
    private LocalDate birth;
    private Country pays;
    private Teenager lastGuest;

    private boolean isRegistered = true; 

    private Map<CriterionName, Criterion> requirements;

    /* Constructeur de Teenager (forename, name, pays, birth, lastGuest). */
    public Teenager(String forename, String name, Country pays, LocalDate birth, Teenager lastGuest) {
        this.id = Teenager.count;
        Teenager.count++;
        this.forename = forename;
        this.name = name;
        this.pays = pays;
        this.birth = birth;
        this.lastGuest = lastGuest;
        initRequirements();
    }

    /* Constructeur de Teenager (forename, name, pays, birth). */
    public Teenager(String forename, String name, Country pays, LocalDate birth) {
        this(forename, name, pays, birth, null);
    }

    /* Constructeur de Teenager (forename, name, pays, birth, lastGuest) avec une chaîne de caractères pour le pays. */
    public Teenager(String forename, String name, String pays, LocalDate birth, Teenager lastGuest) {
        this(forename, name, Country.valueOf(pays), birth, lastGuest);
    }

    /* Constructeur de Teenager (forename, name, pays, birth) avec une chaîne de caractères pour le pays. */
    public Teenager(String forename, String name, String pays, LocalDate birth) {
        this(forename, name, Country.valueOf(pays), birth, null);
    }

    public void initRequirements() {
        this.requirements = new HashMap<CriterionName, Criterion>();
        for(CriterionName c : CriterionName.values()) {
            this.requirements.put(c, null);
        }
    }

    /* Vérifie si le Teenager est compatible avec un autre Teenager. */
    public boolean compatibleWithGuest(Teenager t) {
        if((this.criterionIsProperlyDefine(CriterionName.HISTORY) && t.criterionIsProperlyDefine(CriterionName.HISTORY)) && this.hasLastGuest(t)) {
            if (!(this.getValue(CriterionName.HISTORY).isBlank() && t.getValue(CriterionName.HISTORY).isBlank())) {
                return historyCompatibility(t);
            }
        }
        if(this.criterionIsProperlyDefine(CriterionName.HOST_HAS_ANIMAL) && t.criterionIsProperlyDefine(CriterionName.GUEST_ANIMAL_ALLERGY)) {
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

    public static int diffAge(Teenager t1, Teenager t2) {
        if (t1.birth.isBefore(t2.birth)) {
            return t1.birth.getYear() - t2.birth.getYear();
        }
        return t2.birth.getYear() - t1.birth.getYear();
    }

    /* Vérifie si le Teenager est compatible avec un autre Teenager (Critère de l'historique). */
    public boolean historyCompatibility(Teenager t) {
        String historyHost = this.getValue(CriterionName.HISTORY);
        String historyGuest = t.getValue(CriterionName.HISTORY);
        if(historyHost.equals(Criterion.PREF_SAME) && historyGuest.equals(Criterion.PREF_SAME)) {
            return true;
        } else {
            return false;
        }
    }

    /* Vérifie si le Teenager est compatible avec un autre Teenager (Critère des animaux). */
    public boolean animalCompatibility(Teenager t) {
        String hostAnimal = this.getValue(CriterionName.HOST_HAS_ANIMAL);
        String guestAnimal = t.getValue(CriterionName.GUEST_ANIMAL_ALLERGY);
        return !(hostAnimal.equals("yes") && hostAnimal == guestAnimal);
    }

    /* Vérifie si le Teenager est compatible avec un autre Teenager (Critère des régimes alimentaires). */
    public boolean foodCompatibility(Teenager t) {
        if(t.getValue(CriterionName.GUEST_FOOD).equals("")) return true;
        ArrayList<String> hostRegime = new ArrayList<String>(splitValues(this.getCriterion(CriterionName.HOST_FOOD)));
        ArrayList<String> guestRegime = new ArrayList<String>(splitValues(t.getCriterion(CriterionName.GUEST_FOOD)));

        Collections.sort(hostRegime); Collections.sort(guestRegime);

        return (containsAllValuesCriterionName(hostRegime, guestRegime)) == guestRegime.size();
    }

    public List<String> splitValues(Criterion criterion) {
        String criterionString = criterion.getValue();
        criterionString = criterionString.replace(" ","");
        return Arrays.asList(criterionString.split(","));
    }

    public static int containsAllValuesCriterionName(List<String> hostCriterionsValues, List<String> guestCriterionValues) {
        int res = 0;
        for (int i = 0; i < Integer.min(hostCriterionsValues.size(), guestCriterionValues.size()); i++) {
            if (hostCriterionsValues.contains(guestCriterionValues.get(i))){
                res++;
            }
        }
        return res;
    }

    public boolean hasLastGuest(Teenager t) {
        if (this.lastGuest != null) {
            return this.lastGuest.equals(t);
        }
        return false;
    }

    /* Permet de mettre ajour un critère dans la map requirements en fonction de son label. */
    public void updateCriterion(CriterionName label, Criterion criterion) {
        this.requirements.put(label, criterion);
    }

    /* Permet de mettre ajour un critère dans la map requirements. */
    public void updateCriterion(Criterion criterion) {
        this.updateCriterion(criterion.getLabel(), criterion);
    }

    /* Getter pour un critère. */
    public Criterion getCriterion(CriterionName label) {
        return this.requirements.get(label);
    }

    /* Getter pour la map requirements. */
    public Map<CriterionName, Criterion> getRequirements() {
        return this.requirements;
    }

    /* Retourne la valeur d'un critère dans la map requirements. */
    public String getValue(CriterionName label) {
        Criterion criterion = this.getCriterion(label);
        if (criterion == null) {
            return "";
        }
        return this.getCriterion(label).getValue();
    }

    /* Vérifie si un critère est bien initialisé dans la map requirements. */
    public boolean criterionIsProperlyDefine(CriterionName label) {
        return this.getCriterion(label) != null;
    }

    /**
     * Si le Criterion n'est pas valable selon la méthodes isValid(), sa valuer dans la Map deviens null.
     * Si il est déjà null, on l'ignore.
     */
    public void purgeCriterion() {
        for (Map.Entry<CriterionName, Criterion> criterion : this.requirements.entrySet()) {
            try {
                if (criterion.getValue() != null) criterion.getValue().isValid();
            }
            catch (CriterionException e) {
                System.out.println(e.getMessage());
                this.updateCriterion(criterion.getKey(), null);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /* Getter pour le prénom. */
    public String getForename() {return this.forename;}

    /* Getter pour le nom. */
    public String getName() {return this.name;}

    /* Getter pour le pays. */
    public Country getCountry() {return this.pays;}

    /* Getter pour l'id. */
    public int getId() {return this.id;}

    /* Getter pour la date de naissance. */
    public LocalDate getBirth() {return this.birth;}

    /* Setter pour le dernier invité. */
    public void setLastguest(Teenager t) {this.lastGuest = t;}

    /* Méthodes toString. */
    public String toString() {
        return String.format("(%d) %s %s", this.id, this.forename, this.name);
    }

    /* Méthodes extractValues pour l'écriture dans un fichier (Récupère les valeurs des critères d'un host). */
    public String extractValuesHost() {
        return toString() + ", " +
        getValue(CriterionName.HOST_FOOD) + ", " + getValue(CriterionName.HOST_HAS_ANIMAL) + ", [" + 
        getValue(CriterionName.HOBBIES) + "], " + getValue(CriterionName.HISTORY) + ", " + 
        getValue(CriterionName.PAIR_GENDER);
    }

    /* Méthodes extractValues pour l'écriture dans un fichier (Récupère les valeurs des critères d'un guest). */
    public String extractValuesGuest() {
        return toString() + ", " +
        getValue(CriterionName.GUEST_FOOD) + ", " + getValue(CriterionName.GUEST_ANIMAL_ALLERGY) + ", [" + 
        getValue(CriterionName.HOBBIES) + "], " + getValue(CriterionName.HISTORY) + ", " + 
        getValue(CriterionName.PAIR_GENDER);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((forename == null) ? 0 : forename.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((birth == null) ? 0 : birth.hashCode());
        result = prime * result + ((pays == null) ? 0 : pays.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Teenager other = (Teenager) obj;
        if (forename == null) {
            if (other.forename != null)
                return false;
        } else if (!forename.equals(other.forename))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (birth == null) {
            if (other.birth != null)
                return false;
        } else if (!birth.equals(other.birth))
            return false;
        if (pays != other.pays)
            return false;
        return true;
    }

    /* Méthodes qui revoie la différence d'age entre deux Teenager. */
    public int diffAge(Teenager t2) {
        return Math.abs(this.birth.getYear() - t2.birth.getYear());
    }

    /* Méthode qui renvoie vérifie si la préférence du genre est respectée. */
    public int genderPref(Teenager t2){
        if (this.getValue(CriterionName.PAIR_GENDER).equals(t2.getValue(CriterionName.GENDER)) &&  (t2.getValue(CriterionName.PAIR_GENDER).equals(this.getValue(CriterionName.GENDER)))
            || this.getValue(CriterionName.PAIR_GENDER).equals("") && t2.getValue(CriterionName.PAIR_GENDER).equals(this.getValue(CriterionName.GENDER ))
            || t2.getValue(CriterionName.PAIR_GENDER).equals("") && this.getValue(CriterionName.PAIR_GENDER).equals(t2.getValue(CriterionName.GENDER ))
            ) {
            return 1;
        }
        if( this.getValue(CriterionName.PAIR_GENDER).equals(t2.getValue(CriterionName.GENDER)) || (t2.getValue(CriterionName.PAIR_GENDER).equals(this.getValue(CriterionName.GENDER)))
            || this.getValue(CriterionName.PAIR_GENDER).equals("") || t2.getValue(CriterionName.PAIR_GENDER).equals("")) {

        return 0;
    }
    return -1;
    }

    public int numIncoherence() {
        int incoherence = 0;
        if (this.getCriterion(CriterionName.HOST_HAS_ANIMAL).getValue().equals("yes") 
            && this.getCriterion(CriterionName.GUEST_ANIMAL_ALLERGY).getValue().equals("yes")) {
            incoherence++;
        }
        return incoherence;
    }

    public void changeRegister() {
        this.isRegistered = !this.isRegistered;
    }

    public boolean getRegistered() {
        return this.isRegistered;
    }
}