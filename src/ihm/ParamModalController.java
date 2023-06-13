package ihm;

import graphes.AffectationUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class ParamModalController implements EventHandler<ActionEvent> {
    @FXML
    Slider histWeight;
    @FXML
    Slider allergyWeight;
    @FXML
    Slider dietWeight;
    @FXML
    Slider genderWeight;
    @FXML
    Slider hobbyWeight;
    @FXML
    Label currentHistValue;
    @FXML
    Label currentAllergyValue;
    @FXML
    Label currentDietValue;
    @FXML
    Label currentGenderValue;
    @FXML
    Label currentHobbyValue;

    @Override
    public void handle(ActionEvent arg0) {
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }
    
    public void initialize() {
        allergyWeight.setValue(AffectationUtil.getWeightAllergy());
        histWeight.setValue(AffectationUtil.getWeightHistory());
        dietWeight.setValue(AffectationUtil.getWeightFood());
        genderWeight.setValue(AffectationUtil.getWeightGender());
        hobbyWeight.setValue(AffectationUtil.getWeightHobbies());

        currentAllergyValue.setText(String.valueOf(AffectationUtil.getWeightAllergy()));
        currentHistValue.setText(String.valueOf(AffectationUtil.getWeightHistory()));
        currentDietValue.setText(String.valueOf(AffectationUtil.getWeightFood()));
        currentGenderValue.setText(String.valueOf(AffectationUtil.getWeightGender()));
        currentHobbyValue.setText(String.valueOf(AffectationUtil.getWeightHobbies()));

        //Listeners
        histWeight.valueProperty().addListener((observable, oldValue, newValue) -> {
            onHistChange();
        });

        allergyWeight.valueProperty().addListener((observable, oldValue, newValue) -> {
            onAllergyChange();
        });

        hobbyWeight.valueProperty().addListener((observable, oldValue, newValue) -> {
            onHobbyChange();
        });

        dietWeight.valueProperty().addListener((observable, oldValue, newValue) -> {
            onDietChange();
        });

        genderWeight.valueProperty().addListener((observable, oldValue, newValue) -> {
            onGenderChange();
        });
    }

    public void allergyResetOnAction() {
        AffectationUtil.resetWeightAllergy();
        allergyWeight.setValue((AffectationUtil.getWeightAllergy()));
        currentAllergyValue.setText(""+allergyWeight.getValue());
    }

    public void dietResetOnAction() {
        AffectationUtil.resetWeightFood();
        dietWeight.setValue((AffectationUtil.getWeightFood()));
        currentDietValue.setText(""+dietWeight.getValue());
    }


    public void genderResetOnAction() {
        AffectationUtil.resetWeightGender();
        genderWeight.setValue((AffectationUtil.getWeightGender()));
        currentGenderValue.setText(""+genderWeight.getValue());
    }


    public void hobbyResetOnAction() {
        AffectationUtil.resetWeightHobbies();
        hobbyWeight.setValue((AffectationUtil.getWeightHobbies()));
        currentHobbyValue.setText(""+hobbyWeight.getValue());
    }


    public void histResetOnAction() {
        AffectationUtil.resetWeightHistory();
        histWeight.setValue((AffectationUtil.getWeightHistory()));
        currentHistValue.setText(""+histWeight.getValue());
    }


    public void onAllergyChange() {
        AffectationUtil.setWeightAllergy((int) allergyWeight.getValue());
        currentAllergyValue.setText(""+(int) allergyWeight.getValue());
    }
    public void onDietChange() {
        AffectationUtil.setWeightFood((int) dietWeight.getValue());
        currentDietValue.setText(""+(int) dietWeight.getValue());
    }
    public void onGenderChange() {
        AffectationUtil.setWeightGender((int) genderWeight.getValue());
        currentGenderValue.setText(""+(int) genderWeight.getValue());
    }
    public void onHistChange() {
        AffectationUtil.setWeightHistory((int) histWeight.getValue());
        currentHistValue.setText(""+(int) histWeight.getValue());
    }
    public void onHobbyChange() {
        AffectationUtil.setWeightHobbies((int) hobbyWeight.getValue());
        currentHobbyValue.setText(""+(int) hobbyWeight.getValue());
    }

    public void onClose() {

    }


}
