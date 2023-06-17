package javafxtest.layout;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class FlowPaneTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        //流式布局
        Button button1 = new Button("button");
        Button button2 = new Button("button");
        Button button3 = new Button("button");
        Button button4 = new Button("button");
        Button button5 = new Button("button");
        FlowPane flowPane = new FlowPane();
        flowPane.setStyle("-fx-background-color:#214543");
        flowPane.getChildren().addAll(button1,button2,button3,button4,button5);

        //设置内边距
        flowPane.setPadding(new Insets(10));
        //设置居中
        flowPane.setAlignment(Pos.BASELINE_CENTER);
        //设置组件的水平间距
        flowPane.setHgap(10);
        //设置组件的垂直间距
        flowPane.setVgap(10);
        //设置布局方向
        flowPane.setOrientation(Orientation.VERTICAL);

        Scene scene = new Scene(flowPane);
        stage.setScene(scene);
        stage.setHeight(600);
        stage.setWidth(600);
        stage.show();
    }
}
