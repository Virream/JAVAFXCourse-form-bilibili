package javafxtest.textarea;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.function.Consumer;

//文本查询,排序
public class TextAreaAndTextFieldText02 extends Application {
    int index = 0;
    String subStr;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#231120");

        HBox hBox = new HBox();
        VBox vBox = new VBox();
        //输入查询文字
        TextField textField = new TextField();
        //查询按钮
        Button button1 = new Button("查询");
        //排序(就不排序了吧)
        Button button2 = new Button("排序");
        //文本区域
        TextArea textArea = new TextArea();

        hBox.getChildren().addAll(textField,button1,button2);
        vBox.getChildren().addAll(hBox,textArea);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefWidth(300);
        anchorPane.getChildren().addAll(vBox);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textArea.getParagraphs().forEach(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) {
                        String value = charSequence.toString();
                        String findValue = textField.getText();
                        subStr = value.substring(index);
                        if(value.contains(findValue)){
                            textArea.requestFocus();
                            int i = subStr.indexOf(findValue);
                            int temp = i +index;
                            index = temp + findValue.length();
                            System.out.println("i="+i);
                            textArea.selectRange(temp,index);
                        }
                    }
                });
            }
        });


    }
}
