package javafxtest.event;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

//对键盘的事件的测试
//NEXT:P73
public class ButtonEventTest01 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        HBox hBox = new HBox(20);

        TextField textField = new TextField();
        Rectangle rectangle = new Rectangle(100,100);
        rectangle.setStyle("-fx-background-color:#ff1013");

        Button button1 = new Button("按钮1");
        Button button2 = new Button("按钮2");
        hBox.getChildren().addAll(button1,button2,textField,rectangle);

        //当按钮被按压就会触发
        button1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println(event.getCharacter());//返回按下按键的unicode编码?

                /*System.out.println(event.getEventType());//获得事件类型
                System.out.println(event.getSource());//获取事件源,事件源就是事件的发起者
                System.out.println(event.getTarget());//获取事件目标,事件目标就是事件作用在谁身上
                System.out.println(event.getText());//获取按下的按键的字符
                System.out.println(event.isControlDown());//ctrl键是否按下*/

                if(event.getCode().equals(KeyCode.A)){
                    System.out.println("A被按下");
                }

            }
        });

        //按键被释放时触发
        button1.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println(event.getCode() + "被释放");
            }
        });

        //当有输入焦点时触发
        textField.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //打印输入的字符
                System.out.println(event.getCharacter());
            }
        });

        //对图形设置这个事件并不能直接触发,因为图形没有办法直接获得焦点
        rectangle.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println("erc = " + event.getCode());
            }
        });
        //当鼠标点击图形时将给到图形焦点
        rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rectangle.requestFocus();
            }
        });
        anchorPane.getChildren().addAll(hBox);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(600);
        primaryStage.setWidth(800);
        primaryStage.show();
    }
}
