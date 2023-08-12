package javafxtest.whtestpostestand2d;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.net.IDN;


//2023年8月13日00:02:22
public class WidthHeightTest03 extends Application {
    Point2D button1ChinkPoint = null;//用于下面的碰撞检测
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#000000");

        Button button1 = new Button("button1");
        Button button2 = new Button("button2");

        AnchorPane.setTopAnchor(button2,100.0);
        AnchorPane.setLeftAnchor(button2,100.0);

        anchorPane.getChildren().addAll(button2,button1);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        Bounds button2_bounds = button2.getLayoutBounds();//获取button2的自身坐标系范围
        //将button2的自身坐标系的最小值的位置和最大值的位置放在Point2D对象中
        Point2D point2D_button2min = button2.localToParent(button2_bounds.getMinX(),button2_bounds.getMinY());
        Point2D point2D_button2max = button2.localToParent(button2_bounds.getMaxX(),button2_bounds.getMaxY());
        System.out.println(point2D_button2min);
        System.out.println(point2D_button2max);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            Bounds bounds_button1 = button1.getLayoutBounds();
            @Override
            public void handle(KeyEvent event) {
                //让button1可以移动
                if (event.getCode().getName().equals(KeyCode.D.getName())){
                    button1.setLayoutX(button1.getLayoutX() + 10);//每次移动10个像素
                    button1ChinkPoint = button1.localToParent(bounds_button1.getMaxX(),bounds_button1.getMaxY());//更新碰撞点位置
                }
                if (event.getCode().getName().equals(KeyCode.S.getName())){
                    button1.setLayoutY(button1.getLayoutY() + 10);
                    button1ChinkPoint = button1.localToParent(bounds_button1.getMaxX(),bounds_button1.getMaxY());
                }
                if(button1ChinkPoint == null){
                    return;
                }
                //碰撞检测button1的检测点设为右下角,button2的范围设置为自身坐标系范围
                //检测button2的右上角
                if(button1ChinkPoint.getY() >= point2D_button2min.getY() && button1ChinkPoint.getX() >= point2D_button2min.getX()){
                    if(button1ChinkPoint.getY() <= point2D_button2max.getY() && button1ChinkPoint.getX() <= point2D_button2max.getX()){
                        System.out.println("两按钮相撞");
                        System.out.println(button1ChinkPoint);
                        return;
                    }
                }
                System.out.println("未相撞");
            }
        });
    }
}
