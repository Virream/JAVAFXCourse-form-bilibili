package javafxtest.layout;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
//文本布局
public class TextFlowTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Text text1 = new Text("这里是文本1");
        Text text2= new Text("这里是文本2");
        Text text3 = new Text("这里是文本3");

        text1.setFont(Font.font(19));
        //设置文字颜色
        text1.setFill(Paint.valueOf("#457692"));
        //或
        //text1.setStyle("-fx-fill:#893452");

        TextFlow textFlow = new TextFlow();
        textFlow.getChildren().addAll(text1,text2,text3,new Button("按钮"));

        Scene scene = new Scene(textFlow);
        stage.setWidth(600);
        stage.setHeight(600);
        stage.setScene(scene);
        stage.show();
    }
}
