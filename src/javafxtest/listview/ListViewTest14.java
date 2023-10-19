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
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

//拖动排序,将一个listView中的数据拖拽到另一个listView
public class ListViewTest14 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    //储存要拖动的对象的索引和内部的值
    int index = 0;
    String data = "";
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

        ListView<String> listView2 = new ListView<>();

        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            //索引,用于记录鼠标指针在哪个单元格
            int position = 0;

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

                //拖动事件
                //拖拽时将单元格的数据存储在拖拽板中
                listCell.setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Dragboard dragboard = listCell.startDragAndDrop(TransferMode.COPY_OR_MOVE);
                        ClipboardContent clipboardContent = new ClipboardContent();
                        clipboardContent.putString(data);
                        dragboard.setContent(clipboardContent);
                    }
                });

                //拖拽事件-拖拽进入-当鼠标指针进入单元格就设置预选框
                listCell.setOnDragEntered(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        position = param.getItems().indexOf(label.getText());
                        param.getFocusModel().focus(position);
                        System.out.println(position);
                    }
                });

                listCell.setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        event.acceptTransferModes(TransferMode.COPY);
                    }
                });

                listCell.setOnDragDropped(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        //假如拖到了无内容的单元格会返回-1,这会导致报错
                        if(position == -1){
                            //将所选单元格的位置设置为末尾
                            position = param.getItems().size() -1;
                        }
                        //不是很理解这里的逻辑...为什么要把data临时放在这里?
                        String temp = data;//??data的值和我预期的不一样
                        param.getItems().remove(index);
                        param.getItems().add(position,temp);
                        param.getSelectionModel().select(position);
                    }
                });
                return listCell;
            }
        });

        Button button = new Button("button");

        AnchorPane.setTopAnchor(listView,50.0);
        AnchorPane.setLeftAnchor(listView2,320.0);
        anchorPane.getChildren().addAll(listView,button,listView2);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        //选择监听,对选择的索引的监听
        listView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                index = newValue.intValue();
            }
        });
        //对单元格的监听
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue);
                data = newValue;
            }
        });

        //触发setOnDragDropped所需
        listView2.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.COPY);
            }
        });
        //将data添加到listView2
        listView2.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                listView2.getItems().add(data);
            }
        });
    }
}
