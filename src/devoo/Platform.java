package devoo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import graphes.AffectationUtil;

/**
 * Classe Platform.
 * @autor : Raphael Kiecken, Armand Sady, Antoine Gaienier
 */
public class Platform {

    /* Constantes pour le chemin d'accès au fichier */
    public static final String DIRECTORY = System.getProperty("user.dir");
    public static final String SEPARATOR = System.getProperty("file.separator");

    /* Constantes pour le nom des fichiers. */
    public static final String INPUT = "Input.csv";
    public static final String OUTPUT = "Output.txt";

    /* Constante pour le chemin d'accès au fichier. */
    public static File path = new File(DIRECTORY + SEPARATOR + "rsc" + SEPARATOR);

    /* Ensemble des étudiants. */
    private Set<Teenager> students;

    /* Map des couples d'étudiants après l'affection. */
    private Map<Teenager, Teenager> affectation;;

    /* Constructeur de Platform. */
    public Platform() {
        this.students = new HashSet<Teenager>();
        this.affectation = new HashMap<Teenager, Teenager>();
    }

    /* Ajoute un étudiant à l'ensemble. */
    public void addStudents(Teenager teen) {
        this.students.add(teen);
    }
    
    /* Importe les étudiants depuis un fichier CSV. */
    public void importCSV() {
        File file = new File(path, INPUT);
        String[] header;
        Teenager students;
        try (Scanner lineScanner = new Scanner(file)) {
            header = lineScanner.nextLine().split(";");
            while (lineScanner.hasNextLine()) {
                Scanner fieldScanner = new Scanner(lineScanner.nextLine());
                fieldScanner.useDelimiter(";");
                String forename = "";
                String name = "";
                Country country = null;
                LocalDate birthdate = null;
                List<Criterion> criteria = new ArrayList<Criterion>();
                int i = 0;
                while (fieldScanner.hasNext()) {
                    if (header[i].equals("FORENAME")) {forename = fieldScanner.next();}
                    else if (header[i].equals("NAME")) {name = fieldScanner.next();}
                    else if (header[i].equals("COUNTRY")) {country = Country.valueOf(fieldScanner.next());}
                    else if (header[i].equals("BIRTH_DATE")) {birthdate = LocalDate.parse(fieldScanner.next());}
                    else {criteria.add(new Criterion(header[i], fieldScanner.next()));}
                    i++;
                }
                students = new Teenager(forename, name, country, birthdate);
                for (Criterion c : criteria) {
                    students.updateCriterion(c);
                this.addStudents(students);
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /* Exporte les couples d'étudiants dans un fichier CSV. */
    public void exportCSV() {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(path + SEPARATOR + OUTPUT))) {
            br.write("hostId, hostForename, hostName, hostGender, hostDiet, hostAnimal, hostHobbies, hostHistory, hostPairGender, guestId, guestForename, guestName, guestGender, guestDiet, guestAllergy, guestHistory, guestPairGender");
            br.newLine();
            for (Map.Entry<Teenager, Teenager> couple : this.affectation.entrySet()) {
                br.write(couple.getKey().extractValuesHost() + ", " + couple.getValue().extractValuesGuest());
                br.newLine();
            }
        } catch (IOException e) {
            System.out.println("IOEXCEPTION ;\n"+e.getStackTrace());
        } catch (Exception e) {
            System.out.println("Exception\n" + e.getMessage());
        }
    }

    public void affectation(Country hostCountry, Country guesCountry) {
        List<Teenager> host = new ArrayList<Teenager>();
        List<Teenager> guest = new ArrayList<Teenager>();
        for (Teenager t : this.students) {
            if (t.getCountry().equals(hostCountry)) {
                host.add(t);
            }
            else if (t.getCountry().equals(guesCountry)) {
                guest.add(t);
            }
        }
        this.affectation = AffectationUtil.affectation(host, guest);
    }

    public static void main(String[] args) {
        Platform platform = new Platform();
        platform.importCSV();
        System.out.println(platform.students.size());
        /*for (Teenager t : platform.students) {
            System.out.println(t.toString());
        }*/
        platform.affectation(Country.FRANCE, Country.ITALY);
        for (Map.Entry<Teenager, Teenager> couple : platform.affectation.entrySet()) {
            System.out.println(couple.getKey().getName() + ", " + couple.getValue().getName());
        }
        platform.exportCSV();
    }
}
