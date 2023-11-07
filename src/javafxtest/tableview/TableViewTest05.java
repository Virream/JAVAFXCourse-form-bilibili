package javafxtest.tableview;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
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
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
//TableView常用方法,选择模式和监听
public class TableViewTest05 extends Application {
    public static void main(String[] args) {
        launch();
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
        //tableView.setItems();使用这个方法也可以加载数据

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

        TableColumn<Data,Number> tc_age = new TableColumn<>("年龄");
        tc_age.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Data, Number> param) {
                //注意SimpleIntegerProperty实现ObservableValue时源码中指定的泛型是Number而不是Integer
                return new SimpleIntegerProperty(param.getValue().getAge());
            }
        });

        TableColumn<Data,Number> tc_score = new TableColumn<>("得分");
        tc_score.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Data, Number> param) {
                return new SimpleDoubleProperty(param.getValue().getScore());
            }
        });
        TableColumn<Data,Boolean> tc_is = new TableColumn<>("boolean");
        //tc_is.setVisible(false);//是否隐藏
        tc_is.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Data, Boolean> param) {
                return new SimpleBooleanProperty(param.getValue().getIs());
            }
        });

        tableView.setPlaceholder(new Button("无数据"));
        tableView.setTableMenuButtonVisible(true);//启用菜单

        //向TableView中添加数据要先添加列然后向列添加数据
        tableView.getColumns().addAll(tc_name,tc_age,tc_score,tc_is);
        tableView.setFixedCellSize(30);//设置单元格尺寸
        tableView.setEditable(true);//启用编辑,需要配合下面的代码使用
        tableView.getSelectionModel().setCellSelectionEnabled(true);//开启cell的单选(好像一行叫什么tableRow,一列叫tableColumns,单元格叫tableCell)
        //tableView.getFocusModel().focus(2,tc_age);//聚焦于某行或某列或某单元格
        tc_name.setCellFactory(TextFieldTableCell.forTableColumn());
        //对于不是String类型的可以使用转换器
        tc_age.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                return String.valueOf(object.intValue());
            }

            @Override
            public Number fromString(String string) {
                return Integer.valueOf(string);
            }
        }));
        //设置多选
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //选择监听同ListView
        //对cell的监听
        tableView.getSelectionModel().getSelectedCells().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                //获取cell里的数据
                ObservableList<TablePosition> observableList1 = (ObservableList<TablePosition>)observable;
                //Object o = observableList1.get(0).getTableColumn().getCellData(observableList1.get(0).getRow());//感觉和循环里的逻辑差不多
                for (int i = 0;i < observableList1.size();i++){
                    //当尽行多选时这个for循环可以输出所有选择的数据,observableList1装的是选择的项(或者说装着选择的tableCell)
                    TablePosition tablePosition = observableList1.get(i);
                    Object o = tablePosition.getTableColumn().getCellData(tablePosition.getRow());
                    System.out.println("行:" + tablePosition.getRow() + " 列:" + tablePosition.getColumn() + " 数据:" + o);
                }
            }
        });

        Button button = new Button("选择cell");
        Button button2 = new Button("选择当前cell左边的cell");
        AnchorPane.setTopAnchor(button2,30.0);
        AnchorPane.setTopAnchor(tableView,60.0);
        anchorPane.getChildren().addAll(button,button2,tableView);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tableView.getSelectionModel().select(1,tc_age);
            }
        });
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tableView.getSelectionModel().selectLeftCell();
                tableView.requestFocus();
            }
        });
    }
}
