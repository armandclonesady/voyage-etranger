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
import java.time.format.DateTimeParseException;
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
 * @author : Raphael Kiecken, Armand Sady, Antoine Gaienier
 */
public class Platform {

    /**
     * Constantes pour le chemin d'accès jusqu'au dossier courant.
     */
    public static final String DIRECTORY = System.getProperty("user.dir");
    /**
     * Constantes pour le séparateur de fichier.
     */
    public static final String SEPARATOR = System.getProperty("file.separator");

    /**
     * Constante pour le nom du fichier de sortie.
     */
    public static final String OUTPUT = "Output.csv";

    /**
     * Constante pour le chemin d'accès jusqu'au dossier des ressources.
     */
    public static File ressourcesPath = new File(DIRECTORY + SEPARATOR + "rsc" + SEPARATOR);
    /**
     * Constante pour le chemin d'accès jusqu'au dossier des historiques.
     */
    public static File historyPath = new File(DIRECTORY + SEPARATOR + "hist" + SEPARATOR);

    /**
     * Ensemble des étudiants.
     */
    private Set<Teenager> students;

    /**
     * Liste des étudiants hôtes et invités.
     */
    private List<Teenager> host;
    private List<Teenager> guest;

    /**
     * Map des couples d'étudiants actuels.
     */
    private Map<Teenager, Teenager> currentAffectation;

    /**
     * Map des couples d'étudiants précédents.
     */
    private Map<Teenager, Teenager> previousAffectation;
    
    /**
     * Map des couples d'étudiants fixé.
     */
    private Map<Teenager, Teenager> pairFixed;
    /**
     * Map des couples d'étudiants évités.
     */
    private Map<Teenager, Teenager> pairAvoided;

    /**
     * Constructeur de la classe Platform.
     */
    public Platform() {
        this.students = new HashSet<Teenager>();
        this.currentAffectation = new HashMap<Teenager, Teenager>();
        this.previousAffectation = new HashMap<Teenager, Teenager>();
        this.pairFixed = new HashMap<Teenager, Teenager>();
        this.pairAvoided = new HashMap<Teenager, Teenager>();
    }

    /**
     * Méthode pour ajouter un étudiant dans l'ensemble des étudiants.
     */
    public void addStudents(Teenager teen) {
        this.students.add(teen);
    }

    /**
     * Méthode qui créé les couples d'étudiants les plus compatibles.
     */
    public void affectation(Country hostCountry, Country guestCountry) {
        this.makeHostAndGuest(hostCountry, guestCountry);
        this.currentAffectation = AffectationUtil.affectation(this.host, this.guest, this.pairFixed, this.pairAvoided);
    }
    
    /**
     * Importe les étudiants depuis un fichier CSV.
     * @param file : fichier CSV
     */
    public void importCSV(File file) {
        String[] header;
        Teenager students;
        try (Scanner lineScanner = new Scanner(file)) {
            header = lineScanner.nextLine().split(";");
            while (lineScanner.hasNextLine()) {
                Scanner fieldScanner = new Scanner(lineScanner.nextLine());
                fieldScanner.useDelimiter(";");
                String forename = "";
                String name = "";
                String country = null;
                String birthdate = null;
                List<Criterion> criterion = new ArrayList<Criterion>();
                int i = 0;
                while (fieldScanner.hasNext()) {
                    if (header[i].equals("FORENAME")) {forename = fieldScanner.next();}
                    else if (header[i].equals("NAME")) {name = fieldScanner.next();}
                    else if (header[i].equals("COUNTRY")) {country = fieldScanner.next();}
                    else if (header[i].equals("BIRTH_DATE")) {birthdate = fieldScanner.next();}
                    else {
                        String value = fieldScanner.next();
                        if (i == header.length - 1 && value.equals(null)) {value = "";}
                        criterion.add(new Criterion(header[i], value));
                    }
                    i++;
                }
                if (forename.isEmpty() || forename.isBlank()) {
                    System.out.println("Prénom vide, Etudiant ignoré.");
                    break;
                }
                if (name.isEmpty() || name.isBlank()) {
                    System.out.println("Nom vide, Etudiant ignoré.");
                    break;
                }
                try {
                    if (country.equals("FRANCE")) {
                        students = new FrenchTeenager(forename, name, Country.valueOf(country), LocalDate.parse(birthdate));
                    } else {
                        students = new Teenager(forename, name, Country.valueOf(country), LocalDate.parse(birthdate));
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Date de naissance invalide, Etudiant ignoré.");
                    break;
                }
                catch (IllegalArgumentException e) {
                    System.out.println("Pays invalide, Etudiant ignoré.");
                    break;
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

    /**
     * Exporte les couples d'étudiants dans un fichier CSV.
     */
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

    /**
     * Exporte les couples d'étudiants dans un fichier binaire.
     */
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

    /**
     * Importe les couples d'étudiants depuis un fichier binaire.
     * @param filename : nom du fichier binaire
     */
    public void readBin(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(historyPath + SEPARATOR + filename))) {
            this.previousAffectation = (Map<Teenager,Teenager>) ois.readObject();
        } catch (IOException e) {
            System.out.println("IOEXCEPTION ;\n"+e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception\n" + e.getMessage());
        }
    }

    /**
     * Ajoute une nouvelle paire d'étudiants fixes.
     * @param host : étudiant hôte
     * @param guest : étudiant invité
     */
    public void addPairFixed(Teenager host, Teenager guest) {
        this.pairFixed.put(host, guest);
    }

    /**
     * Ajoute une nouvelle paire d'étudiants évités.
     * @param host : étudiant hôte
     * @param guest : étudiant invité
     */
    public void addPairAvoided(Teenager host, Teenager guest) {
        this.pairAvoided.put(host, guest);
    }

    /**
     * Génére la liste des hôtes et des invités.
     * @param hostCountry : pays hôte
     * @param guestCountry : pays invité
     */
    public void makeHostAndGuest(Country hostCountry, Country guestCountry) {
        this.clearHost(); this.clearGuest();
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

    /**
     * Accesseur de l'ensemble des étudiants.
     * @return Set de Teenager : ensemble des étudiants
     */
    public Set<Teenager> getStudents() {
        return this.students;
    }

    /**
     * Accesseur des couples d'étudiants courants.
     * @return Map de Teenager : couples d'étudiants courants
     */
    public Map<Teenager, Teenager> getCurrentAffectation() {
        return this.currentAffectation;
    }

    /**
     * Accesseur des couples d'étudiants précédents.
     * @return Map de Teenager : couples d'étudiants précédents
     */
    public Map<Teenager, Teenager> getPreviousAffectation() {
        return this.previousAffectation;
    }

    /**
     * Accesseur des couples d'étudiants fixes.
     * @return Map de Teenager : couples d'étudiants fixes
     */
    public Map<Teenager, Teenager> getPairFixed() {
        return this.pairFixed;
    }

    /**
     * Accesseur des couples d'étudiants évités.
     * @return Map de Teenager : couples d'étudiants évités
     */
    public Map<Teenager, Teenager> getPairAvoided() {
        return this.pairAvoided;
    }

    /**
     * Accesseur des étudiants hôtes.
     * @return List de Teenager : étudiants hôtes
     */
    public List<Teenager> getHost() {
        return this.host;
    } 

    /**
     * Accesseur des étudiants invités.
     * @return List de Teenager : étudiants invités
     */
    public List<Teenager> getGuest() {
        return this.guest;
    }

    /**
     * Nettoie la liste des étudiants hôtes.
     */
    public void clearHost() {
        if (this.host != null) {
            this.host.clear();
        }
        this.host = new ArrayList<Teenager>();
    }

    /**
     * Nettoie la liste des étudiants invités.
     */
    public void clearGuest() {
        if (this.guest != null) {
            this.guest.clear();
        }
        this.guest = new ArrayList<Teenager>();
    }

    /**
     * Charge l'historique des couples d'étudiants depuis un fichier binaire et met à jour la variable lastguest de chaque étudiant hôte.
     * @param file : fichier binaire
     */
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

    /**
     * Désinscrit un nombre donné d'étudiants.
     * @param num : nombre d'étudiants à désinscrire
     */
    public void unregisterTeenager(int num) {
        List<Teenager> list = new ArrayList<Teenager>(this.students);
        list.sort(new IncoherenceSort());
        List<Teenager> list2 = list.subList(list.size() - num, list.size());
        for (Teenager t : list2) {
            t.unregister();
        }
    }

    /**
     * Désinscrit un nombre donné d'étudiants d'un pays donné.
     * @param num : nombre d'étudiants à désinscrire
     * @param country : pays des étudiants à désinscrire
     */
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
