package javafxtest.pagination;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
//p45
public class PaginationTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#00FFFF");

        //分页
        Pagination pagination = new Pagination();
        pagination.setStyle("-fx-background-color:#FF00FF");
        pagination.setPrefHeight(150);
        //设置总页数(默认无限)
        pagination.setPageCount(10);
        //pagination.setPageCount(Pagination.INDETERMINATE);//手动设置为无限(不确定)
        //显示几页可供选择
        pagination.setMaxPageIndicatorCount(5);
        //翻到第xx页(从0开始是第一页)
        pagination.setCurrentPageIndex(3);
        //设置样式(讲css时会说)
        pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);

        //添加node
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer param) {
                System.out.println("页数:" + param);
                return new Label("无内容");
            }
        });

        AnchorPane.setBottomAnchor(pagination,0.0);
        AnchorPane.setLeftAnchor(pagination,250.0);
        anchorPane.getChildren().addAll(pagination);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        //监听是第几页
        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(newValue);
            }
        });
    }
}
