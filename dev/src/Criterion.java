public abstract class Criterion {
    private CriterionName label;
    private String value;

    public Criterion(CriterionName label, String value) {
        this.label = label;
        this.value = value;
    }

    private void setValue(CriterionName label, String value) {
        
    }

    private boolean checkBooleanCompability() {
        if (this.value == null) {return false;}
        if (this.value.equals("yes") ^ this.value.equals("no")) {return false;}
        return true;
    }

    public abstract boolean isValid();
}
