package javafxtest.choicebox;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Comparator;

//p37
public class ChoiceBoxTest03 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {
        //排序按钮
        Button button = new Button("数字排序");

        ObservableList<String> list1 = FXCollections.observableArrayList();
        list1.addAll("数字","字母");
        ObservableList<String> list2 = FXCollections.observableArrayList();
        list2.addAll("1","2","3","4","5");
        ObservableList<String> list3 = FXCollections.observableArrayList();
        list3.addAll("A","B","C","D");

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#235122");
        //多级联动
        ChoiceBox<String> cb1 = new ChoiceBox<String>();
        cb1.setItems(list1);
        ChoiceBox<String> cb2 = new ChoiceBox<String>();
        cb1.setPrefWidth(200);
        cb2.setPrefWidth(200);

        AnchorPane.setTopAnchor(cb1,100.0);
        AnchorPane.setLeftAnchor(cb2,250.0);
        AnchorPane.setTopAnchor(cb2,100.0);

        anchorPane.getChildren().addAll(cb1,cb2,button);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();
        //联动
        cb1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.equals("数字")){
                    cb2.setItems(list2);
                }else{
                    cb2.setItems(list3);
                }
            }
        });
        //按钮单击事件
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                list2.sort(new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return Integer.valueOf(o2)-Integer.valueOf(o1);
                    }
                });
            }
        });
    }
}