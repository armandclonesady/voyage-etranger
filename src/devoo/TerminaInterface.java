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
        System.out.print("\nChoisissez le fichier à utiliser : ");
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
     * @return un tableau de deux pays, le pays hôte
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
     * @return un tableau de deux pays, le pays invité
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
        System.out.println("\n1 : Importer un fichier CSV \n2 : Afficher les binomes \n3 : Paramètres \n4 : Fixer un binôme \n5 : Eviter un binôme \n6 : Changer les pays hote et inviter  \n7 : Quitter");
        System.out.print("\nChoisissez une option : ");
        try {
            choix = Integer.parseInt(sc.next());
            System.out.println("");
            if (choix < 1 || choix > 6){
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
                break;
            case 2:
                TerminaInterface.platform.affectation(TerminaInterface.hostCountry, TerminaInterface.guestCountry);
                System.out.println("\nVoici les binomes : \n");
                for( Map.Entry< Teenager, Teenager> e : TerminaInterface.platform.getCurrentAffectation().entrySet()){
                    System.out.println(e.getKey().toString() + " -> " + e.getValue().toString());
                }
                break;
            case 3:
                while(paramMenu()){}
                TerminaInterface.platform.affectation(TerminaInterface.hostCountry, TerminaInterface.guestCountry);
                break;
            case 4:
                pairFixedMenu();
                break;
            case 5:
                chooseHostCountry();
                chooseGuestCountry();
                break;
            case 6:
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
                //howPairFixed();
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
        for (Map.Entry<Teenager, Teenager> entry : TerminaInterface.platform.getPairFixed().entrySet()) {
            hostList.remove(entry.getKey());
        }
        hostList.sort(new IdComparator());
        for (Teenager t : hostList) {
            System.out.println(t.toString());
        }
        System.out.print("Etudiant hôtes choisi : ");
        try {
            int id = Integer.parseInt(sc.next());
            for (Teenager t : hostList) {
                if (t.getId() == id) {
                    return t;
                }
            }
        } catch (Exception e) {
            System.out.println("Veuillez entrer un nombre valide\n");
            chooseHostFixed();
        }
        return null;
    }

    public static Teenager chooseGuestFixed() {
        List<Teenager> guestList = TerminaInterface.platform.getGuest();
        for (Map.Entry<Teenager, Teenager> entry : TerminaInterface.platform.getPairFixed().entrySet()) {
            guestList.remove(entry.getValue());
        }
        guestList.sort(new IdComparator());
        for (Teenager t : guestList) {
            System.out.println(t.toString());
        }
        System.out.print("Etudiant visiteur choisi : ");
        try {
            int id = Integer.parseInt(sc.next());
            for (Teenager t : guestList) {
                if (t.getId() == id) {
                    return t;
                }
            }
        } catch (Exception e) {
            System.out.println("Veuillez entrer un nombre valide\n");
            chooseGuestFixed();
        }
        return null;
    }

    public static void removePairFixed() {
        int i = 0;
        int choix = 0;
        for (Map.Entry<Teenager, Teenager> entry : TerminaInterface.platform.getPairFixed().entrySet()) {
            System.out.println((i+1) + " : " + entry.getKey().toString() + " -> " + entry.getValue().toString());
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
        for (Map.Entry<Teenager, Teenager> entry : TerminaInterface.platform.getPairAvoided().entrySet()) {
            hostList.remove(entry.getKey());
        }
        hostList.sort(new IdComparator());
        for (Teenager t : hostList) {
            System.out.println(t.toString());
        }
        System.out.print("Etudiant hôtes choisi : ");
        try {
            int id = Integer.parseInt(sc.next());
            for (Teenager t : hostList) {
                if (t.getId() == id) {
                    return t;
                }
            }
        } catch (Exception e) {
            System.out.println("Veuillez entrer un nombre valide\n");
            chooseHostAvoided();
        }
        return null;
    }

    public static Teenager chooseGuestAvoided() {
        List<Teenager> guestList = TerminaInterface.platform.getGuest();
        for (Map.Entry<Teenager, Teenager> entry : TerminaInterface.platform.getPairAvoided().entrySet()) {
            guestList.remove(entry.getValue());
        }
        guestList.sort(new IdComparator());
        for (Teenager t : guestList) {
            System.out.println(t.toString());
        }
        System.out.print("Etudiant visiteur choisi : ");
        try {
            int id = Integer.parseInt(sc.next());
            for (Teenager t : guestList) {
                if (t.getId() == id) {
                    return t;
                }
            }
            throw new Exception();
        } catch (Exception e) {
            System.out.println("Veuillez entrer un nombre valide\n");
            chooseGuestAvoided();
        }
        return null;
    }

    // Permet l'utilisation de l'applications pour créer les binome des échange l'inguistique.
    public static void main(String[] args) {
        System.out.println("Bienvenue dans l'application de création de binome pour les échanges linguistiques\n");
        TerminaInterface.platform = new Platform();
        TerminaInterface.platform.importCSV(chooseCSV());
        chooseHostCountry();
        System.out.println("");
        chooseGuestCountry();
    
        while(menuInterface()){}
        System.exit(0);
    }
}