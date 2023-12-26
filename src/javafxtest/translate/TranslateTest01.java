package javafxtest.translate;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

//Translate的父类是Transform,Transform是不同的仿射变换的基类,其中的一个子类是Translate,有平移之意
//这是Transform的已知子类:Affine, Rotate, Scale, Shear, Translate
//Translate是通过仿射变换用来控制Node平移的一个类
public class TranslateTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();

        AnchorPane anchorPane2 = new AnchorPane();
        anchorPane2.setPrefHeight(500);
        anchorPane2.setPrefWidth(500);
        AnchorPane.setTopAnchor(anchorPane2,100.0);
        AnchorPane.setLeftAnchor(anchorPane2,100.0);
        anchorPane2.setStyle("-fx-background-color:#d1e2f3");

        anchorPane.getChildren().addAll(anchorPane2);

        Button button1 = new Button("button1");
        button1.setPrefHeight(80);
        button1.setPrefWidth(80);
        Button button2 = new Button("button2");
        button2.setPrefHeight(80);
        button2.setPrefWidth(80);
        Translate translate = new Translate(80,80);//代表平移
        button2.getTransforms().addAll(translate);//将平移操作添加到button2的Transforms列表中,这个列表中的仿射变换会在某个时机进行计算
        //定义为布局目的而添加到节点变换中的平移 x 坐标。该值应计算为将节点的位置从其当前 layoutBounds minX 位置（可能不是 0）调整到所需位置所需的偏移量。
        //原文:Defines the x coordinate of the translation that is added to this Node's transform for the purpose of layout.
        // The value should be computed as the offset required to adjust the position of the node from its current
        // layoutBounds minX position (which might not be 0) to the desired location.
        System.out.println(button2.layoutBoundsProperty().get().getMinX());
        button2.setLayoutX(80);//也就是说这里设置了button2.getLayoutBounds().getMinX()要再加上多少偏移量,这里只是设置这个值并没有立即对位置进行调整,至于何时计算...不知道
        button2.setLayoutY(80);
        //由于看不懂源码,所以只能记住Translate中的值会和setLayoutX/Y的值相加,来移动Node的位置
        button1.getTransforms().addAll();

        //取反
        System.out.println("-translate: " + translate.createInverse());
        //克隆
        translate.clone();
        //将translate的xy和传入的xy相加
        Point2D point2D = translate.transform(30,30);
        System.out.println(point2D);
        //将任意的值包装为point2d
        Point2D point2D_2 = translate.deltaTransform(20,20);
        System.out.println(point2D_2);
        Point2D point2D_3 = translate.inverseDeltaTransform(3,3);//将任意的值包装为point2d,好奇怪啊这里有什么意义吗?
        System.out.println(point2D_3);

        anchorPane2.getChildren().addAll(button1,button2);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();
    }
}
