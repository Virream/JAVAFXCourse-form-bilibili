package javafxtest.hboxandvbox;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class BoxTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Button  button = new Button("button1");
        Button  button2 = new Button("button2");
        Button  button3 = new Button("button3");
        Button  button4 = new Button("button4");
        Button  button5 = new Button("button5");
        Button  button6 = new Button("button6");

        //水平布局
        HBox hBox = new HBox();
        hBox.setStyle("-fx-background-color:#222226");
        hBox.setPrefHeight(400);
        hBox.setPrefWidth(400);
        hBox.getChildren().addAll(button5,button4,button6,button2,button,button3);
        //垂直布局
        VBox vBox = new VBox();

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(600);
        anchorPane.setPrefWidth(600);
        anchorPane.getChildren().add(hBox);
        anchorPane.setStyle("-fx-background-color:#111111");
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();

    }
}
