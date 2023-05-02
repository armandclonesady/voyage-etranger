package devoo;

import java.util.ArrayList;
import java.util.Arrays;

public class Criterion {
    /**
     * Attributes
     */
    private CriterionName label;
    private String value;

    /**
     * Values for Booleans
     */
    final static public String POS = "yes"; 
    final static public String NEG = "no";
    final static private ArrayList<String> B_VALUES = new ArrayList<String>(Arrays.asList(POS, NEG));
    /**
     * Values for Gender
     */
    final static public String F = "female"; 
    final static public String M = "male";
    final static public String OTH = "other";
    final static private ArrayList<String> GENDER_VALUES = new ArrayList<String>(Arrays.asList(F, M, OTH));

    /**
     * Values for History
     */
    final static public String PREF_SAME = "same";
    final static public String PREF_OTH = "other";
    final static private ArrayList<String> HISTORY_VALUES = new ArrayList<String>(Arrays.asList(PREF_SAME, PREF_OTH));

    /**
     * Constructeur de Criterion, entièrement
     * @param value 
     * @param label
     */
    public Criterion(CriterionName label, String value) {
        this.label = label;
        this.value = value;
    }

    public Criterion(String label, String value) {
        this(CriterionName.valueOf(label), value);
    }

    public boolean isValid() {
        switch (this.label.getType()) {
            case 'B':
                return B_VALUES.contains(this.value);
            case 'T':
                if (this.label.equals(CriterionName.GENDER) || this.label.equals(CriterionName.PAIR_GENDER)) {
                    return GENDER_VALUES.contains(this.value);
                } 
                else if (this.label.equals(CriterionName.HISTORY)) {
                    return HISTORY_VALUES.contains(this.value);
                }
                return false;
            default:
                return true;   
        }
    }

    

    public CriterionName getLabel() {
        return this.label;
    }

    public String getValue() {
        return this.value;
    }
}
