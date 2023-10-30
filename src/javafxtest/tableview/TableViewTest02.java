package javafxtest.tableview;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

//向TableView加载数据,但是加载的数据类型换成了DataProperty
public class TableViewTest02 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#101010");

        //可观察的列表
        ObservableList<DataProperty> observableList = FXCollections.observableArrayList();
        //可观察的列表但是监听了name属性
        /*ObservableList<DataProperty> observableList = FXCollections.observableArrayList(new Callback<DataProperty, Observable[]>() {
            @Override
            public Observable[] call(DataProperty param) {
                return new Observable[]{param.getNameProperty()};
            }
        });*/
        //向列表中中添加数据
        for(int i = 0;i < 5;i++){
            boolean b = true;
            if(i % 2 ==0){
                b = false;
            }
            observableList.add(new DataProperty("data-"+i,i * 7,i*70.3,b));
        }

        //TableView对象
        TableView<DataProperty> tableView = new TableView<>(observableList);

        //列对象
        TableColumn<DataProperty,String> tc_name = new TableColumn<>("姓名");

        //列的工厂方法
        tc_name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataProperty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DataProperty, String> param) {
                //返回的是Property
                return param.getValue().getNameProperty();
            }
        });

        //更简单的向列加载数据的方式
        //tc_name.setCellValueFactory(new PropertyValueFactory<DataProperty,String>("name"));

        //需要注意的是:
        //上面在创建可观察列表对象observableList时并没有对FXCollections.observableArrayList()重写callBack方法来选择要监听的DataProperty的属性
        //但是在下面的按钮监听事件中对列表中的对象的数据进行修改时界面仍然同步了,javaFx已经帮我们做好了封装无需重写callBack,但是这将使wasUpdate()方法一直false
        //TableView中的列有的重写setCellValueFactory()的callBack有的使用new PropertyValueFactory<>()时javaFx将不会帮我们省略掉对可观察列表重写callBack
        //这会导致数据更新了但是界面没更新

        TableColumn<DataProperty,Number> tc_age = new TableColumn<>("年龄");
        tc_age.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataProperty, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<DataProperty, Number> param) {
                return param.getValue().getAgeProperty();
            }
        });

        TableColumn<DataProperty,Number> tc_score = new TableColumn<>("得分");
        tc_score.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataProperty, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<DataProperty, Number> param) {
                return param.getValue().getScoreProperty();
            }
        });
        TableColumn<DataProperty,Boolean> tc_is = new TableColumn<>("boolean");
        tc_is.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataProperty, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<DataProperty, Boolean> param) {
                return param.getValue().getIsProperty();
            }
        });

        //向TableView中添加列
        tableView.getColumns().addAll(tc_name,tc_age,tc_score,tc_is);

        //当点击这个按钮时会添加数据
        Button buttonAddData = new Button("添加数据");
        buttonAddData.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                observableList.add(new DataProperty("数据?",0,0.0,true));
            }
        });
        //点击这个按钮时会修改数据,并同步界面
        Button button = new Button("修改数据");
        button.setTooltip(new Tooltip("没有效果?这很正常"));
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                observableList.get(0).setName("???");
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
        observableList.addListener(new ListChangeListener<DataProperty>() {
            @Override
            public void onChanged(Change<? extends DataProperty> c) {
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
