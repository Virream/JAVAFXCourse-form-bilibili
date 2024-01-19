package javafxtest.transform;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

//旋转
public class RotateTest01 extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        //参数:角度,旋转点的X,Y(node自身参考系)
        Rotate rotate = new Rotate(70,20,50);

        Button button1 = new Button("按钮1");
        button1.setPrefHeight(60);
        button1.setPrefWidth(60);
        Button button2 = new Button("按钮2");
        button2.setPrefWidth(60);
        button2.setPrefHeight(60);

        AnchorPane.setTopAnchor(button1,40.0);
        AnchorPane.setTopAnchor(button2,40.0);
        AnchorPane.setLeftAnchor(button1,40.0);
        AnchorPane.setLeftAnchor(button2,40.0);

        button2.getTransforms().addAll(rotate);

        anchorPane.getChildren().addAll(button1,button2);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.show();

        //获取button的原点在父组件中的位置
        System.out.println(button2.getLocalToParentTransform().getTx() + " -- " + button2.getLocalToParentTransform().getTy());

        //获取布局中button2的bounds
        Bounds bounds_b2 = button2.getLayoutBounds();
        //转到父组件中
        Bounds bounds_b2toP = button2.localToParent(bounds_b2);
        //此时给出的值是button2外接矩形的左上角的位置
        System.out.println(bounds_b2toP.getMinX() + " -- " + bounds_b2toP.getMinY());
    }
}
