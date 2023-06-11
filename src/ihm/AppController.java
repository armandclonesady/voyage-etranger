package ihm;

import java.util.ArrayList;
import java.util.List;

import devoo.Country;
import devoo.IdComparator;
import devoo.Teenager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

public class AppController implements EventHandler<ActionEvent> {
    @FXML
    ComboBox<Country> countryComboBox;
    @FXML
    ListView<Teenager> teenagerList;
    @FXML
    Label name;
    @FXML
    Label surname;
    @FXML 
    TextField search;

    public void initialize() {
        countryComboBox.getItems().setAll(Country.values());
        countryComboBox.getItems().add(0, null);

        countryComboBox.setOnAction(this);
        teenagerList.getSelectionModel().selectionModeProperty().set(SelectionMode.SINGLE);
        teenagerList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            onStudentsListChange();
        });

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            onSearch();
        });

        App.platform.importCSV("adosAléatoires.csv");

        for (Teenager student : App.platform.getStudents()) {
            System.out.println(student);
        }

        teenagerList.getItems().setAll(App.platform.getStudents());
        teenagerList.getItems().sort(new IdComparator());
    }

    public void handle(ActionEvent event) {
        if (event.getTarget() == countryComboBox) {
            onCountryComboBoxChange();
        }
        else if (event.getTarget() == teenagerList) {
            onStudentsListChange();
        }
    }
    
    public void onCountryComboBoxChange() {
        teenagerList.getItems().clear();
        if (countryComboBox.getSelectionModel().getSelectedItem() == null) {
            teenagerList.getItems().setAll(App.platform.getStudents());
        }
        for (Teenager student : App.platform.getStudents()) {
            if (student.getCountry() == countryComboBox.getSelectionModel().getSelectedItem()) {
                teenagerList.getItems().add(student);
            }
        }
        teenagerList.getItems().sort(new IdComparator());
        teenagerList.getSelectionModel().selectFirst();
    }

    public void onStudentsListChange() {
        if (teenagerList.getSelectionModel().getSelectedItem() == null) {
            name.setText("");
            surname.setText("");
            return;
        }
        name.setText(teenagerList.getSelectionModel().getSelectedItem().getName());
        surname.setText(teenagerList.getSelectionModel().getSelectedItem().getForename());
    }

    public void onSearch() {
        onCountryComboBoxChange();
        List<Teenager> students = new ArrayList<Teenager>();
        for (Teenager student : teenagerList.getItems()) {
            if (student.toString().toLowerCase().contains(search.getText().toLowerCase()) || student.getForename().toLowerCase().contains(search.getText().toLowerCase())) {
                students.add(student);
            }
        }
        teenagerList.getItems().setAll(students);
        teenagerList.getItems().sort(new IdComparator());
        teenagerList.getSelectionModel().selectFirst();
    }
}
