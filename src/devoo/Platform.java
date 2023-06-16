package devoo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    public static final String OUTPUT = "Output.csv";

    /* Constante pour le chemin d'accès au fichier. */
    public static File ressourcesPath = new File(DIRECTORY + SEPARATOR + "rsc" + SEPARATOR);
    public static File historyPath = new File(DIRECTORY + SEPARATOR + "hist" + SEPARATOR);

    /* Ensemble des étudiants. */
    private Set<Teenager> students;

    /* Liste des étudiants hôtes et invités. */
    private List<Teenager> host;
    private List<Teenager> guest;

    /* Map des couples d'étudiants après l'affection. */
    private Map<Teenager, Teenager> currentAffectation;

    /* Map des couples d'étudiants sauvegardé. */
    private Map<Teenager, Teenager> previousAffectation;
    
    /* Map des couples d'étudiants fixés et à éviter. */
    private Map<Teenager, Teenager> pairFixed;
    private Map<Teenager, Teenager> pairAvoided;

    /* Constructeur de Platform. */
    public Platform() {
        this.students = new HashSet<Teenager>();
        this.currentAffectation = new HashMap<Teenager, Teenager>();
        this.previousAffectation = new HashMap<Teenager, Teenager>();
        this.pairFixed = new HashMap<Teenager, Teenager>();
        this.pairAvoided = new HashMap<Teenager, Teenager>();
    }

    /* Ajoute un étudiant à l'ensemble. */
    public void addStudents(Teenager teen) {
        this.students.add(teen);
    }

    // Méthode pour affecter les étudiants dans arrayList Host and Guest.
    public void affectation(Country hostCountry, Country guestCountry) {
        this.makeHostAndGuest(hostCountry, guestCountry);
        this.currentAffectation = AffectationUtil.affectation(this.host, this.guest, this.pairFixed, this.pairAvoided);
    }
    
    /* Importe les étudiants depuis un fichier CSV. */
    public void importCSV(File file) {
        String[] header;
        Teenager students;
        try (Scanner lineScanner = new Scanner(file)) {
            header = lineScanner.nextLine().split(";");
            System.out.println(header);
            while (lineScanner.hasNextLine()) {
                Scanner fieldScanner = new Scanner(lineScanner.nextLine());
                fieldScanner.useDelimiter(";");
                String forename = "";
                String name = "";
                Country country = null;
                LocalDate birthdate = null;
                List<Criterion> criterion = new ArrayList<Criterion>();
                int i = 0;
                while (fieldScanner.hasNext()) {
                    if (header[i].equals("FORENAME")) {forename = fieldScanner.next();}
                    else if (header[i].equals("NAME")) {name = fieldScanner.next();}
                    else if (header[i].equals("COUNTRY")) {country = Country.valueOf(fieldScanner.next());}
                    else if (header[i].equals("BIRTH_DATE")) {birthdate = LocalDate.parse(fieldScanner.next());}
                    else {
                        String value = fieldScanner.next();
                        if (i == header.length - 1 && value.equals("null")) {value = "";}
                        criterion.add(new Criterion(header[i], value));}
                    i++;
                }
                if (country == Country.FRANCE) {
                    students = new FrenchTeenager(forename, name, country, birthdate);
                } else {
                    students = new Teenager(forename, name, country, birthdate);
                }
                for (Criterion c : criterion) {
                    students.updateCriterion(c);
                }
                students.purgeCriterion();
                this.addStudents(students);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    // Surcharge de la méthode importCSV pour importer le fichier par défaut.
    public void importCSV(String filename) {
        this.importCSV(new File(ressourcesPath + SEPARATOR + filename));
    } 

    /* Exporte les couples d'étudiants dans un fichier CSV. */
    public void exportCSV() {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(ressourcesPath + SEPARATOR + OUTPUT))) {
            br.write("hostId, hostForename, hostName, hostGender, hostDiet, hostAnimal, hostHobbies, hostHistory, hostPairGender, guestId, guestForename, guestName, guestGender, guestDiet, guestAllergy, guestHistory, guestPairGender");
            br.newLine();
            for (Map.Entry<Teenager, Teenager> couple : this.currentAffectation.entrySet()) {
                br.write(couple.getKey().extractValuesHost() + ", " + couple.getValue().extractValuesGuest());
                br.newLine();
            }
        } catch (IOException e) {
            System.out.println("IOEXCEPTION ;\n"+e.getStackTrace());
        } catch (Exception e) {
            System.out.println("Exception\n" + e.getMessage());
        }
    }
    // Surcharge de la méthode exportCSV pour exporter le fichier par défaut.
    public void exportBin() {
        Country hostCountry = this.currentAffectation.keySet().iterator().next().getCountry();
        Country guestCountry = this.currentAffectation.values().iterator().next().getCountry();
        String filename = String.format("%s-%s (%d).bin", hostCountry, guestCountry, LocalDate.now().getYear());
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(historyPath + SEPARATOR + filename))) {
            oos.writeObject(this.currentAffectation);
        } catch (IOException e) {
            System.out.println("IOEXCEPTION ;\n"+e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception\n" + e.getMessage());
        }
    }
    // Méthode pour lire un fichier binaire.
    public void readBin(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(historyPath + SEPARATOR + filename))) {
            this.previousAffectation = (Map<Teenager,Teenager>) ois.readObject();
        } catch (IOException e) {
            System.out.println("IOEXCEPTION ;\n"+e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception\n" + e.getMessage());
        }
    }

    public Map<Teenager, Teenager> getAffectation() {
        return this.currentAffectation;
    }

    public Map<Teenager, Teenager> getPreviousAffectation() {
        return this.previousAffectation;
    }

    public Set<Teenager> getStudents() {
        return this.students;
    }

    public Map<Teenager, Teenager> getPairFixed() {
        return this.pairFixed;
    }

    public Map<Teenager, Teenager> getPairAvoided() {
        return this.pairAvoided;
    }

    public void addPairFixed(Teenager host, Teenager guest) {
        this.pairFixed.put(host, guest);
    }

    public void addPairAvoided(Teenager host, Teenager guest) {
        this.pairAvoided.put(host, guest);
    }

    public void makeHostAndGuest(Country hostCountry, Country guestCountry) {
        this.host = new ArrayList<Teenager>();
        this.guest = new ArrayList<Teenager>();
        for (Teenager t : this.students) {
            if (t.getCountry().equals(hostCountry)) {
                host.add(t);
            }
            else if (t.getCountry().equals(guestCountry)) {
                guest.add(t);
            }
        }
        if (this.host.size() > this.guest.size()) {
            this.unregisterTeenager(this.host.size() - this.guest.size(), hostCountry);
        }
        else if (this.guest.size() > this.host.size()) {
            this.unregisterTeenager(this.guest.size() - this.host.size(), guestCountry);
        }
        for (Teenager t : this.students) {
            if (t.getCountry().equals(hostCountry) && !t.getRegistered()) {
                this.host.remove(t);
            }
            else if (t.getCountry().equals(guestCountry) && !t.getRegistered()) {
                this.guest.remove(t);
            }
        }
    } 

    public List<Teenager> getHost() {
        return this.host;
    } 

    public List<Teenager> getGuest() {
        return this.guest;
    }

    public void clearHost() {
        this.host.clear();
    }

    public void clearGuest() {
        this.guest.clear();
    }

    public void loadHistory(File file) {
        this.readBin(file.getName());
        for (Map.Entry<Teenager, Teenager> couple : this.previousAffectation.entrySet()) {
            for (Teenager t : this.students) {
                if (t.equals(couple.getKey())) {
                    t.setLastguest(this.previousAffectation.get(t));
                }
            }
        }
    }

    public void unregisterTeenager(int num) {
        List<Teenager> list = new ArrayList<Teenager>(this.students);
        list.sort(new IncoherenceSort());
        List<Teenager> list2 = list.subList(list.size() - num, list.size());
        for (Teenager t : list2) {
            t.unregister();
        }
    }

    public void unregisterTeenager(int num, Country country) {
        List<Teenager> list = new ArrayList<Teenager>();
        for (Teenager t : this.students) {
            if (t.getCountry().equals(country)) {
                list.add(t);
            }
        }
        list.sort(new IncoherenceSort());
        List<Teenager> list2 = list.subList(list.size() - num, list.size());
        for (Teenager t : list2) {
            t.unregister();
        }
    }

}
