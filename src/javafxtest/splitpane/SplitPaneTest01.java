package javafxtest.splitpane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
//p48
public class SplitPaneTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#234900");

        Button button1 = new Button("button1");
        Button button2 = new Button("button2");
        Button button3 = new Button("button3");

        //面板(可拖动)
        SplitPane splitPane = new SplitPane();
        splitPane.setPrefWidth(400);
        splitPane.setPrefHeight(600);

//        splitPane.getItems().addAll(button1,button2,button3);//不能直接添加node,这样没有拖动效果
        StackPane stackPane1 = new StackPane();//要将这个布局放进去才有拖动调整大小
        StackPane stackPane2 = new StackPane();
        StackPane stackPane3 = new StackPane();
        stackPane1.getChildren().addAll(button1);
        stackPane2.getChildren().addAll(button2);
        stackPane3.getChildren().addAll(button3);
        stackPane1.setMinWidth(80);
//        splitPane.setOrientation(Orientation.VERTICAL);//设置垂直

        //设置某个元素占的区域的比例(但是这玩意是这么算的:从上一个splitPane的末尾到你指定的比例所在位置)
        splitPane.setDividerPosition(0,0.33);
        splitPane.setDividerPosition(1,0.66);
        splitPane.setDividerPosition(2,1);

        splitPane.getItems().addAll(stackPane1,stackPane2,stackPane3);

        anchorPane.getChildren().addAll(splitPane);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();
    }
}
