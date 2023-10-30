package javafxtest.tableview;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

//向TableView加载数据
public class TableViewTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#101010");

        //可观察的列表
        ObservableList<Data> observableList = FXCollections.observableArrayList();
        //向列表中中添加数据
        for(int i = 0;i < 5;i++){
            boolean b = true;
            if(i % 2 ==0){
                b = false;
            }
            observableList.add(new Data("data-"+i,i * 7,i*70.3,b));
        }

        //TableView对象
        //虽然向TableView的构造器传入了observableList对象但是这并不会直接加载数据
        TableView<Data> tableView = new TableView<>(observableList);

        //列对象,泛型中第一个值是要获取数据的对象的类型,第二个是获取的数据的类型,在初始化列时可以指定列名
        TableColumn<Data,String> tc_name = new TableColumn<>("姓名");
        //列的工厂方法:setCellValueFactory,向列中加载值
        tc_name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Data, String> param) {
                //SimpleStringProperty实现了ObservableValue
                return new SimpleStringProperty(param.getValue().getName());
            }
        });
        //更简单的向列加载数据的方式
        //这个中方式用到了反射,它会根据你提供的String来找对应的get方法加载数据
        //这里给了name所以它会通过反射调用Dat对象的getName方法
        //tc_name.setCellValueFactory(new PropertyValueFactory<Data,String>("name"));


        TableColumn<Data,Number> tc_age = new TableColumn<>("年龄");
        tc_age.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Data, Number> param) {
                //注意SimpleIntegerProperty实现ObservableValue时源码中指定的泛型是Number而不是Integer
                return new SimpleIntegerProperty(param.getValue().getAge());
            }
        });
        //这里泛型将不再使用Number而是使用Integer,TableColumn<Data,Number> tc_age = new TableColumn<>("年龄");中的Number也要改
        //tc_age.setCellValueFactory(new PropertyValueFactory<Data,Integer>("age"));

        TableColumn<Data,Number> tc_score = new TableColumn<>("得分");
        tc_score.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Data, Number> param) {
                return new SimpleDoubleProperty(param.getValue().getScore());
            }
        });
        TableColumn<Data,Boolean> tc_is = new TableColumn<>("boolean");
        tc_is.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Data, Boolean> param) {
                return new SimpleBooleanProperty(param.getValue().getIs());
            }
        });

        //向TableView中添加数据要先添加列然后向列添加数据
        tableView.getColumns().addAll(tc_name,tc_age,tc_score,tc_is);

        //当点击这个按钮时会添加数据
        Button buttonAddData = new Button("添加数据");
        buttonAddData.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                observableList.add(new Data("数据?",0,0.0,true));
            }
        });
        //点击这个按钮时会修改数据,但是并不会使ListView的界面同步显示,原因在前面的例子已经解释过了
        Button button = new Button("修改数据");
        button.setTooltip(new Tooltip("没有效果?这很正常"));
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                observableList.get(0).setName("???");
                //tableView.refresh();//刷新仍然有用
            }
        });

        AnchorPane.setTopAnchor(button,30.0);
        AnchorPane.setTopAnchor(tableView,60.0);
        anchorPane.getChildren().addAll(buttonAddData,button,tableView);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();
    }
}
