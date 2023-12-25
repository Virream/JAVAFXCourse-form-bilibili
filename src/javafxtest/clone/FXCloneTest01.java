package javafxtest.clone;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

//克隆
public class FXCloneTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        MyButton myButton = new MyButton("myButton");
        myButton.setPrefHeight(40 *3);
        myButton.setPrefWidth(40 *3);
        myButton.setStyle("-fx-background-color:#efef00");

        myButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("myButton setOnAction");
            }
        });

        myButton.setUserData("userdata_myButton");

        Node node = myButton.clone();

        node.setUserData("userData_node");

        HBox hBox = new HBox(20);
        hBox.getChildren().addAll(myButton,node);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(hBox);

        //奇怪的事情,虽然写了和教程中同样的代码,但是我的电脑上只显示一个button,但确实有两个button
        //经过验证在jdk1.8复现了教程中的情况,目前的jdk17是不会同时出现两个button的
        System.out.println(hBox.getChildren());
        //这里的克隆是浅克隆,两个对象除了内存地址不一样,内部指向的是同一个成员变量,所以myButton和node也有同样的触发事件,但是在现在的fx版本无法直观展示
        System.out.println("myButton_userData:" + myButton.getUserData() +
                            " // node_userData:" + node.getUserData());

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();
    }
}
//Cloneable接口表示此类可克隆,就像序列化接口一样,只是一个标志
class MyButton extends Button implements Cloneable{
    public MyButton(){
        super();
    }
    public MyButton(String text){
        super(text);
    }
    public MyButton(String text, Node node){
        super(text,node);
    }

    @Override
    public Node clone() throws CloneNotSupportedException {
        return (Node) super.clone();//由于我们知道克隆的一定是一个button所以返回的是button类型
    }
}