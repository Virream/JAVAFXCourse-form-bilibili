package javafxtest.scrollbartestandothertest;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScrollBarTest01 extends Application {
    //滚动条
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#231120");

        VBox vBox = new VBox();
        for(int i = 0;i < 10;i++){
            vBox.getChildren().addAll(new Button("BUTTON" + i));
        }
        //滚动条
        ScrollBar scrollBar = new ScrollBar();
        //设置水平
        scrollBar.setOrientation(Orientation.VERTICAL);

        //数值监听(通过监听和vBox联系在一起)
        scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                vBox.setLayoutY(-newValue.doubleValue());//设置vBox的Y轴位置
                System.out.println(newValue);
            }
        });
        scrollBar.setPrefHeight(200);//设置滚动条的高
        scrollBar.setPrefWidth(20);
//        scrollBar.setValue(50);//设置初始位置
//        scrollBar.setVisibleAmount(50);//设置滚动条内按钮的长度
        scrollBar.setUnitIncrement(20);//点击按钮时每次滚动多少
        scrollBar.setBlockIncrement(50);//设置点击滚动条的背景长条时滚动多少
        scrollBar.increment();//向下滚动

        AnchorPane.setLeftAnchor(scrollBar,100.0);
        anchorPane.getChildren().addAll(vBox,scrollBar);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        scrollBar.setMax(vBox.getHeight());//设置滚动的最大值(这里设置为了vbox的高度)
    }
}
