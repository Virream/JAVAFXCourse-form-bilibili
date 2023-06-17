package javafxtest.textarea;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.function.UnaryOperator;
//文本过滤
public class TextAreaAndTextFieldText01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vBox = new VBox(10);
        TextField textField = new TextField();
        TextArea textArea = new TextArea();

        //文本过滤,禁止某些文字的输入
        textField.setTextFormatter(new TextFormatter<String>(new UnaryOperator<TextFormatter.Change>() {
            @Override
            public TextFormatter.Change apply(TextFormatter.Change change) {
                System.out.println("输入了:"+change.getText());
                //使用正则表达式控制会接收哪些输入的数据
                String s = change.getText();
                //假如输入的是a-z就接收
                if(s.matches("[a-z]*")){
                    return change;
                }
                return null;
            }
        }));
        //文本过滤,将xx替换为xx
        textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                textArea.setTextFormatter(new TextFormatter<String>(new StringConverter<String>() {
                    //文本将从这里出(这里接收的是fromString方法return的数据)
                    @Override
                    public String toString(String object) {
                        System.out.println("toString接收了:"+object);
                        return object;
                    }

                    //文本将从这里进(输入的文本会先被这里接收到)
                    @Override
                    public String fromString(String string) {
                        System.out.println("fromString接收了:"+string);
                        //文本过滤语句
                        if(string.contains("6")){
                            String s = string.replace("6","陸");
                            return s;
                        }
                        return string;
                    }
                }));
                //上面的文本过滤操作每过滤一次需要提交一次才会生效
                textArea.commitValue();
            }
        });

        vBox.getChildren().addAll(textArea,textField);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.setHeight(600);
        primaryStage.setWidth(600);
        primaryStage.show();
    }
}
