package javafxtest.fxml;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;


public class MyController02 {
    @FXML
    private ListView<String> listView;
    @FXML
    private Button button1;
    public MyController02(){}
    @FXML
    public void initialize(){
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                System.out.println(newValue);
            }
        });
    }

    public Button getButton1() {
        return button1;
    }
}
