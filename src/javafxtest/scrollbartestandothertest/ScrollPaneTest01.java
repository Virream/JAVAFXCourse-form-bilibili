package javafxtest.scrollbartestandothertest;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScrollPaneTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    //关于滚动面板的测试类
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#231120");

        VBox rootVbox = new VBox();

        VBox vBox = new VBox();
        for(int i = 0;i < 10;i++){
            vBox.getChildren().addAll(new Button("BUTTON1-" + i));
        }
        HBox hBox = new HBox();
        for (int i = 0;i < 10;i++){
            hBox.getChildren().addAll(new Button("Button2-" + i));
        }

        rootVbox.getChildren().addAll(hBox,vBox);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(300);
        scrollPane.setContent(rootVbox);//指定组件,只能指定一个node

        anchorPane.getChildren().addAll(scrollPane);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        //监听
//        scrollPane.vvalueProperty()//垂直数值监听水平是hvalue,默认值的范围是0.0-1.0.按比例滚动

    }
}
