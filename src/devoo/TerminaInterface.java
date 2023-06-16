package devoo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import graphes.*;

import java.util.Scanner;

public class TerminaInterface {

    static private Platform platform;
    static private Scanner sc = new Scanner(System.in);
    static private Country hostCountry;
    static private Country guestCountry;
    static private File historyFile;

    /**
     * Récupère la liste des fichiers CSV dans le dossier rsc et demande à l'utilisateur de choisir le fichier à utiliser
     * @return le fichier choisi
     */
    public static File chooseCSV(){
        File folder = new File(Platform.ressourcesPath.getAbsolutePath());
        List<File> csvFiles = new ArrayList<File>();
        for (int i = 0; i < folder.listFiles().length; i++) {
            if (isCSV(folder.listFiles()[i])) {
                csvFiles.add(folder.listFiles()[i]);
            }
        }
        for (File file : csvFiles) {
            System.out.println((csvFiles.indexOf(file)+1) + " : " + file.getName());
        }
        System.out.print("Choisissez le fichier à utiliser : ");
        try {
            int choix = sc.nextInt()-1;
            System.out.println("");
            return csvFiles.get(choix);
        } 
        catch (Exception e) {
            System.out.println("Veuillez entrer un nombre valide\n");
            return chooseCSV();
        }
    }

    /**
     * Vérifie si le fichier est un fichier CSV
     * @param file : le fichier à vérifier
     * @return true si le fichier est un fichier CSV, false sinon
     */
    public static boolean isCSV(File file){
        return file.getName().endsWith(".csv");
    }

    /**
     * Récupère la liste des pays et demande à l'utilisateur de choisir le pays hôte
     */
    public static void chooseHostCountry(){
        int choix = 0;
        for (Country country : Country.values()) {
            System.out.println((country.ordinal()+1) + " : " + country);
        }
        System.out.print("Choisissez le pays hôte : ");
        try {
            choix = Integer.parseInt(sc.next())-1;
            hostCountry = Country.values()[choix];
        } 
        catch (Exception e) {
            System.out.println("\nVeuillez entrer un nombre valide\n");
            chooseHostCountry();
        }
    }

    /**
     * Récupère la liste des pays et demande à l'utilisateur de choisir le pays invité
     */
    public static void chooseGuestCountry(){
        List<Country> countries = Arrays.asList(Country.values());
        int choix = 0;
        for (Country country : countries) {
            if (country != TerminaInterface.hostCountry) {
                System.out.println((country.ordinal()+1) + " : " + country);
            }
        }
        System.out.print("Choisissez le pays invité : ");
        try {
            choix = Integer.parseInt(sc.next())-1;
            if (choix == hostCountry.ordinal()) {
                System.out.println("\nVeuillez entrer un nombre valide\n");
                chooseGuestCountry();
            }
            guestCountry = countries.get(choix);
        } 
        catch (Exception e) {
            System.out.println("Veuillez entrer un nombre valide\n");
            chooseGuestCountry();
        }
    }

    public static boolean menuInterface(){
        int choix = 0;
        System.out.println("\n1 : Importer un fichier CSV\n2 : Afficher les binomes\n3 : Afficher les infos d'un participant\n4 : Paramètres\n5 : Historique\n6 : Fixer un binôme\n7 : Eviter un binôme\n8 : Changer les pays hote et invité\n9 : Quitter");
        System.out.print("Choisissez une option : ");
        try {
            choix = Integer.parseInt(sc.next());
            System.out.println("");
            if (choix < 1 || choix > 9){
                System.out.println("Veuillez entrer un nombre valide");
                return menuInterface();
            }
        } catch (Exception e) {
            System.out.println("Veuillez entrer un nombre valide");
            return menuInterface();
        }
        switch (choix) {
            case 1:
                TerminaInterface.platform.importCSV(chooseCSV());
                TerminaInterface.historyFile = null;
                break;
            case 2:
                TerminaInterface.platform.affectation(TerminaInterface.hostCountry, TerminaInterface.guestCountry);
                List<Map.Entry<Teenager,Teenager>> listCouple = new ArrayList<>(TerminaInterface.platform.getCurrentAffectation().entrySet());
                listCouple.sort(new AffectListComparator());
                System.out.println("\nVoici les binomes :\n");
                for(Map.Entry<Teenager,Teenager> e : listCouple) {
                    System.out.println(e.getKey().toString() + " -> " + e.getValue().toString());
                }
                break;
            case 3:
                showStudentInfo();
                break;
            case 4:
                while(paramMenu()){}
                TerminaInterface.platform.affectation(TerminaInterface.hostCountry, TerminaInterface.guestCountry);
                break;
            case 5:
                historyMenu();
            case 6:
                pairFixedMenu();
                break;
            case 7:
                pairAvoidedMenu();
                break;
            case 8:
                chooseHostCountry();
                chooseGuestCountry();
                TerminaInterface.historyFile = null;
                break;
            case 9:
                return false;
        }
        return true;
    }

    // choix entre modifier et reset
    public static int paramOption(){
        System.out.print("1 : Modifier la valeur\n2 : Rétablir la valeur\nChoix  : ");
        int option = 0;
        try {
            option = Integer.parseInt(sc.next());
            if (option < 1 || option > 2){
                System.out.println("Veuillez entrer un nombre valide");
                return paramOption();
            }
        } catch (Exception e) {
            System.out.println("Veuillez entrer un nombre valide");
            return paramOption();
        }
        return option;
    }

    // menu des parametre
    public static boolean paramMenu() {
        int choix = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("\n1 : Rétablir tout les poids par défaut");
        sb.append("\n2 : Poids de l'historique : %d");
        sb.append("\n3 : Poids des allergie : %d");
        sb.append("\n4 : Poids de l'alimentation : %d");
        sb.append("\n5 : Poids des hobbies : %d");
        sb.append("\n6 : Poids de la préférences de genre : %d");
        sb.append("\n7 : Retour menu\nChoix : ");
        System.out.print(String.format(sb.toString(), 
            AffectationUtil.getWeightHistory(), 
            AffectationUtil.getWeightAllergy(), 
            AffectationUtil.getWeightFood(), 
            AffectationUtil.getWeightHobbies(), 
            AffectationUtil.getWeightGender()));
        try {
            choix = Integer.parseInt(sc.next());
            if (choix < 1 || choix > 7){
                System.out.println("Veuillez entrer un nombre valide\n");
                return menuInterface();
            }
        } catch (Exception e) {
            System.out.println("Veuillez entrer un nombre valide\n");
            return menuInterface();
        }
        if(choix == 1) {
            AffectationUtil.allReset();
        }
        if(choix == 2) {
            switch (paramOption()) {
                case 1:
                    System.out.print("Nouveau poids : ");
                    try {
                        int newPoids  = Integer.parseInt(sc.next());
                        AffectationUtil.setWeightAllergy(newPoids);
                    } catch (Exception e) {
                        System.out.println("Veuillez entrer un nombre valide\n");
                        break;
                    }
                case 2:
                    AffectationUtil.resetWeightHistory();
                    break;
                case 3:
                    break;
            }
        }
        if(choix == 3) {
            switch (paramOption()) {
                case 1:
                    System.out.print("Nouveau poids : ");
                    try {
                        int newPoids  = Integer.parseInt(sc.next());
                        AffectationUtil.setWeightAllergy(newPoids);
                    } catch (Exception e) {
                        System.out.println("Veuillez entrer un nombre valide\n");
                        break;
                    }
                    break;
                case 2:
                    AffectationUtil.resetWeightAllergy();
                    break;
                case 3:
                    break;
            }
        }
        if(choix == 4) {
            switch (paramOption()) {
                case 1:
                    System.out.print("Nouveau poids : ");
                    try {
                        int newPoids  = Integer.parseInt(sc.next());
                        AffectationUtil.setWeightFood(newPoids);
                    } catch (Exception e) {
                        System.out.println("Veuillez entrer un nombre valide\n");
                        break;
                    }
                    break;
                case 2:
                    AffectationUtil.resetWeightFood();
                    break;
                case 3:
                    break;
            }
        }
        if(choix == 5) {
            switch (paramOption()) {
                case 1:
                    System.out.print("Nouveau poids : ");
                    try {
                        int newPoids  = Integer.parseInt(sc.next());
                        AffectationUtil.setWeightHobbies(newPoids);
                    } catch (Exception e) {
                        System.out.println("Veuillez entrer un nombre valide\n");
                        break;
                    }
                    break;
                case 2:
                    AffectationUtil.resetWeightHobbies();
                    break;
                case 3:
                    break;
            }
        }
        if(choix == 6) {
            switch (paramOption()) {
                case 1:
                    System.out.print("Nouveau poids : ");
                    try {
                        int newPoids  = Integer.parseInt(sc.next());
                        AffectationUtil.setWeightGender(newPoids);
                    } catch (Exception e) {
                        System.out.println("Veuillez entrer un nombre valide\n");
                        break;
                    }
                    break;
                case 2:
                    AffectationUtil.resetWeightGender();
                    break;
                case 3:
                    break;
            }
        }
        if(choix == 7) return false;
        return true;
    }

    public static void pairFixedMenu() {
        int choix = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("\n1 : Ajouter un couple fixe");
        sb.append("\n2 : Supprimer un couple fixe");
        sb.append("\n3 : Afficher les couples fixes");
        sb.append("\n4 : Retour menu\nChoix : ");
        System.out.print(sb.toString());
        try {
            choix = Integer.parseInt(sc.next());
            if (choix < 1 || choix > 4){
                System.out.println("Veuillez entrer un nombre valide\n");
                pairFixedMenu();
            }
        } catch (Exception e) {
            System.out.println("Veuillez entrer un nombre valide\n");
            pairFixedMenu();
        }
        switch (choix) {
            case 1:
                addPairFixed();
                break;
            case 2:
                removePairFixed();
                break;
            case 3:
                if (TerminaInterface.platform.getPairFixed().isEmpty()) {
                    System.out.println("Aucun couple fixe");
                    break;
                }
                for (Map.Entry<Teenager, Teenager> entry : TerminaInterface.platform.getPairFixed().entrySet()) {
                    System.out.println(entry.getKey().toString() + " -> " + entry.getValue().toString());
                }
                break;
            case 4:
                return;
        }
        pairFixedMenu();
    }

    public static void addPairFixed() {
        TerminaInterface.platform.makeHostAndGuest(TerminaInterface.hostCountry, TerminaInterface.guestCountry);
        Teenager host = chooseHostFixed();
        Teenager guest = chooseGuestFixed();
        System.out.println(host.toString() + " -> " + guest.toString());
        TerminaInterface.platform.addPairFixed(host, guest);
        TerminaInterface.platform.affectation(TerminaInterface.hostCountry, TerminaInterface.guestCountry);
    }

    public static Teenager chooseHostFixed() {
        List<Teenager> hostList = TerminaInterface.platform.getHost();
        Teenager host = null;
        int choix = 0;
        for (Map.Entry<Teenager, Teenager> entry : TerminaInterface.platform.getPairFixed().entrySet()) {
            hostList.remove(entry.getKey());
        }
        int i = 1;
        for (Teenager t : hostList) {
            System.out.println(i + " : " + t.toString());
            i++;
        }
        System.out.print("Etudiant hôtes choisi : ");
        try {
            choix = Integer.parseInt(sc.next());
            host = hostList.get(choix - 1);
        } 
        catch (Exception e) {
            System.out.println("Veuillez entrer un nombre valide\n");
            return chooseHostFixed();
        }
        return host;
    }

    public static Teenager chooseGuestFixed() {
        List<Teenager> guestList = TerminaInterface.platform.getGuest();
        Teenager guest = null;
        int choix = 0;
        for (Map.Entry<Teenager, Teenager> entry : TerminaInterface.platform.getPairFixed().entrySet()) {
            guestList.remove(entry.getValue());
        }
        int i = 1;
        for (Teenager t : guestList) {
            System.out.println(i + " : " + t.toString());
            i++;
        }
        System.out.print("Etudiant visiteur choisi : ");
        try {
            choix = Integer.parseInt(sc.next());
            guest = guestList.get(choix - 1);
        } catch (Exception e) {
            System.out.println("Veuillez entrer un nombre valide\n");
            return chooseGuestFixed();
        }
        return guest;
    }

    public static void removePairFixed() {
        int i = 1;
        int choix = 0;
        if (TerminaInterface.platform.getPairFixed().isEmpty()) {
            System.out.println("Il n'y a pas de couple fixe\n");
            return;
        }
        for (Map.Entry<Teenager, Teenager> entry : TerminaInterface.platform.getPairFixed().entrySet()) {
            System.out.println((i) + " : " + entry.getKey().toString() + " -> " + entry.getValue().toString());
            i++;
        }
        try {
            choix = Integer.parseInt(sc.next());
            if (choix < 1 || choix > TerminaInterface.platform.getPairFixed().size()) {
                System.out.println("Veuillez entrer un nombre valide\n");
                removePairFixed();
            }
        } catch (Exception e) {
            System.out.println("Veuillez entrer un nombre valide\n");
            removePairFixed();
        }
    }

    public static void pairAvoidedMenu() {
        int choix = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("\n1 : Ajouter un couple évité");
        sb.append("\n2 : Supprimer un couple évité");
        sb.append("\n3 : Afficher les couples évités");
        sb.append("\n4 : Retour menu\nChoix : ");
        System.out.print(sb.toString());
        try {
            choix = Integer.parseInt(sc.next());
            if (choix < 1 || choix > 4){
                System.out.println("Veuillez entrer un nombre valide\n");
                pairAvoidedMenu();
            }
        } catch (Exception e) {
            System.out.println("Veuillez entrer un nombre valide\n");
            pairAvoidedMenu();
        }
        switch (choix) {
            case 1:
                addAvoidedPair();
                break;
            case 2:
                removeAvoidedPair();
                break;
            case 3:
                if (TerminaInterface.platform.getPairAvoided().isEmpty()) {
                    System.out.println("Aucun couple évité");
                    break;
                }
                for (Map.Entry<Teenager, Teenager> entry : TerminaInterface.platform.getPairAvoided().entrySet()) {
                    System.out.println(entry.getKey().toString() + " -> " + entry.getValue().toString());
                }
                break;
            case 4:
                return;
        }
        pairAvoidedMenu();
    }

    public static void addAvoidedPair() {
        TerminaInterface.platform.makeHostAndGuest(TerminaInterface.hostCountry, TerminaInterface.guestCountry);
        Teenager host = chooseHostAvoided();
        Teenager guest = chooseGuestAvoided();
        System.out.println(host.toString() + " -> " + guest.toString());
        TerminaInterface.platform.addPairAvoided(host, guest);
        TerminaInterface.platform.affectation(TerminaInterface.hostCountry, TerminaInterface.guestCountry);
    }

    public static Teenager chooseHostAvoided() {
        List<Teenager> hostList = TerminaInterface.platform.getHost();
        Teenager host = null;
        int choix = 0;
        for (Map.Entry<Teenager, Teenager> entry : TerminaInterface.platform.getPairAvoided().entrySet()) {
            hostList.remove(entry.getKey());
        }
        int i = 1;
        for (Teenager t : hostList) {
            System.out.println(i + " : " + t.toString());
            i++;
        }
        System.out.print("Etudiant visiteur choisi : ");
        try {
            choix = Integer.parseInt(sc.next());
            host = hostList.get(choix - 1);
        } catch (Exception e) {
            System.out.println("Veuillez entrer un nombre valide\n");
            return chooseGuestFixed();
        }
        return host;
    }

    public static Teenager chooseGuestAvoided() {
        List<Teenager> guestList = TerminaInterface.platform.getGuest();
        Teenager guest = null;
        int choix = 0;
        for (Map.Entry<Teenager, Teenager> entry : TerminaInterface.platform.getPairAvoided().entrySet()) {
            guestList.remove(entry.getValue());
        }
        int i = 1;
        for (Teenager t : guestList) {
            System.out.println(i + " : " + t.toString());
            i++;
        }
        System.out.print("Etudiant visiteur choisi : ");
        try {
            choix = Integer.parseInt(sc.next());
            guest = guestList.get(choix - 1);
        } catch (Exception e) {
            System.out.println("Veuillez entrer un nombre valide\n");
            return chooseGuestFixed();
        }
        return guest;
    }

    public static void removeAvoidedPair() {
        int i = 1;
        int choix = 0;
        if (TerminaInterface.platform.getPairAvoided().isEmpty()) {
            System.out.println("Il n'y a pas de couple évité\n");
            return;
        }
        for (Map.Entry<Teenager, Teenager> entry : TerminaInterface.platform.getPairAvoided().entrySet()) {
            System.out.println((i) + " : " + entry.getKey().toString() + " -> " + entry.getValue().toString());
            i++;
        }
        try {
            choix = Integer.parseInt(sc.next());
            if (choix < 1 || choix > TerminaInterface.platform.getPairAvoided().size()) {
                System.out.println("Veuillez entrer un nombre valide\n");
                removeAvoidedPair();
            }
        } catch (Exception e) {
            System.out.println("Veuillez entrer un nombre valide\n");
            removeAvoidedPair();
        }
    }

    public static void showStudentInfo() {
        List<Teenager> listStudents = new ArrayList<>(TerminaInterface.platform.getStudents());
        listStudents.sort(new IdComparator());
        int i = 1;
        for (Teenager student : listStudents) {
            System.out.println(i + " : " + student);
            i++;
        }
        try {
            System.out.print("Choisissez un participant : ");
            int choixStudent = Integer.parseInt(sc.next())-1;
            System.out.println("");
            Teenager student = listStudents.get(choixStudent);
            System.out.println(student.toString() + " " + student.getBirth().toString() + "\n\n" + "COUNTRY : " + student.getCountry());
            for (Map.Entry<CriterionName,Criterion> criterion : student.getRequirements().entrySet()) {
                if (criterion.getValue() == null) {
                    System.out.println(criterion.getKey() + " : Non renseigné");
                }
                else {
                    System.out.println(criterion.getKey() + " : " + criterion.getValue().getValue());
                }
            }
        } catch (Exception e) {
            System.out.println("Veuillez entrer un nombre valide");
            showStudentInfo();
        }
    }

    public static boolean isHistory(File file) {
        return file.getName().endsWith(".bin") && file.getName().startsWith(hostCountry.name() + "-" + guestCountry.name());
    }

    public static void chooseHistory() {
        File folder = new File(Platform.historyPath.getAbsolutePath());
        List<File> historyFiles = new ArrayList<File>();
        for (int i = 0; i < folder.listFiles().length; i++) {
            if (isHistory(folder.listFiles()[i])) {
                historyFiles.add(folder.listFiles()[i]);
            }
        }
        for (File file : historyFiles) {
            System.out.println((historyFiles.indexOf(file)+1) + " : " + file.getName());
        }
        System.out.print("Choisissez le fichier à utiliser : ");
        try {
            int choix = sc.nextInt()-1;
            System.out.println("");
            TerminaInterface.platform.loadHistory(historyFiles.get(choix));
            TerminaInterface.historyFile = historyFiles.get(choix);
        } 
        catch (Exception e) {
            System.out.println("Veuillez entrer un nombre valide\n");
            chooseHistory();
        }
    }

    public static void historyMenu() {
        System.out.println("Historique : ");
        if (TerminaInterface.historyFile == null) {
            System.out.println("Non chargé\n");
        }
        else {
            System.out.println(historyFile.getName() + "\n");
        }
         
        System.out.println("1 : Charger un historique");
        System.out.println("2 : Sauvegarder les affectations");
        System.out.println("3 : Retour");
        int choix = 0;
        try {
            choix = Integer.parseInt(sc.next());
            if (choix < 1 || choix > 3) {
                System.out.println("Veuillez entrer un nombre valide\n");
                historyMenu();
            }
        } catch (Exception e) {
            System.out.println("Veuillez entrer un nombre valide\n");
            historyMenu();
        }
        switch (choix) {
            case 1:
                chooseHistory();
                break;
            case 2:
                TerminaInterface.platform.exportBin();
                break;
            case 3:
                return;
        }
        historyMenu();
    }

    // Permet l'utilisation de l'applications pour créer les binome des échange l'inguistique.
    public static void main(String[] args) {
        TerminaInterface.sc = new Scanner(System.in);
        System.out.println("Bienvenue dans l'application de création de binome pour les échanges linguistiques\n");
        TerminaInterface.platform = new Platform();
        TerminaInterface.platform.importCSV(chooseCSV());
        chooseHostCountry();
        System.out.println("");
        chooseGuestCountry();
    
        while(menuInterface()){}
        TerminaInterface.sc.close();
        System.exit(0);
    }
}