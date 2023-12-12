package javafxtest.fxml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PersonList {
    private ObservableList<Person> persons = FXCollections.observableArrayList();

    public ObservableList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ObservableList<Person> persons) {
        this.persons = persons;
    }
}