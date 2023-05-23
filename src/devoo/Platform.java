package devoo;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @autor : Raphael Kiecken, Armand Sady, Antoine Gaienier
 */
public class Platform {

    private Set<Teenager> students = new HashSet<Teenager>();

    public void addStudents(Teenager teen) {
        this.students.add(teen);
    }
    
}
