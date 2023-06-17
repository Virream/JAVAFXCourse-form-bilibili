package javafxtest.textarea;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
//p33
public class TextAreaTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        //多行文本
        TextArea textArea = new TextArea();
        //设置默认
        textArea.setText("this is textArea!");
        //是否自动换行
        //textArea.setWrapText(true);
        //设置宽高
        textArea.setPrefHeight(200);
        textArea.setPrefWidth(200);
        //追加文本
        textArea.appendText("追加文本123456abcdef");
        //删除文本
        textArea.deleteText(0,3);
        //插入
        textArea.insertText(5,"-");
        //替换
        textArea.replaceText(0,4,"this not");
        //选择光标后面的一个字符(还有个选前面的方法)
        textArea.selectForward();
        //选择指定的位置后面的文本?(貌似会自动断词)
        textArea.selectPositionCaret(20);
        //选择文字,能设置起始位置结束位置
        textArea.selectRange(5,22);
        //移动光标到...
        textArea.positionCaret(25);
        //移动光标到起点/末尾
        textArea.home();//.end
        //从光标位置选择到最后//最前
        textArea.selectEnd();//.selectHome
        //是否可编辑
        //textArea.setEditable(false);
        //触发拷贝操作(要选中的有文本),用户可以直接ctrl+v粘贴文本
        //textArea.copy();

        anchorPane.getChildren().addAll(textArea);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(600);
        primaryStage.setHeight(600);
        primaryStage.show();

        anchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            int i = 0;
            @Override
            public void handle(MouseEvent event) {
                //设置进度条滚动到xx像素的位置
                textArea.setScrollLeft(i+=10);
            }
        });
        //滚动监听(监听进度条位置)
        textArea.scrollLeftProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(newValue);
            }
        });
    }
}
