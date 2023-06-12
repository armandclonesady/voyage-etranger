package devoo;

import java.io.File;
import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.Map;

import graphes.*;

import java.util.Scanner;

public class TerminaInterface {


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

    public static boolean menuInterface(Platform plat){
        System.out.println("1 : Importer un fichier CSV \n2 : Afficher les binomes \n3 : paraméère \n4 : Créer des binôme \n5 : Changer les pays hote et inviter  \n6 : Quitter");
        Scanner sc = new Scanner(System.in);
        int choix = sc.nextInt();
        if(choix == 1){
            plat.importCSV(chooseCSV());
        }else if(choix == 2){
            System.out.println("Voici les binomes : ");
            for( Map.Entry< Teenager, Teenager> e : plat.getAffectation().entrySet()){
                System.out.println(e.getKey().toString() + " -> " + e.getValue().toString());
            }
            try{
                Thread.sleep(10000);
            } catch( InterruptedException e){
                e.printStackTrace();
            }

        }else if(choix == 3){
            while(parametreMenu()){}
        }else if(choix == 4){
            // créer des binomes
        }else if(choix == 5){
            Country[] hostAndGuest =chooseCountry();
                plat.affectation(hostAndGuest[0], hostAndGuest[1]);
        }else if(choix == 6){
            return false;
        }
        return true;
    }


    public static int option(){
        System.out.print("1 : modifier \n2 : reset\nChoix  : ");
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        return option;
    }

    // menu des parametre
    public static boolean  parametreMenu(){
        System.out.print("1 : tout rétablir les ppoids\n2 : poids de l'historique : " + AffectationUtil.getWeightHistori()+
            "\n3 : poids des allergie : "+AffectationUtil.getWeightAlergi() +
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
                AffectationUtil.setWeightHistori(newPoids);
            }else{AffectationUtil.resetWeightHistori();}
        }
        if(choix == 3){
            if(option()==1){
                System.out.print("nouveau poids : ");
                Scanner scann = new Scanner(System.in);
                int newPoids  = scann.nextInt();
                AffectationUtil.setWeightAlergi(newPoids);
            }else{AffectationUtil.resetWeightAlergi();}
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
           
    /*
     * Permet l'utilisation de l'applications pour créer les binome des échange l'inguistique
     */
    public static void main(String[] args) {
        System.out.println("Bienvenue dans l'application de création de binome pour les échanges linguistiques");
        Platform plat = new Platform();
        plat.importCSV(chooseCSV());
        System.out.println("choisiser le pays Hôte : ");
        Country[] hostAndGuest =chooseCountry();
        plat.affectation(hostAndGuest[0], hostAndGuest[1]);
        while(menuInterface(plat)){}
        System.exit(0);
    }
}