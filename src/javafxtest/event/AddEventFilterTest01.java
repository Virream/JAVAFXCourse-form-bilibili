package javafxtest.event;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

//next:p78
//addEventFilter和addEventHandler
public class AddEventFilterTest01 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane);
        anchorPane.setStyle("-fx-background-color:#000000");

        HBox hBox = new HBox(20);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefWidth(200);
        hBox.setPrefHeight(200);
        hBox.setStyle("-fx-background-color:#ff0000");
        Button button1 = new Button("button1");
        button1.setStyle("-fx-background-color:#ffff00");
        Label label1 = new Label("label1");
        label1.setPrefHeight(30);
        label1.setPrefWidth(50);
        label1.setStyle("-fx-background-color:#ff00ff");
        hBox.getChildren().addAll(button1,label1);

        AnchorPane.setTopAnchor(hBox,100.0);
        AnchorPane.setLeftAnchor(hBox,100.0);

        anchorPane.getChildren().addAll(hBox);
        primaryStage.setScene(scene);
        primaryStage.setHeight(600);
        primaryStage.setWidth(800);
        primaryStage.show();

        //弹幕:addEventFilter是事件捕获，addEventHandler是事件冒泡

        /*
        * 使用addEventFilter添加监听器
        * 参数:事件类型,对应的EventHandler对象
        *
        * 需要了解的点:
        *   事件传递方向 父组件->子组件(不是继承的父子关系)
        *   当前代码父子组件关系 stage->scene->anchorPane->hBox->button,label
        *   当给子node添加事件监听后,假如事件被触发,那么从这个node的父级到这个node本身的所有相同事件类型都会触发一遍,
        *   当然假如父级对应的这个事件没有写任何代码那么看起来就没有任何反应,下面显示了三个层级关系,
        *   但实际上anchorPane的父级是Scene,Scene的父级是Stage
        *   但由于这两个层级没有写事件对应的代码所以没有任何对应输出
        * */

        //为了证明事件传递方向,所以没有按照层级添加事件
        hBox.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println("hBox.addEventFilter 源=" + event.getSource() + "目标=" + event.getTarget());
            }
        });
        anchorPane.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //event.consume();//阻止事件传递,这将使事件停止传递给下一级
                event.isConsumed();//判断事件传递是否被阻止
                System.out.println("anchorPane.addEventFilter 源=" + event.getSource() + "目标=" + event.getTarget());
            }
        });
        button1.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println("button1.addEventFilter 源=" + event.getSource() + "目标=" + event.getTarget());
                System.out.println("-------------------------------------");
            }
        });

        //当想验证addEventFilter和addEventHandler谁的优先级高时请将上面代码注视,将此段代码放出
        /*hBox.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("hBox.addEventFilter 源=" + event.getSource() + "目标=" + event.getTarget());
            }
        });
        anchorPane.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //event.consume();//阻止事件传递,这将使事件停止传递给下一级
                event.isConsumed();//判断事件传递是否被阻止
                System.out.println("anchorPane.addEventFilter 源=" + event.getSource() + "目标=" + event.getTarget());
            }
        });
        button1.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("button1.addEventFilter 源=" + event.getSource() + "目标=" + event.getTarget());
                System.out.println("-------------------------------------");
            }
        });*/


        //使用addEventHandler添加监听器,情况和上面基本相同但是事件传递的方向是相反的
        //即事件传递的方向是:子组件->父组件
        //注意Button无法向下传递事件(把label1换成button1试一下就知道了)
        label1.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("label1.addEventHandler 源=" + event.getSource() + "目标=" + event.getTarget());
            }
        });
        anchorPane.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("anchorPane.addEventHandler 源=" + event.getSource() + "目标=" + event.getTarget());
                System.out.println("-------------------------------------");
            }
        });
        hBox.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("hBox.addEventHandler 源=" + event.getSource() + "目标=" + event.getTarget());
            }
        });

        //当addEventFilter和addEventHandler同时监听一种事件时,addEventFilter的优先级更高
        //setOnXXX这种方式添加的事件优先级还要比上面的再第一级,用这种方式添加的事件实际上还是用EventHandler添加的(我没验证)
        //anchorPane和hBox同时使用了这两种监听方式监听同一种事件

        //移除监听器
        //hBox.removeEventFilter();
        //hBox.removeEventHandler();
    }
}
