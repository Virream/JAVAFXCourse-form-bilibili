package javafxtest.layout;

import com.sun.scenario.effect.impl.prism.ps.PPSOneSamplerPeer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class BorderPaneTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {

        AnchorPane anchorPane1 = new AnchorPane();
        anchorPane1.setStyle("-fx-background-color:#BBFFFF");
        anchorPane1.setPrefHeight(100);
        anchorPane1.setPrefWidth(100);
        AnchorPane anchorPane2 = new AnchorPane();
        anchorPane2.setStyle("-fx-background-color:#000080");
        anchorPane2.setPrefWidth(100);
        anchorPane2.setPrefHeight(100);
        AnchorPane anchorPane3 = new AnchorPane();
        anchorPane3.setStyle("-fx-background-color:#696969");
        anchorPane3.setPrefHeight(100);
        anchorPane3.setPrefWidth(100);
        AnchorPane anchorPane4 = new AnchorPane();
        anchorPane4.setStyle("-fx-background-color:#76EE00");
        anchorPane4.setPrefWidth(100);
        anchorPane4.setPrefHeight(100);
        AnchorPane anchorPane5 = new AnchorPane();
        anchorPane5.setStyle("-fx-background-color:#FF8247");
        anchorPane5.setPrefHeight(100);
        anchorPane5.setPrefWidth(100);
        Button button = new Button("button");

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color:#8A2BE2");

        //borderPane.setTop(anchorPane1);
        borderPane.setTop(button);
        borderPane.setLeft(anchorPane2);
        borderPane.setRight(anchorPane3);
        //中间的组件有更高的优先级,当周围的组件没有大小时,中间的组件会占用其他组件的位置
        borderPane.setCenter(anchorPane4);
        borderPane.setBottom(anchorPane5);
        //设置布局的内边距
        borderPane.setPadding(new Insets(20));
        //设置外边距
        //BorderPane.setMargin(anchorPane1,new Insets(3));
        //设置居右
        BorderPane.setAlignment(button, Pos.BASELINE_RIGHT);
        //获取组件
        Button button1 = (Button)borderPane.getTop();

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setWidth(600);
        stage.setHeight(600);
        stage.show();


    }
}
