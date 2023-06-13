package devoo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class FrenchTeenager extends Teenager {

    public FrenchTeenager(String forename, String name, Country pays, LocalDate birth, Teenager lastGuest) {
        super(forename, name, pays, birth, lastGuest);
    }

    public FrenchTeenager(String forename, String name, Country pays, LocalDate birth) {
        this(forename, name, pays, birth, null);
    }

    public boolean countryCompatibility(Teenager t) {
        ArrayList<String> hostHobbies = new ArrayList<String>(splitValues(this.getCriterion(CriterionName.HOBBIES)));
        ArrayList<String> guestHobbies = new ArrayList<String>(splitValues(t.getCriterion(CriterionName.HOBBIES)));
        
        Collections.sort(hostHobbies); Collections.sort(guestHobbies);

        return (containsAllValuesCriterionName(hostHobbies, guestHobbies)) >= 1;
    }

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
