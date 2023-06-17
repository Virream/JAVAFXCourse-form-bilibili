package javafxtest.hyperlink;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
//p26
public class HyperlinkTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //类似于html的超链接,但是没有点击跳转到xx,要自己写事件
        TilePane tilePane = new TilePane();
        Hyperlink hyperlink1 = new Hyperlink("www.bilibili.com");
        Hyperlink hyperlink2 = new Hyperlink("www.baidu.com",new Button("按钮"));
        tilePane.getChildren().addAll(hyperlink2,hyperlink1);
        //转跳
        hyperlink2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                HostServices hostServices = HyperlinkTest01.this.getHostServices();
                hostServices.showDocument(hyperlink2.getText());
            }
        });

        Scene scene = new Scene(tilePane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(600);
        primaryStage.setHeight(600);
        primaryStage.show();
    }
}
