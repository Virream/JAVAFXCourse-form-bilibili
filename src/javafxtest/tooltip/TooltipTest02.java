package javafxtest.tooltip;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//自定义提示信息
//2023年9月16日15:48:46
//next:p67
public class TooltipTest02 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        VBox vBox = new VBox();
        vBox.setPrefWidth(200);
        vBox.setPrefHeight(200);
        vBox.setStyle("-fx-background-color:#451700");
        Tooltip tooltip = new Tooltip("这里是提示信息");
        tooltip.setWidth(200);
        tooltip.setHeight(200);
        tooltip.setGraphic(vBox);//大多数node都可以使用这个方法放一个node
        Button button1 = new Button("按钮1");
        button1.setTooltip(tooltip);

        anchorPane.getChildren().addAll(button1);
        Scene scene = new Scene(anchorPane);
        primaryStage.setHeight(600);
        primaryStage.setWidth(900);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
