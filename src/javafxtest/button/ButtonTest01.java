package javafxtest.button;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ButtonTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Button b1 = new Button("B1按钮");

        b1.setLayoutX(100);
        b1.setLayoutY(100);

        b1.setPrefHeight(200);
        b1.setPrefWidth(200);
        //设置按钮的字体和字体大小
        b1.setFont(Font.font("sans-serif",40/1.5));

        /*//设置文字颜色
        //#00FF00 6位代表颜色 后面再加两位代表透明度 #00FF0055
        b1.setTextFill(Paint.valueOf("#00FF00"));

        //设置按钮的背景元素
        //设置颜色,圆角弧度(填一个数值四个角统一,填四个数值四个角分别设置),设置边框距离(填一个数值四个边统一,填四个数值四个边分别设置)
        BackgroundFill backgroundFill = new BackgroundFill(Paint.valueOf("#8FBC8F"),new CornerRadii(20),new Insets(10));
        Background background = new Background(backgroundFill);
        b1.setBackground(background);

        //设置按钮的边框
        BorderStroke borderStroke = new BorderStroke(
                Paint.valueOf("#FFD700"),BorderStrokeStyle.DOTTED,new CornerRadii(5),new BorderWidths(5));
        Border border = new Border(borderStroke);
        b1.setBorder(border);*/

        //上面的设置可以使用css
        //注意设置css样式只可以设置一条,因为后设置的css样式会把前面的给覆盖
        b1.setStyle("-fx-background-color:#7CCD7C;" +
                "-fx-background-radius:20;" +
                "-fx-text-fill: yellow;"
        );

        //设置按钮事件
        b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //actionEvent可以使用getSource返回b1
                Button tempButton = (Button) actionEvent.getSource();
                System.out.println("事件源是"+tempButton.getText());

                System.out.println("按钮被点击");
            }
        });

        //设置双击事件(java8不会报错,javafx17报错....具体怎么改的不知道)
        //我人傻了,研究了半天,原来一不小心导入了awt的库
        b1.addEventHandler(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent) {
                //鼠标单击是否计数和系统设置有关,&&后面是只有鼠标左键计数
                if(mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    System.out.println("双击事件");
                    System.out.println(mouseEvent.getButton().name());//返回按钮的名字
                }
            }
        });

        //键盘输入检测
        b1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                System.out.println("按下了:"+keyEvent.getCode());
            }
        });
        b1.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                System.out.println("释放了"+keyEvent.getCode());
            }
        });

        Group rootGroup = new Group();
        rootGroup.getChildren().add(b1);
        Scene scene = new Scene(rootGroup);

        stage.setTitle("ButtonTest01");

        stage.setWidth(1920/1.5);
        stage.setHeight(1080/1.5);
        stage.setScene(scene);
        stage.show();

    }
}
