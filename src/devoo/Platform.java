package devoo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * 
 * @autor : Raphael Kiecken, Armand Sady, Antoine Gaienier
 */
public class Platform {

    public static final String DIRECTORY = System.getProperty("user.dir");
    public static final String SEPARATOR = System.getProperty("file.separator");

    public static File path = new File(DIRECTORY + SEPARATOR + "rsc" + SEPARATOR);

    private Set<Teenager> students = new HashSet<Teenager>();

    public void addStudents(Teenager teen) {
        this.students.add(teen);
    }
    
    public void importCSV(String filename) {
        File file = new File(path, filename);
        String[] header;
        Teenager students;
        int i;
        try (Scanner lineScanner = new Scanner(file)) {
            header = lineScanner.nextLine().split(";");
            while (lineScanner.hasNextLine()) {
                Scanner fieldScanner = new Scanner(lineScanner.nextLine());
                students = new Teenager(fieldScanner.next(), LocalDate.parse(fieldScanner.next()), fieldScanner.next());
                i = 2;
                while (fieldScanner.hasNextLine()) {
                    students.updateCriterion(new Criterion(header[i], fieldScanner.nextLine()));
                    i++;
                }
                this.addStudents(students);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void exportToCSV(HashMap<Teenager, Teenager>     )

    public static void main(String[] args) {
        Platform platform = new Platform();
        platform.importCSV("adosAleatoires.csv");
    
    }

}
