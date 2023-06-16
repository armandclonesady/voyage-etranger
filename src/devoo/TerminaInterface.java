package devoo;

import java.io.File;
import java.util.Map;

import graphes.*;

import java.util.Scanner;

public class TerminaInterface {

    static private Platform platform;


    /*
     * Permet de choisir le fichier CSV à utiliser dans le dissier rsc
     * @return le nom du fichier CSV
     */
    public static String chooseCSV(){
        // récupérer la liste des fichiers dans le dossier rsc (dossier de ressources) et vérifier si c'est un .csv
        File folder = new File("rsc");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (!isCSV(listOfFiles[i].getName())) {
                listOfFiles[i] = null;
            }
        }
        File[] csvFiles = new File[listOfFiles.length];
        // afficher la liste des fichiers .csv du dossier rsc
        int conteur =0;
        for (int i = 0; i < listOfFiles.length; i++) {
            if(listOfFiles[i] != null){
                csvFiles[conteur] = listOfFiles[i];
                conteur++;
                System.out.println((conteur) + " : " + listOfFiles[i].getName());
            }
        }

        System.out.println("Choisissez le fichier à utiliser : ");
        // utiliser un scanner pour choisir le fichier
        Scanner sc = new Scanner(System.in);
        int choix = sc.nextInt()-1;
        return csvFiles[choix].getName();
    }
    /*
     * fonction qui vériifie si le fichier est bien un .csv
     */
    public static boolean isCSV(String file){
        return file.endsWith(".csv");
    }

    /*
     * Permet de choisir le pays hote et le guest en meme temps
     */
    public static Country[] chooseCountry(){
        Country[] countries = new Country[2];
        for (int i = 0; i < Country.values().length; i++) {
            System.out.println((i+1) + " : " + Country.values()[i]);
        }
        System.out.println("Choisissez le pays Hôte : ");
        Scanner sc = new Scanner(System.in);
        int choix = sc.nextInt()-1;
        countries[0] = Country.values()[choix];
        // réafficher la liste des pays sans le pays choisit pour le pays hote
        for (int i = 0; i < Country.values().length; i++) {
            if(i != choix){
                System.out.println((i+1) + " : " + Country.values()[i]);
            }
        }
        System.out.println("Choisissez le pays Visiteur : ");
        int newChoix;
        do{
        newChoix = sc.nextInt()-1;}
        while(newChoix == choix);
        countries[1] = Country.values()[newChoix];
        return countries;
    }

    public static boolean menuInterface(Country[] hostAndGuest){
        System.out.println("1 : Importer un fichier CSV \n2 : Afficher les binomes \n3 : paraméère \n4 : Créer des binôme \n5 : Changer les pays hote et inviter  \n6 : Quitter");
        Scanner sc = new Scanner(System.in);
        int choix = sc.nextInt();
        if(choix == 1){
            TerminaInterface.platform.importCSV(chooseCSV());
        }else if(choix == 2){
            TerminaInterface.platform.affectation(hostAndGuest[0], hostAndGuest[1]);
            System.out.println("Voici les binomes : ");
            for( Map.Entry< Teenager, Teenager> e : TerminaInterface.platform.getAffectation().entrySet()){
                System.out.println(e.getKey().toString() + " -> " + e.getValue().toString());
            }

        }else if(choix == 3){
            while(parametreMenu()){}
            TerminaInterface.platform.affectation(hostAndGuest[0], hostAndGuest[1]);
        }else if(choix == 4){
            TerminaInterface.platform.affectation(hostAndGuest[0], hostAndGuest[1]);
            addBinome(hostAndGuest);
        }else if(choix == 5){
            hostAndGuest =chooseCountry();
            TerminaInterface.platform.affectation(hostAndGuest[0], hostAndGuest[1]);
        }else if(choix == 6){
            return false;
        }
        return true;
    }

    // choix entre modifier et reset
    public static int option(){
        System.out.print("1 : modifier \n2 : reset\nChoix  : ");
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        return option;
    }

    // menu des parametre
    public static boolean  parametreMenu(){
        System.out.print("1 : rétablir tout les poids\n2 : poids de l'historique : " + AffectationUtil.getWeightHistory()+
            "\n3 : poids des allergie : "+AffectationUtil.getWeightAllergy() +
            "\n4 : poids de l'alimentation : "+AffectationUtil.getWeightFood() +
            "\n5 : poids des hobbies :" +AffectationUtil.getWeightHobbies()+
            "\n6 : poids de sur la préfèrence des genres :" +AffectationUtil.getWeightGender() + 
            "\n7 : retour menu\nChoix : ");
        Scanner sc = new Scanner(System.in);
        int choix = sc.nextInt();
        if(choix==1){AffectationUtil.allReset();}

        if( choix == 2){
            if(option() == 1){
                System.out.print("nouveau poids : ");
                Scanner scann = new Scanner(System.in);
                int newPoids  = scann.nextInt();
                AffectationUtil.setWeightHistory(newPoids);
            }else{AffectationUtil.resetWeightHistory();}
        }
        if(choix == 3){
            if(option()==1){
                System.out.print("nouveau poids : ");
                Scanner scann = new Scanner(System.in);
                int newPoids  = scann.nextInt();
                AffectationUtil.setWeightAllergy(newPoids);
            }else{AffectationUtil.resetWeightAllergy();}
        }
        if(choix == 4){
            if(option()==1){
                System.out.print("nouveau poids : ");
                Scanner scann = new Scanner(System.in);
                int newPoids  = scann.nextInt();
                AffectationUtil.setWeightFood(newPoids);
            }else{AffectationUtil.resetWeightFood();}
        }
        if(choix == 5){
            if(option()==1){
                System.out.print("nouveau poids : ");
                Scanner scann = new Scanner(System.in);
                int newPoids  = scann.nextInt();
                AffectationUtil.setWeightHobbies(newPoids);
            }else{AffectationUtil.resetWeightHobbies();}
        }
        if(choix == 6){
            if(option()==1){
                System.out.print("nouveau poids : ");
                Scanner scann = new Scanner(System.in);
                int newPoids  = scann.nextInt();
                AffectationUtil.setWeightGender(newPoids);
            }else{AffectationUtil.resetWeightGender();}
        }
        if(choix == 7)return false;
        return true;
    }

    public static void addBinome(Country[] hostAndGuest){
        TerminaInterface.platform.clearHost();
        TerminaInterface.platform.clearGuest();
        TerminaInterface.platform.makeHostAndGuest(hostAndGuest[0], hostAndGuest[1]);

        int choice[] = new int[2];

        for (Teenager t : TerminaInterface.platform.getHost()) {
            System.out.println((TerminaInterface.platform.getHost().lastIndexOf(t)+1) + " : " + t.toString());
        }
        System.out.print("Etudiant host choisi : ");
        Scanner sc = new Scanner(System.in);
        choice[0] = sc.nextInt();

        System.out.println("");

        for (Teenager t : TerminaInterface.platform.getGuest()) {
            System.out.println((TerminaInterface.platform.getGuest().lastIndexOf(t)+1) + " : " + t.toString());
        }
        System.out.print("Etudiant guest choisi : ");
        choice[1] = sc.nextInt();

        System.out.println("");

        Teenager host = TerminaInterface.platform.getHost().get(choice[0]-1);
        Teenager guest = TerminaInterface.platform.getGuest().get(choice[1]-1);

        System.out.println(host.toString() + " -> " + guest.toString());
        TerminaInterface.platform.addPairFixed(host, guest);
        TerminaInterface.platform.affectation(hostAndGuest[0], hostAndGuest[1]);
    }

    // Permet l'utilisation de l'applications pour créer les binome des échange l'inguistique.
    public static void main(String[] args) {
        System.out.println("Bienvenue dans l'application de création de binome pour les échanges linguistiques");
        TerminaInterface.platform = new Platform();
        TerminaInterface.platform.importCSV(chooseCSV());
        System.out.println("choisiser le pays Hôte : ");
        Country[] hostAndGuest = chooseCountry();
    
        while(menuInterface(hostAndGuest)){}
        System.exit(0);
    }
}