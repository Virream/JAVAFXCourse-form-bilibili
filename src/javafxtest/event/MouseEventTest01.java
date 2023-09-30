package javafxtest.event;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

//对鼠标事件的测试
public class MouseEventTest01 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        HBox hBox = new HBox(20);

        Button button1 = new Button("button1");
        Button button2 = new Button("button2");
        hBox.getChildren().addAll(button1,button2);
        AnchorPane.setTopAnchor(hBox,100.0);
        AnchorPane.setLeftAnchor(hBox,100.0);

        anchorPane.getChildren().addAll(hBox);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(600);
        primaryStage.setWidth(800);
        primaryStage.show();

        //鼠标单击事件
        button1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(event.getSceneY());//获取scene内鼠标点击的位置Y
                System.out.println(event.getSceneX());
                System.out.println("=========================");
                System.out.println(event.getScreenY());//获取在屏幕中鼠标点击的位置Y
                System.out.println(event.getScreenX());
                System.out.println("=========================");
                System.out.println(event.getX());//获取button中鼠标的位置X
                System.out.println(event.getY());
                System.out.println("================");

                System.out.println("事件源" + event.getSource());
                System.out.println("事件目标" + event.getTarget());//假如点击按钮上的文字那么事件目标将不再是button本身
                System.out.println("事件类型" + event.getEventType());
                System.out.println("使用的鼠标按键" + event.getButton());
                System.out.println("连击次数" + event.getClickCount());//多少时间内算连击应该是由平台决定
                System.out.println("鼠标右键是否被按下" + event.isSecondaryButtonDown());//具体来说检测的是当其他按键被按下时鼠标右键是否被按下中
            }
        });

        //鼠标按下的事件
        button1.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("鼠标按键被按下");
            }
        });
        //鼠标被释放的事件
        button1.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("鼠标按键释放");
            }
        });
        //鼠标的进入事件
        button1.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("鼠标进入button");
            }
        });
        //鼠标退出事件
        button1.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("鼠标退出button");
            }
        });
        //鼠标移动事件
        button1.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("鼠标在按钮内移动中...");
            }
        });
    }
}
