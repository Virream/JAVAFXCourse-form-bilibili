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


public class AnchorPanTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        //AnchorPlan 既可以绝对布局又可以相对布局
        AnchorPane anchorPane = new AnchorPane();
        stage.setWidth(500);
        stage.setHeight(500);
        Button button1 = new Button("按钮1");
        Button button2 = new Button("按钮2");

        //设置anchorPlan的内边距(将anchorPlan的原点向x和y移动10个像素)
        anchorPane.setPadding(new Insets(10));

        //设置node相对于父控件(anchorPane)的位置
        //设置上边框距离父控件10个像素,下面以此类推
        anchorPane.setTopAnchor(button1,10.0);//这是一个静态方法不建议这样调用
        AnchorPane.setLeftAnchor(button1,10.0);//建议这样调用
        AnchorPane.setRightAnchor(button1,10.0);
        AnchorPane.setBottomAnchor(button1,10.0);
        //失效,因为上面的方法生效后下面的就不生效了
        button1.setLayoutY(150);
        button1.setLayoutX(150);
        anchorPane.getChildren().addAll(button2,button1);
        anchorPane.setStyle("-fx-background-color:#FF3E96;");
        anchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("anchorPane的鼠标单击事件");
            }
        });

        //这是一个容器
        Group group = new Group();
        //以下代码无反应
        group.setStyle("-fx-background-color:#FF3E96;");
        group.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("group的鼠标单击事件");
            }
        });

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }
}
