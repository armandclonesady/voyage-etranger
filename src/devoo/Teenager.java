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
    /**
     * Compteur d'identifiant.
     */
    private static int count = 1;
    
    /**
     * Identifiant du Teenager.
     */
    private int id;
    /**
     * Prénom du Teenager.
     */
    private String forename;
    /**
     * Nom du Teenager.
     */
    private String name;
    /**
     * Date de naissance du Teenager.
     */
    private LocalDate birth;
    /**
     * Pays du Teenager.
     */
    private Country pays;
    /**
     * Dernier invité du Teenager.
     */
    private Teenager lastGuest;

    /**
     * Booléen indiquant si le Teenager est inscrit ou non.
     */
    private boolean isRegistered = true; 

    /**
     * Map contenant les Criterion du Teenager.
     */
    private Map<CriterionName, Criterion> requirements;

    /**
     * Constructeur de Teenager avec un dernier invité.
     * @param forename Prénom du Teenager.
     * @param name Nom du Teenager.
     * @param pays Pays du Teenager.
     * @param birth Date de naissance du Teenager.
     * @param lastGuest Dernier invité du Teenager.
     */
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

    /**
     * Constructeur de Teenager sans dernier invité.
     * @param forename Prénom du Teenager.
     * @param name Nom du Teenager.
     * @param pays Pays du Teenager.
     * @param birth Date de naissance du Teenager.
     */
    public Teenager(String forename, String name, Country pays, LocalDate birth) {
        this(forename, name, pays, birth, null);
    }

    /**
     * Initialise les Criterion du Teenager en les mettant à null.
     */
    public void initRequirements() {
        this.requirements = new HashMap<CriterionName, Criterion>();
        for(CriterionName c : CriterionName.values()) {
            this.requirements.put(c, null);
        }
    }

    /**
     * Vérifie si le Teenager est compatible avec un autre Teenager.
     * @param t Teenager avec lequel on veut vérifier la compatibilité.
     * @return true si le Teenager est compatible avec le Teenager t, false sinon.
     */
    public boolean compatibleWithGuest(Teenager t) {
        if((this.criterionIsProperlyDefine(CriterionName.HISTORY) && t.criterionIsProperlyDefine(CriterionName.HISTORY)) && this.hasLastGuest(t)) {
            if (historyCompatibility(t) != -1) {
                return historyCompatibility(t) == 1 ? true : false;
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

    /**
     * Vérifie la compatibilité des entre deux Teenager au niveau du Criterion HISTORY
     * @param t Teenager avec lequel on veut vérifier la compatibilité.
     * @return 1 si les deux Teenager doivent être ensemble, 0 si les deux Teenager ne doivent pas être ensemble et -1 si ce critères n'as pas d'influence.
     */
    public int historyCompatibility(Teenager t) {
        String historyHost = this.getValue(CriterionName.HISTORY);
        String historyGuest = t.getValue(CriterionName.HISTORY);
        if(historyHost.equals(Criterion.PREF_SAME) && historyGuest.equals(Criterion.PREF_SAME)) {
            return 1;
        } else if (historyHost.equals(Criterion.OTH) || historyGuest.equals(Criterion.OTH)) {
            return 0;
        }
        return -1;
    }

    /**
     * Vérifie la compatibilité des entre deux Teenager au niveau des Criterion HOST_HAS_ANIMAL et GUET_ANIMAL_ALLERGY
     * @param t Teenager avec lequel on veut vérifier la compatibilité.
     * @return true si les deux Teenager sont compatibles, false sinon.
     */
    public boolean animalCompatibility(Teenager t) {
        String hostAnimal = this.getValue(CriterionName.HOST_HAS_ANIMAL);
        String guestAnimal = t.getValue(CriterionName.GUEST_ANIMAL_ALLERGY);
        return !(hostAnimal.equals("yes") && hostAnimal == guestAnimal);
    }

    /**
     * Vérifie la compatibilité des entre deux Teenager au niveau des Criterion HOST_FOOD et GUET_FOOD
     * @param t Teenager avec lequel on veut vérifier la compatibilité.
     * @return true si les deux Teenager sont compatibles, false sinon.
     */
    public boolean foodCompatibility(Teenager t) {
        if(t.getValue(CriterionName.GUEST_FOOD).equals("")) return true;
        ArrayList<String> hostRegime = new ArrayList<String>(splitValues(this.getCriterion(CriterionName.HOST_FOOD)));
        ArrayList<String> guestRegime = new ArrayList<String>(splitValues(t.getCriterion(CriterionName.GUEST_FOOD)));

        Collections.sort(hostRegime); Collections.sort(guestRegime);

        return (containsAllValuesCriterionName(hostRegime, guestRegime)) == guestRegime.size();
    }

    /**
     * Transforme la valeur des 
     * @param criterion
     * @return
     */
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

    /**
     * Vérifie si un Teenager possède un lastGuest et si ce lastGuest est le Teenager t.
     * @param t Teenager avec lequel on veut vérifier la compatibilité.
     * @return true si le Teenager possède un lastGuest et que ce lastGuest est le Teenager t, false sinon.
     */
    public boolean hasLastGuest(Teenager t) {
        if (this.lastGuest != null) {
            return this.lastGuest.equals(t);
        }
        return false;
    }

    /**
     * Met à jour le Criterion dans la map requirements.
     * @param label Label du critère à mettre à jour.
     * @param criterion Nouveau Criterion
     */
    public void updateCriterion(CriterionName label, Criterion criterion) {
        this.requirements.put(label, criterion);
    }

    /**
     * Met à jour le Criterion dans la map requirements.
     * @param criterion Nouveau Criterion
     */
    public void updateCriterion(Criterion criterion) {
        this.updateCriterion(criterion.getLabel(), criterion);
    }

    /**
     * Accesseur pour un Criterion dans la map requirements.
     * @param label Label du critère à retourner.
     * @return Criterion correspondant au label.
     */
    public Criterion getCriterion(CriterionName label) {
        return this.requirements.get(label);
    }

    /**
     * Accesser pour la map requirements.
     * @return Map requirements.
     */
    public Map<CriterionName, Criterion> getRequirements() {
        return this.requirements;
    }

    /**
     * Accesseur pour la valeur d'un Criterion dans la map requirements.
     * @param label Label du critère dont on veut la valeur.
     * @return Valeur du Criterion correspondant au label.
     */
    public String getValue(CriterionName label) {
        try {
            return this.getCriterion(label).getValue();
        }
        catch (NullPointerException e) {
            return "";
        }
    }

    /**
     * Vérifie si un Criterion est bien défini dans la map requirements.
     * @param label Label du critère à vérifier.
     * @return true si le Criterion est bien défini, false sinon.
     */
    public boolean criterionIsProperlyDefine(CriterionName label) {
        return this.getCriterion(label) != null;
    }

    /**
     * Met à null tous les Criterion de la map requirements qui ne sont pas valide.
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

    
    public int diffAge(Teenager t2) {
        return Math.abs(this.birth.getYear() - t2.birth.getYear());
    }

    /* Méthode qui renvoie vérifie si la préférence du genre est respectée. */
    public int genderPref(Teenager t){
        int res = 0;
        if (this.getValue(CriterionName.PAIR_GENDER).isEmpty() || this.getValue(CriterionName.PAIR_GENDER).equals(t.getValue(CriterionName.GENDER))) {
            res += 1;
        }
        if (t.getValue(CriterionName.PAIR_GENDER).isEmpty() ||t.getValue(CriterionName.PAIR_GENDER).equals(this.getValue(CriterionName.GENDER))) {
            res += 1;
        }
        return res;
    }

    public int numIncoherence() {
        int incoherence = 0;
        if (this.getCriterion(CriterionName.HOST_HAS_ANIMAL).getValue().equals("yes") 
            && this.getCriterion(CriterionName.GUEST_ANIMAL_ALLERGY).getValue().equals("yes")) {
            incoherence++;
        }
        return incoherence;
    }

    public void unregister() {
        if (this.isRegistered) {this.isRegistered = false;}
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

    /* Getter pour l'id. */
    public int getId() {return this.id;}

    /* Getter pour le prénom. */
    public String getForename() {return this.forename;}

    /* Getter pour le nom. */
    public String getName() {return this.name;}

    /* Getter pour le pays. */
    public Country getCountry() {return this.pays;}

    /* Getter pour la date de naissance. */
    public LocalDate getBirth() {return this.birth;}

    public boolean getRegistered() {return this.isRegistered;}

    /* Setter pour le dernier invité. */
    public void setLastguest(Teenager t) {this.lastGuest = t;}

    /* Méthodes toString. */
    public String toString() {
        return String.format("(%d) %s %s", this.id, this.forename, this.name);
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
}