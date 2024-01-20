package javafxtest.transform;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Shear;
import javafx.stage.Stage;

//变形(错切)
public class ShearTest01 extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        //X轴错切值,Y轴错切值,定点pivotX,pivotY
        Shear shear = new Shear(0.5,0,0,0);

        /*
        * 变形后的其中的某一点的计算公式
        *
        * 坐标系:node自身坐标系
        * x1 x1为要计算的一点变性前的坐标x,y
        *
        * x,y pivotX,pivotY均为shear对象中的值
        *
        * x2= pivotX + (x1 - pivotX) + x * (y1-pivotY)
        * y2= pivotY + (y1 - pivotY) + y * (x1-pivotX)
        *
        * */

        Button button1 = new Button("按钮1");
        button1.setPrefHeight(50);
        button1.setPrefWidth(50);
        Button button2 = new Button("按钮2");
        button2.setPrefWidth(50);
        button2.setPrefHeight(50);
        button2.setOpacity(0.5);//50%透明
        button2.setStyle("-fx-background-color:#ff0000");
        Button button3 = new Button("button3");
        button3.setPrefHeight(50);
        button3.setPrefWidth(50);

        AnchorPane.setTopAnchor(button1,50.0);
        AnchorPane.setTopAnchor(button2,50.0);
        AnchorPane.setTopAnchor(button3,100.0);
        AnchorPane.setLeftAnchor(button1,50.0);
        AnchorPane.setLeftAnchor(button2,50.0);
        AnchorPane.setLeftAnchor(button3,110.0);

        button2.getTransforms().addAll(shear);

        anchorPane.getChildren().addAll(button1,button2,button3);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.show();
    }
}
