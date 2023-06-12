package ihm;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * ModifPonderation class
 * @author Elise Leroy
 */

public class EliseCode extends Application {

    public void start(Stage stage) {
        //récupère les pondérations de base
        /*final int[] tableauReset = new int[]{(int)AffectationUtil.getResetFoodWeight(),
                                        (int)AffectationUtil.getResetHobbiesWeight(),
                                        (int)AffectationUtil.getResetAllergyWeight(),
                                        (int)AffectationUtil.getResetSuperCompatibleHistoryWeight(),
                                        (int)AffectationUtil.getResetLessCompatibleHistoryWeight(),
                                        (int)AffectationUtil.getResetGenderWeight(),
                                        (int)AffectationUtil.getResetAgeWeight()};

        final int[] changedValueTab = tableauReset;
        */
        VBox root = new VBox();
        HBox conteneurPrincipal = new HBox(90);

        Label titre = new Label("UniCo  | Modification des pondérations");
        titre.setFont(Font.font("Bahnschrift", FontWeight.BOLD, null, 34));
        titre.setPadding(new Insets(20, 0, 0, 20));
        Button continuer = new Button("Valider et continuer");
        continuer.setStyle("-fx-border-style: solid;"+
                "-fx-border-color: lightgreen;"+
                "-fx-background-radius: 10px;"+
                "-fx-border-radius: 10px;"+
                "-fx-padding: 10 30;"+
                "-fx-font-size: 16px;"+
                "-fx-background-color: lightgreen;");


        
        String[] labelTab = new String[7];
        labelTab[0] = "Malus d’incompatibilité alimentaire";
        labelTab[1]= "Bonus de hobby similaire";
        labelTab[2] = "Malus d’allergie aux animaux incompatible";
        labelTab[3] = "Valeur de compatibilité de l’historique 1";
        labelTab[4] = "Valeur de compatibilité de l’historique 2";
        labelTab[5] = "Bonus de compatibilité du genre";
        labelTab[6] = "Bonus de compatibilité du même âge";

        String[] noticeTab = new String[3];
        noticeTab[0] = "/!\\ Attention à la modification de cette valeur";
        noticeTab[1] = "Dans le cas où les deux adolescents souhaiteraient être ensemble ou non";
        noticeTab[2] = "Dans le cas ou seul un des deux adolescents se serait prononcé sur\n                           le souhait ou non d’être avec le même hôte/invité";

        VBox conteneurTexte = new VBox(30);
        for(int i=0; i<7; i++){
            Label label = new Label(labelTab[i]);
            label.setFont(Font.font("Banhnschrift", FontWeight.NORMAL, null, 20));
            VBox conteneurHoriTexte = new VBox();
            if(i==2 || i==3 || i==4){
                Label notice = new Label(noticeTab[i-2]);
                notice.setFont(Font.font("Banhnschrift", FontWeight.SEMI_BOLD, null, 14));
                if(i==2){
                    notice.setStyle("-fx-text-fill: red;");
                }
                conteneurHoriTexte.getChildren().addAll(label, notice);
            } else {
                conteneurHoriTexte.getChildren().add(label);
            }
            conteneurHoriTexte.setAlignment(Pos.CENTER_RIGHT);
            conteneurTexte.getChildren().add(conteneurHoriTexte);
            conteneurTexte.setAlignment(Pos.CENTER_RIGHT);
        }

        VBox conteneurBoutons = new VBox();
        for(int i=0; i<7; i++){

            Button moins = new Button(" - ");
            moins.setPrefSize(32, 30); 
            TextField valeur = new TextField("");
            valeur.setPrefSize(70, 30);
            valeur.setAlignment(Pos.CENTER);

            Button plus = new Button(" + ");
            plus.setPrefSize(32, 30);

            moins.setOnMouseClicked(e ->{
                int currentValue = Integer.parseInt(valeur.getText());
                valeur.setText(""+(currentValue - 1));
                //changedValueTab[cpt] = currentValue -1;
            });

            plus.setOnMouseClicked(e ->{
                int currentValue = Integer.parseInt(valeur.getText());
                valeur.setText(""+(currentValue + 1));
            });

            VBox retablirBox = new VBox();
            Button retablir = new Button("Rétablir");
            retablir.setPrefSize(70, 30);

            
            retablir.setOnMouseClicked(e ->{
                valeur.setText(""+(0));
            });

            retablirBox.getChildren().add(retablir);
            retablirBox.setPadding(new Insets(0, 0, 0, 30));



            HBox conteneurHoriBouton = new HBox(10);
            conteneurHoriBouton.getChildren().addAll(moins, valeur, plus, retablirBox);
            if(i==2 || i==3){
                conteneurHoriBouton.setPadding(new Insets(3, 0, 46, 0));
            } else if(i==4){
                conteneurHoriBouton.setPadding(new Insets(3, 0, 60, 0));
            } else {
                conteneurHoriBouton.setPadding(new Insets(6, 0, 28, 0));
            }
            
            conteneurBoutons.setPadding(new Insets(20, 0, 0, 0));
            conteneurBoutons.getChildren().add(conteneurHoriBouton);
            conteneurBoutons.setAlignment(Pos.TOP_LEFT);

            
        }

        conteneurPrincipal.getChildren().addAll(conteneurTexte, conteneurBoutons);
        conteneurPrincipal.setPadding(new Insets(50, 0, 90, 100));

        continuer.setOnMouseClicked(e ->{
            /*
            AffectationUtil.setFoodWeight((double)changedValueTab[0]);
            AffectationUtil.setHobbiesWeight((double)changedValueTab[1]);
            AffectationUtil.setAllergyWeight((double)changedValueTab[2]);
            AffectationUtil.setSuperCompatibleHistoryWeight((double)changedValueTab[3]);
            AffectationUtil.setLessCompatibleHistoryWeight((double)changedValueTab[4]);
            AffectationUtil.setGenderWeight((double)changedValueTab[5]);
            AffectationUtil.setAgeWeight((double)changedValueTab[6]);
            System.out.println(
                    "Food :" + AffectationUtil.foodWeight +"\n"+
                    "Hobby :" + AffectationUtil.hobbiesWeight +"\n"+
                    "Allergy :" + AffectationUtil.allergyWeight +"\n"+
                    "Super compatibility :" + AffectationUtil.superCompatibleHistoryWeight +"\n"+
                    "Less Compatible :" + AffectationUtil.lessCompatibleHistoryWeight +"\n"+
                    "Gender :" + AffectationUtil.genderWeight +"\n"+
                    "Age :" + AffectationUtil.ageWeight +"\n"
            );
            javafx.application.Application.launch(RetirerEleves.class);
             */
        });
        
        VBox alignementCentre = new VBox();
        alignementCentre.getChildren().addAll(conteneurPrincipal, continuer);
        alignementCentre.setAlignment(Pos.CENTER);

        root.getChildren().addAll(titre, alignementCentre);

        Scene scene = new Scene(root, 1194, 834);
        stage.setScene(scene);
        stage.setTitle("UniCo - Modification des pondérations");
        stage.show();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
    
}