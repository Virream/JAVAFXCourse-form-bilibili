package javafxtest.group;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GroupTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //获取缩放比例
        double d = Screen.getPrimary().getOutputScaleX();
        //创建按钮,并设置按钮位置大小
        Button button1 = new Button("b1");
        button1.setLayoutX(50);
        button1.setLayoutY(50);
        button1.setPrefHeight(50/d);
        button1.setPrefWidth(50/d);
        Button button2 = new Button("b2");
        button2.setLayoutX(100);
        button2.setLayoutY(0);
        button2.setPrefWidth(50/d);
        button2.setPrefHeight(50/d);
        Button button3 = new Button("b3");
        button3.setLayoutX(150);
        button3.setLayoutY(0);
        button3.setPrefHeight(50/d);
        button3.setPrefWidth(50/d);
        //group等于容器一类的东西
        Group group = new Group();
        //设置是group否自动控制node大小,好像一旦设定false就不显示了
        //group.setAutoSizeChildren(false);
        //将button添加进group
        group.getChildren().addAll(button1,button2,button3);//按钮叠在了一起,因为group没有布局
        //移除某个按钮组件
        //group.getChildren()返回的是一个list
        //group.getChildren().remove(0);
        //获取group中的node
        Object[] objects = group.getChildren().toArray();
        //设置透明度
        //group.setOpacity(0.5);
        //获取某个位置有没有node(只能检测布局中的精准位置)
        System.out.println(group.contains(50,50));

        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.setHeight(800/d);
        stage.setWidth(800/d);
        stage.show();
    }
}
