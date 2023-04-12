package devoo;

import java.util.ArrayList;
import java.util.Arrays;

public class Criterion {
    /**
     * Attributes
     */
    private String value;
    private CriterionName label;

    /**
     * Values for Booleans
     */
    final static private String POS = "yes"; 
    final static private String NEG = "no";
    final static private ArrayList<String> B_VALUES = new ArrayList<String>(Arrays.asList(POS, NEG));
    /**
     * Values for Gender
     */
    final static private String F = "female"; 
    final static private String M = "male";
    final static private String OTH = "other";
    final static private ArrayList<String> GENDER_VALUES = new ArrayList<String>(Arrays.asList(F, M, OTH));

    /**
     * Values for History
     */
    final static private String PREF_SAME = "same";
    final static private String PREF_OTH = "other";
    final static private ArrayList<String> HISTORY_VALUES = new ArrayList<String>(Arrays.asList(PREF_SAME, PREF_OTH));

    /**
     * Constructeur de Criterion, entièrement
     * @param value
     * @param label
     */
    public Criterion(String value, CriterionName label) {
        this.value = value;
        this.label = label;
    }

    public Criterion(String value, String label) {
        this(value, CriterionName.valueOf(label));
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
                return false;   
        }
    }
}
