package javafxtest.tableview;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.HashMap;

//向TableView加载Map中的数据
//2023年11月1日01:06:31 next:p95
public class TableViewTest04 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#101010");

        HashMap<String, SimpleStringProperty> hashMap1 = new HashMap<>();
        hashMap1.put("name",new SimpleStringProperty("A"));
        hashMap1.put("age",new SimpleStringProperty("16"));
        hashMap1.put("boolean",new SimpleStringProperty("false"));

        HashMap<String, SimpleStringProperty> hashMap2 = new HashMap<>();
        hashMap2.put("name",new SimpleStringProperty("B"));
        hashMap2.put("age",new SimpleStringProperty("18"));
        hashMap2.put("boolean",new SimpleStringProperty("true"));

        //可观察的列表
        ObservableList<HashMap<String,SimpleStringProperty>> observableList = FXCollections.observableArrayList();
        observableList.addAll(hashMap1,hashMap2);
        //TableView对象
        TableView<HashMap<String,SimpleStringProperty>> tableView = new TableView<>(observableList);

        //列对象
        TableColumn<HashMap<String,SimpleStringProperty>,String> tc_name = new TableColumn<>("姓名");
        //列的工厂方法
        tc_name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HashMap<String,SimpleStringProperty>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HashMap<String,SimpleStringProperty>, String> param) {
                //返回的是Property
                return param.getValue().get("name");
            }
        });
        //更简便的方式
        //tc_name.setCellValueFactory(new MapValueFactory("name"));//我也没研究出来这个泛型要写什么,反正不写也不报错就不写吧

        TableColumn<HashMap<String,SimpleStringProperty>,String> tc_age = new TableColumn<>("年龄");
        tc_age.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HashMap<String,SimpleStringProperty>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HashMap<String,SimpleStringProperty>, String> param) {
                //返回的是Property
                return param.getValue().get("age");
            }
        });

        TableColumn<HashMap<String,SimpleStringProperty>,String> tc_boolean = new TableColumn<>("boolean");
        tc_boolean.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HashMap<String,SimpleStringProperty>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HashMap<String,SimpleStringProperty>, String> param) {
                //返回的是Property
                return param.getValue().get("boolean");
            }
        });

        //向TableView中添加列
        tableView.getColumns().addAll(tc_name,tc_age,tc_boolean);

        //当点击这个按钮时会添加数据
        Button buttonAddData = new Button("添加数据");
        buttonAddData.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //observableList.add(new DataProperty("数据?",0,0.0,true));
            }
        });
        //点击这个按钮时会修改数据,并同步界面
        Button button = new Button("修改数据");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //observableList.get(0).setName("???");
                //tableView.refresh();//无需刷新
            }
        });

        Button button1 = new Button("删除数据");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                observableList.remove(0);
            }
        });

        //假如没有重写可观察列表的callBack那么wasUpdate将一直false
        observableList.addListener(new ListChangeListener<HashMap<String,SimpleStringProperty>>() {
            @Override
            public void onChanged(Change<? extends HashMap<String,SimpleStringProperty>> c) {
                while (c.next()){
                    System.out.print("是否触发了更新?");
                    System.out.println(c.wasUpdated());

                    System.out.print("是否触发了删除?");
                    System.out.println(c.wasRemoved());

                    System.out.print("是否触发了添加?");
                    System.out.println(c.wasAdded());
                    System.out.println("---------------");
                }
            }
        });

        AnchorPane.setTopAnchor(button,30.0);
        AnchorPane.setTopAnchor(tableView,60.0);
        AnchorPane.setLeftAnchor(button1,65.0);
        anchorPane.getChildren().addAll(buttonAddData,button,button1,tableView);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();
    }
}
