package javafxtest.transform;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

//缩放
public class ScaleTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        Button button1 = new Button("button1");
        button1.setPrefWidth(80);
        button1.setPrefHeight(80);
        button1.setStyle("-fx-background-color:#ff00ff");
        Button button2 = new Button("button2");
        button2.setPrefWidth(80);
        button2.setPrefHeight(80);
        AnchorPane.setTopAnchor(button1,30.0);
        AnchorPane.setLeftAnchor(button1,30.0);
        AnchorPane.setTopAnchor(button2,30.0);
        AnchorPane.setLeftAnchor(button2,30.0);

        anchorPane.getChildren().addAll(button1,button2);

        //x轴缩放比例,y轴缩放比例,缩放支点x,缩放支点y(参考系是node自身坐标系)
        //假如缩放系数为负数将会使得node的图像颠倒
        //x,y轴缩放50%支点为node自身坐标系中的X:20 Y:20
        Scale scale = new Scale(0.5,0.5,20,20);//在缩放时支点两边都会被缩小50%
        button2.getTransforms().addAll(scale);
        //直接对node执行缩放操作
        //button1.setScaleX(0.6);//此方法缩放支点是node的中心

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(800);
        primaryStage.setWidth(800);
        primaryStage.show();

        //虽然button2确实缩小了,但是prefH/W的值并没有改变
        System.out.println("button2_W: " + button2.getPrefWidth());
        System.out.println("button2_H: " + button2.getPrefHeight());
        //使用坐标变换来获取实际大小(更多关于坐标变换的信息,见whtestpostestand2d内的类)
        System.out.println(button2.getLocalToParentTransform().getMxx());//获取被缩放的比例
        System.out.println(button2.getLocalToParentTransform().getTx());//获取距离父元素原点的距离

        Bounds bounds = button2.getLayoutBounds();//获取node的自身坐标系的边界对象
        Bounds bounds2 = button2.localToParent(bounds);//将边界对象从自身坐标系转为父对象的坐标系
        bounds2.getMaxX();//可以获取X的最大最小值
        bounds2.getMinX();
        bounds2.getWidth();//也可以直接获取宽高
        bounds2.getHeight();

        //这里的createInverse()是Scale中的方法和Translate中的同名方法效果不一样
        //这里并不是取反,这里的算法是X=1/X,Y=1/Y,Z=1/Z,XYZ均为上面设置过的缩放倍数
        Scale scale2 = scale.createInverse();
        System.out.println(scale2);
        //算法:mxx * x + (1 - mxx) * getPivotX()
        //原缩放倍数 * 现在传入的倍数 + (1 - 原缩放倍数) * 缩放支点坐标
        Point2D point2D = scale.transform(0.2,0.2);
        System.out.println(point2D);
    }
}
