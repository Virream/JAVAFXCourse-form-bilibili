package javafxtest.buttonbar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class ButtonBarTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#231120");

        //类似于之前学的菜单栏
        ButtonBar buttonBar = new ButtonBar();

        Button button1 = new Button("APPLY");
        Button button2 = new Button("CANCEL_CLOSE");
        Button button3 = new Button("NO");

        //设置标签(按钮在不同平台上在bar内的顺序不同,可以指定一下平台,固定排序)
        ButtonBar.setButtonData(button1, ButtonBar.ButtonData.APPLY);
        ButtonBar.setButtonData(button2, ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonBar.setButtonData(button3, ButtonBar.ButtonData.NO);
        //true:和其他按钮保持宽高
        //ButtonBar.setButtonUniformSize(button1,true);

        buttonBar.getButtons().addAll(button1,button2,button3);
        anchorPane.getChildren().addAll(buttonBar);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();
    }
}
