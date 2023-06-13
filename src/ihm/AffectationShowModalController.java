package ihm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import devoo.CriterionName;
import devoo.Teenager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AffectationShowModalController {
    
    @FXML Label hostLabel;
    @FXML Label guestLabel;

    @FXML Label hostAnimal;
    @FXML Label guestAnimal;

    @FXML Label hostFood;
    @FXML Label guestFood;

    @FXML Label hostHobbies;
    @FXML Label guestHobbies;

    @FXML Label hostGender;
    @FXML Label guestGender;

    @FXML Label historyValue;

    @FXML Label resultAnimal;
    @FXML Label resultFood;
    @FXML Label resultHobbies;
    @FXML Label resultGender;

    public static Map.Entry<Teenager,Teenager> currentEntry;

    public void initialize() {
        Teenager host = currentEntry.getKey();
        Teenager guest = currentEntry.getValue();

        hostLabel.setText(host.getName());
        guestLabel.setText(guest.getName());

        if (host.getCriterion(CriterionName.HOST_HAS_ANIMAL).getValue() != null 
            && host.getCriterion(CriterionName.HOST_HAS_ANIMAL).getValue().equals("yes")) {
            hostAnimal.setText("Oui");
        } else {
            hostAnimal.setText("Non");
        }

        if (guest.getCriterion(CriterionName.GUEST_ANIMAL_ALLERGY).getValue() != null 
            && guest.getCriterion(CriterionName.GUEST_ANIMAL_ALLERGY).getValue().equals("yes")) {
            guestAnimal.setText("Oui");
        } else {
            guestAnimal.setText("Non");
        }

        if (host.getCriterion(CriterionName.HOST_FOOD).getValue() != null 
            && host.getCriterion(CriterionName.HOST_FOOD).getValue().equals("vegetarian")) {
            hostFood.setText("Végétarien");
        } else if (host.getCriterion(CriterionName.HOST_FOOD).getValue() != null 
            && host.getCriterion(CriterionName.HOST_FOOD).getValue().equals("nonuts")) {
            hostFood.setText("Sans fruits à coque");
        } else {
            hostFood.setText("Aucun");
        }

        if (guest.getCriterion(CriterionName.GUEST_FOOD).getValue() != null 
            && guest.getCriterion(CriterionName.GUEST_FOOD).getValue().equals("vegetarian")) {
            guestFood.setText("Végétarien");
        } else if (guest.getCriterion(CriterionName.GUEST_FOOD).getValue() != null 
            && guest.getCriterion(CriterionName.GUEST_FOOD).getValue().equals("nonuts")) {
            guestFood.setText("Sans fruits à coque");
        } else {
            guestFood.setText("Aucun");
        }

        if (host.getCriterion(CriterionName.HOBBIES).getValue() != null) {
            hostHobbies.setText(host.getCriterion(CriterionName.HOBBIES).getValue());
        } else {
            hostHobbies.setText("Aucun");
        }

        if (guest.getCriterion(CriterionName.HOBBIES).getValue() != null) {
            guestHobbies.setText(guest.getCriterion(CriterionName.HOBBIES).getValue());
        } else {
            guestHobbies.setText("Aucun");
        }

        if (host.getCriterion(CriterionName.PAIR_GENDER).getValue() != null && !host.getCriterion(CriterionName.PAIR_GENDER).getValue().isEmpty()) {
            hostGender.setText(host.getCriterion(CriterionName.PAIR_GENDER).getValue().equals("male") ? "Homme" : "Femme");
        } else {
            hostGender.setText("Aucun");
        }

        guestGender.setText(guest.getCriterion(CriterionName.GENDER).getValue().equals("male") ? "Homme" : "Femme");

        if (!EcranIntro.platform.getPreviousAffectation().isEmpty()) {
            if (host.hasLastGuest(guest)) {
                historyValue.setText("Oui");
            } else {
                historyValue.setText("Non");
            }
        } else {
            historyValue.setText("Pas d'historique");
        }

        resultAnimal.setText(host.animalCompatibility(guest) ? "Valide" : "Non valide");
        resultFood.setText(host.foodCompatibility(guest) ? "Valide" : "Non valide");
        resultFood.setText(host.foodCompatibility(guest) ? "Valide" : "Non valide");

        ArrayList<String> hostRegime = new ArrayList<String>(host.splitValues(host.getCriterion(CriterionName.HOST_FOOD)));
        ArrayList<String> guestRegime = new ArrayList<String>(host.splitValues(guest.getCriterion(CriterionName.GUEST_FOOD)));

        Collections.sort(hostRegime); Collections.sort(guestRegime);

        resultHobbies.setText(String.format("%d similaire(s)", Teenager.containsAllValuesCriterionName(hostRegime, guestRegime)));

        resultGender.setText(host.genderPref(guest) == 1 ? "Valide" : "Non valide");
    }
}
