package javafxtest.dialog;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Optional;
import java.util.function.Consumer;
//Dialog简单例子
public class DialogTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#10101000");

        Button button1 = new Button("弹出Dialog!");

        anchorPane.getChildren().addAll(button1);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Dialog<ButtonType> dialog = new Dialog<>();//默认什么按钮都不加的话...这个玩意关不掉
                //添加两个按钮,确定和关闭
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CLOSE,ButtonType.NEXT,ButtonType.PREVIOUS);//前面是不是讲过类似的?有点熟悉
                //新建一个按钮对象,但是指向的是Dialog对象上的类型是ButtonType.OK的按钮
                Button buttonOk = (Button)dialog.getDialogPane().lookupButton(ButtonType.OK);
                dialog.setTitle("关于");//设置窗口标题
                //一个矩形
                Shape shapeRec = new Rectangle(10,10, Paint.valueOf("#110011"));
                dialog.setGraphic(shapeRec);//设置图标
                dialog.setHeaderText("这里是正文的标题");
                dialog.setContentText("这里是正文");
                //设置宽高,用于show前
                dialog.getDialogPane().setPrefSize(300,300);
                //设置位置,基于屏幕坐标
                //dialog.setX(500);

                //好像是等待用户输入
                Optional<ButtonType> optionalButtonType = dialog.showAndWait();
                //文档上这么说:如果存在一个值就执行该值给定的操作,否则什么都不干
                //点击面板上的按钮后这个dialog关闭了一下...但是它咋又显示了???...视频中说什么??这个弹窗有两级???咋就两级了??
                optionalButtonType.ifPresent(new Consumer<ButtonType>() {
                    @Override
                    public void accept(ButtonType buttonType) {
                        System.out.println("opt:" + buttonType);
                        if(buttonType == ButtonType.CLOSE){
                            dialog.setContentText("文字已改变!");
                        }else dialog.setContentText("");
                    }
                });

                //dialog.show();//回答上面的疑问,这里有个show()刚刚没注释掉和上面showAndWait()冲突了....这就是原因

                //设置宽高用于show后
                //dialog.setHeight(150);
                //dialog.setWidth(300);

                //关闭时触发
                dialog.setOnCloseRequest(new EventHandler<DialogEvent>() {
                    @Override
                    public void handle(DialogEvent event) {
                        System.out.println(event.getSource());
                    }
                });

                //转换器,好像可以通过call传进来的参数获取dialog上的所有按钮对象
                dialog.setResultConverter(new Callback<ButtonType, ButtonType>() {
                    @Override
                    public ButtonType call(ButtonType param) {
                        System.out.println("ResultConverter: " + param);
                        return param;
                    }
                });

                //ok按钮的监听事件
                buttonOk.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("ok!");
                    }
                });
            }
        });
    }
}
