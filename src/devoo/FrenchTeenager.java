package devoo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe FrenchTeenager.
 * @author : Raphael KIECKEN, Armand SADY, Antoine GAIENIER
 */
public class FrenchTeenager extends Teenager {

    /**
     * Constructeur FrenchTeenager.
     * @param forename : prénom
     * @param name : nom
     * @param pays : pays
     * @param birth : date de naissance
     * @param lastGuest : dernier invité
     */
    public FrenchTeenager(String forename, String name, Country pays, LocalDate birth, Teenager lastGuest) {
        super(forename, name, pays, birth, lastGuest);
    }

    /**
     * Constructeur FrenchTeenager.
     * @param forename : prénom
     * @param name : nom
     * @param pays : pays
     * @param birth : date de naissance
     */
    public FrenchTeenager(String forename, String name, Country pays, LocalDate birth) {
        this(forename, name, pays, birth, null);
    }

    /**
     * Vérifie la compatibilité des Criterion HOBBIES entre l'adolescent et son invité.
     * @param t : invité
     * @return : boolean
     */
    public boolean countryCompatibility(Teenager t) {
        ArrayList<String> hostHobbies = new ArrayList<String>(splitValues(this.getCriterion(CriterionName.HOBBIES)));
        ArrayList<String> guestHobbies = new ArrayList<String>(splitValues(t.getCriterion(CriterionName.HOBBIES)));
        
        Collections.sort(hostHobbies); Collections.sort(guestHobbies);

        return (containsAllValuesCriterionName(hostHobbies, guestHobbies)) >= 1;
    }

    /**
     * Vérifie la compatibilité de l'adolescent avec son invité en prenant en compte les Criterion HOBBIES.
     * @param t : invité
     * @return : boolean
     */
    @Override
    public boolean compatibleWithGuest(Teenager t) {
        if (super.compatibleWithGuest(t)) {
            if (this.criterionIsProperlyDefine(CriterionName.HOBBIES) && t.criterionIsProperlyDefine(CriterionName.HOBBIES)) {
                if (this.countryCompatibility(t)) {
                    return true;
                }
            }
        }
        return false;
    }
}
