package javafxtest.layout;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.function.Consumer;

//网格布局
public class GridPaneTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        //网格布局
        Button button1 = new Button("button1");
        Button button2 = new Button("button2");
        Button button3 = new Button("button3");
        Button button4 = new Button("button4");
        Button button5 = new Button("button5");
        Button button6 = new Button("button6");
        Button button7 = new Button("button7");
        Button button8 = new Button("button8");
        Button button9 = new Button("button9");
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color:#214543");

        //节点 行 列
        //gridPane.add(button1,0,0);
        //gridPane.add(button2,0,1);
        gridPane.add(button3,0,2);
        gridPane.add(button4,1,0);
        gridPane.add(button5,1,1);
        gridPane.add(button6,1,2);
        //自动靠拢
        gridPane.add(button7,3,0);
        gridPane.add(button8,3,1);
        gridPane.add(button9,3,2);
        //设置某个组件的外边距
        GridPane.setMargin(button9,new Insets(10));

        //添加节点的方法2
        GridPane.setConstraints(button1,0,0);
        gridPane.getChildren().add(button1);

        //添加节点的方法3
        GridPane.setRowIndex(button2,1);
        GridPane.setColumnIndex(button2,0);
        gridPane.getChildren().add(button2);

        //遍历
        gridPane.getChildren().forEach(new Consumer<Node>() {
            @Override
            public void accept(Node node) {
                Button b = (Button)node;
                System.out.println(b.getText());
            }
        });

        //重叠布局Stack(栈布局)
        StackPane stackPane = new StackPane();//就不演示了


        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.setHeight(600);
        stage.setWidth(600);
        stage.show();
    }
}
