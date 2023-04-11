import java.util.List;

public class Teenager {
    private static final int count = 1;
    
    private int id;
    private String name;
    private List<Criterion> requirements;

    public Teenager(int id, String name, List<Criterion> requirements) {
        this.id = id;
        this.name = name;
        this.requirements = requirements;
    }

    public boolean compatibleWithGuest(Teenager t) {
        
    }
}
