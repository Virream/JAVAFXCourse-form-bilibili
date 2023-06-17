package javafxtest.anchorpan;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class AnchorPanTest02 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        //AnchorPlan 既可以绝对布局又可以相对布局
        AnchorPane anchorPane = new AnchorPane();
        stage.setWidth(500);
        stage.setHeight(500);

        anchorPane.setStyle("-fx-background-color:#FF3E96;");
        //将按钮设置到group中,然后设置按钮相对于group的位置
        Group group1 = new Group();
        Button button1 = new Button("b1");
        Button button2 = new Button("b2");
        button1.setLayoutX(20);
        button1.setLayoutY(20);
        group1.getChildren().addAll(button1,button2);
        Group group2 = new Group();
        Button button3 = new Button("b3");
        Button button4 = new Button("b4");
        group2.getChildren().addAll(button3,button4);
        //将group添加进anchorPane,设置group相对于anchorPane的位置
        AnchorPane.setTopAnchor(group1,40.0);
        AnchorPane.setLeftAnchor(group1,40.0);
        anchorPane.getChildren().addAll(group1,group2);
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }
}
