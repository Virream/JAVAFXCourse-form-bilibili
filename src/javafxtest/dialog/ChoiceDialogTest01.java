package javafxtest.dialog;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

//ChoiceDialog是Dialog的子类
public class ChoiceDialogTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#10101000");

        Button button1 = new Button("弹出ChoiceDialog");

        anchorPane.getChildren().add(button1);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<String> observableList = FXCollections.observableArrayList();
                for(int i = 0;i < 6;i++){
                    observableList.add("data" + i);
                }
                //参数:默认选择项,一个可观察的列表 (这个默认选择项可以不写列表里的值)
                ChoiceDialog<String> choiceDialog = new ChoiceDialog<>("null",observableList);
                //获取选择的值
                choiceDialog.selectedItemProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        System.out.println(newValue);
                    }
                });
                choiceDialog.show();
            }
        });
    }
}
