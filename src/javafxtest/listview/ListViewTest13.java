package javafxtest.listview;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListViewTest13 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#10101000");

        ListView<String> listView = new ListView<>();
        listView.setPrefHeight(300);
        listView.setPrefWidth(300);
        ObservableList<String> observableList = listView.getItems();
        for(int i = 0;i < 5;i++){
            observableList.add("data-" + i);
        }

        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            //索引,用于记录鼠标指针在哪个单元格
            int position;
            @Override
            public ListCell<String> call(ListView<String> param) {
                Label label = new Label();
                label.setPrefHeight(20);
                ListCell<String> listCell = new ListCell<>(){
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty == false && item != null){
                            label.setText(item);
                            this.setGraphic(label);
                        }
                    }
                };

                //当鼠标放在ListView中的单元格上时触发
                listCell.hoverProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                        //newValue当鼠标在ListView中的单元格时这个值会变成true,不在时为false
                        //当鼠标指针在ListView中的单元格上并且label中有信息时将label的高设置为35
                        //当label被设置大小超过单元格大小后它会把单元格给撑开
                        if(newValue == true && label.getText().equals("") != true){
                            //根据label的内容获取对应的单元格索引 ?
                            position = param.getItems().indexOf(label.getText());
                            //预选框跟随鼠标指针
                            param.getFocusModel().focus(position);
                            label.setPrefHeight(35);
                        }else{
                            //还原大小
                            label.setPrefHeight(20);
                        }
                    }
                });
                return listCell;
            }
        });

        //滚轮滑动事件
        listView.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                //负数代表向下划,正数代表向上滑,值越大代表滑动的幅度越大
                System.out.println(event.getDeltaX());
            }
        });


        Button button = new Button("button");

        AnchorPane.setTopAnchor(listView,50.0);
        anchorPane.getChildren().addAll(listView,button);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();
    }
}
