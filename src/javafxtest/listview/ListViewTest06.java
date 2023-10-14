package javafxtest.listview;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

//关于更改数据后界面的更新问题
//请将本类和ListViewTest07对比
public class ListViewTest06 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#10101000");

        ObservableList<String> observableList = FXCollections.observableArrayList();
        ListView<String> listView = new ListView<>(observableList);
        listView.setPrefWidth(300);
        listView.setPrefHeight(300);
        for(int i = 0;i < 10;i++){
            observableList.add("data-" + i);
        }

        Button button = new Button("按钮");

        AnchorPane.setTopAnchor(listView,40.0);
        anchorPane.getChildren().addAll(button,listView);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                observableList.add(0,"abcd");
            }
        });

        //可观察列表的监听,只要有删除,添加等操作就输出最新的列表内容
        observableList.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                System.out.println(observable);
            }
        });
        //对可观察列表执行了什么操作的监听
        observableList.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
                while (c.next()){
                    if(c.wasAdded()){
                        System.out.println("执行了添加操作");
                    }
                }
            }
        });
    }
}
