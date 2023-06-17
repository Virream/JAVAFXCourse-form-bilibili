package javafxtest.textfield;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TextFieldTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Group group = new Group();
        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.setHeight(500);
        stage.setWidth(500);

        //文本框
        TextField textField = new TextField();//可以给一个值但是没必要
        //设置文字大小
        textField.setFont(Font.font(13));
        //文本框是一个node所以button可以用的方法它也有很多可以用如设置css
        //提示
        Tooltip tip = new Tooltip("这是提示");
        textField.setTooltip(tip);
        //灰色提示
        textField.setPromptText("用户名:七个字");
        textField.setFocusTraversable(false);//取消焦点
        //使用事件控制字数
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if(newValue.length() > 7){
                    textField.setText(oldValue);
                }
            }
        });
        //监听用户光标选择了哪些文字
        textField.selectedTextProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                System.out.println(t1);
            }
        });
        //文本框的监听 只有按下确认键enter才有用 //改成监听鼠标单击就可以设置鼠标单击触发了
        textField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("文本框监听");
            }
        });
        //密码框
        PasswordField passwordField = new PasswordField();
        passwordField.setLayoutX(100);
        passwordField.setLayoutY(100);

        //标签
        Label label = new Label("这是一个标签");
        label.setLayoutY(50);
        label.setLayoutX(50);
        //标签设置鼠标单击事件
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("点击了标签");
            }
        });

        group.getChildren().add(label);
        group.getChildren().add(textField);
        group.getChildren().add(passwordField);
        stage.show();
    }
}
