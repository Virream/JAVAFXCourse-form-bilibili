package javafxtest.whtestpostestand2d;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.*;

//p50
//关于坐标
public class WidthHeightTest02 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#ffffff");

        Button button1 = new Button("Button1");

        HBox hBox = new HBox();
        hBox.setStyle("-fx-background-color:#055050");
        hBox.setPrefHeight(200);
        hBox.setPrefWidth(200);
        hBox.getChildren().addAll(button1);
        //设置居中
        hBox.setAlignment(Pos.CENTER);

        anchorPane.getChildren().addAll(hBox);

        AnchorPane.setLeftAnchor(hBox,20.0);
        AnchorPane.setTopAnchor(hBox,20.0);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        //输出按钮的坐标(node的原点一般都在左上角)
        //值均为0(如果不居中),因为此时输出的坐标的原点在父组件的左上角
        System.out.println("相对于父组件hbox的x:" + button1.getLayoutX());
        System.out.println("相对于父组件hbox的y:" + button1.getLayoutY());

        //须知:node,例如按钮是有一个自身坐标系的
        //范围是外切矩形,左上角依旧是原点,对于button的自身坐标系的范围就是显示在屏幕上的范围

        Bounds bounds = button1.getLayoutBounds();//新建一个Bounds接收button的自身坐标系的范围
        System.out.println("button自身坐标系的最小x:" + bounds.getMinX());
        System.out.println("button自身坐标系的最小y:" + bounds.getMinY());//上面这两条加起来在button的右上角

        System.out.println("button自身坐标系的最大X:" + bounds.getMaxX());
        System.out.println("button自身坐标系的最大Y:" + bounds.getMaxY());//上面这两条加起来这个位置在button的右下角

        //坐标转换
        // 自身坐标->父系坐标(如果要反过来转换只要把to前后的单词换一下位置即可,下同)
        Point2D point2D = button1.localToParent(bounds.getMaxX(),bounds.getMaxY());
        System.out.println("button右下角在父坐标系的位置是:" + "x=" + point2D.getX() + ",y=" + point2D.getY());
        // 自身坐标->场景坐标
        Point2D point2D1 = button1.localToScene(bounds.getMaxX(),bounds.getMaxY());
        System.out.println("button右下角在scene坐标系的位置是:" + "x=" + point2D1.getX() + ",y=" + point2D1.getY());
        // 自身坐标->屏幕坐标
        Point2D point2D2 = button1.localToScreen(bounds.getMaxX(),bounds.getMaxY());
        System.out.println("button右下角在屏幕坐标系的位置是:" + "x=" + point2D2.getX() + ",y=" + point2D2.getY());

        //检测某个坐标是否在范围内,可以传入一个Point2D,手动输入坐标等
        System.out.println(bounds.contains(100.0,100.0));//false,button自身坐标系的范围只有62x23

        //2023年8月13日00:01:08
    }
}
