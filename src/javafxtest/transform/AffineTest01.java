package javafxtest.transform;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

//仿射变换
//自行了解......
public class AffineTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        Button button1 = new Button("按钮1");
        button1.setPrefHeight(100);
        button1.setPrefWidth(150);
        Button button2 = new Button("按钮1");
        button2.setPrefWidth(150);
        button2.setPrefHeight(100);
        button2.setStyle("-fx-background-color:ff0000");

        AnchorPane.setTopAnchor(button1,100.0);
        AnchorPane.setTopAnchor(button2,100.0);
        AnchorPane.setLeftAnchor(button1,100.0);
        AnchorPane.setLeftAnchor(button2,100.0);

        anchorPane.getChildren().addAll(button1,button2);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();
    }
}
