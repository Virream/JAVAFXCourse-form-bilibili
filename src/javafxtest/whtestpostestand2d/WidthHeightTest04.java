package javafxtest.whtestpostestand2d;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
//2023年8月13日01:07:25
//三个bounds的区别
public class WidthHeightTest04 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#746580");

        Button button1 = new Button("button1");
        button1.setEffect(new DropShadow());//给按钮设置一个阴影效果
        button1.setRotate(45);//设置旋转

        AnchorPane.setLeftAnchor(button1,20.0);
        AnchorPane.setTopAnchor(button1,20.0);

        anchorPane.getChildren().addAll(button1);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        //三个bounds(请忽视z轴,现在不讲3d)

        //获得不带任何效果(特效)的边界
        System.out.println(button1.getLayoutBounds());
        //获得带有效果的边界(关于minX为-9的猜测:getBoundsInLocal的maxX-getLayoutBounds的maxX,
        // 也就是说这9个像素是效果的大小)
        System.out.println(button1.getBoundsInLocal());
        //计算外切矩形的边框(边界框)
        System.out.println(button1.getBoundsInParent());
    }
}
