package javafxtest.dialog;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//自定义弹窗
public class MyDialog extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#101010");

        Button button1 = new Button("弹出自定义弹窗");

        anchorPane.getChildren().add(button1);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                AnchorPane anchorPane1 = new AnchorPane();
                Stage stage = new Stage();
                stage.setHeight(150);
                stage.setWidth(300);
                //设置窗口的父级
                stage.initOwner(primaryStage);
                //设置布局面板
                Scene scene = new Scene(anchorPane1);
                //模态化设置
                stage.initModality(Modality.WINDOW_MODAL);
                //窗口样式设置
                stage.initStyle(StageStyle.UTILITY);
                //是否置顶
                stage.setAlwaysOnTop(true);
                //是否可以全屏
                stage.setResizable(false);

                //添加提示文本,图片什么的直接扔到AnchorPane即可

                stage.show();
            }
        });
    }
}
