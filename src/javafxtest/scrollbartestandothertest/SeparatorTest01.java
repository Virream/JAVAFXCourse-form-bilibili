package javafxtest.scrollbartestandothertest;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SeparatorTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#231120");

        HBox hBox = new HBox();

        Button button1 = new Button("button1");
        Button button2 = new Button("button2");

        //分隔符
        Separator separator = new Separator(Orientation.HORIZONTAL);//生成时设置了水平
        separator.setOrientation(Orientation.VERTICAL);//设置成垂直

        separator.setValignment(VPos.CENTER);//设置当处于垂直时居中
        separator.setPrefHeight(200);
        separator.setPrefWidth(200);

        hBox.getChildren().addAll(button1,separator,button2);

        anchorPane.getChildren().addAll(hBox);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();
    }
}
