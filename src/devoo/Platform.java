package devoo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
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
    public static final String OUTPUT = "Output.txt";

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
                fieldScanner.useDelimiter(";");
                students = new Teenager(fieldScanner.next(), fieldScanner.next(), fieldScanner.next(), LocalDate.parse(fieldScanner.next()));
                i = 4;
                while (fieldScanner.hasNext()) {
                    students.updateCriterion(new Criterion(header[i], fieldScanner.next()));
                    i++;
                }
                this.addStudents(students);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void exportToCSV(HashMap<Teenager, Teenager> teenagerMap) {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(path+OUTPUT))) {
            br.write("host_name, host_forename, host_id, host_gender, host_diet, host_animal, host_hobbies, host_history, host_same_gender, guest_name, guest_forename, guest_id, guest_gender, guest_diet, guest_allergy, guest_history, guest_same_gender");
            br.newLine();
            for (Teenager teenager : teenagerMap.keySet()) {
                br.write(""+ teenager.extractValuesHost() + ", " + (teenagerMap.get(teenager)).extractValuesGuest());
                br.newLine();
            }
        } catch (IOException e) {
            System.out.println("IOEXCEPTION ;\n"+e.getStackTrace());
        } catch (Exception e) {
            System.out.println("Exception\n" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Platform platform = new Platform();
        platform.importCSV("adosAleatoires.csv");
        System.out.println(platform.students.size());
        for (Teenager t : platform.students) {
            System.out.println();
        }
    
    }

}
