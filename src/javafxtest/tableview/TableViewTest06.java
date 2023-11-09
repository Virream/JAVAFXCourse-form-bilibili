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

import java.util.Comparator;
import java.util.function.Consumer;

//TableView自定义排序,列宽模式
public class TableViewTest06 extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#101010");

        ObservableList<Data> observableList = FXCollections.observableArrayList();
        for(int i = 0;i < 5;i++){
            boolean b = true;
            if(i % 2 ==0){
                b = false;
            }
            observableList.add(new Data("data-"+i,i * 7,i*70.3,b));
        }
        observableList.add(new Data("data-3",7,70.3,true));

        TableView<Data> tableView = new TableView<>(observableList);

        TableColumn<Data,String> tc_name = new TableColumn<>("姓名");
        tc_name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Data, String> param) {
                return new SimpleStringProperty(param.getValue().getName());
            }
        });

        TableColumn<Data,Number> tc_age = new TableColumn<>("年龄");
        tc_age.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Data, Number> param) {
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
        tc_is.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Data, Boolean> param) {
                return new SimpleBooleanProperty(param.getValue().getIs());
            }
        });

        tableView.setPlaceholder(new Button("无数据"));
        tableView.setTableMenuButtonVisible(true);//启用菜单
        //tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);//设置列宽的模式
        //自定义列宽的模式
        /*tableView.setColumnResizePolicy(new Callback<TableView.ResizeFeatures, Boolean>() {
            @Override
            public Boolean call(TableView.ResizeFeatures param) {
                if(param == null) return;//param在初始化时会为空
                param.getDelta();//获取拖动的距离数值,正数向右数值越大距离越远,负数相反
                param.getColumn();//获取当前列的列对象,可以配合param的更多方法自定义列宽拖动的变化逻辑
                return null;
                //return true;//如果返回true将会限制用户改变列宽
            }
        });*/
        //是否允许排序
        //tc_name.setSortable(false);
        //排序的优先级
        //tableView.getSortOrder().addAll(tc_name,tc_score);//先比较名字然后比较得分
        //自定义排序,一般不使用
        tableView.setSortPolicy(new Callback<TableView<Data>,Boolean>(){

            @Override
            public Boolean call(TableView<Data> param) {
                System.out.println("自定义排序执行");
                //完全不知道这里在写什么,特别是泛型
                param.getColumns().forEach(new Consumer<TableColumn<Data, ?>>() {
                    @Override
                    public void accept(TableColumn<Data, ?> dataTableColumn) {
                        //ASCENDING代表升序,当列名名是姓名,并且要以升序排列时会触发这里的逻辑
                        if(dataTableColumn.getText().equals("姓名")
                           && dataTableColumn.getSortType() == TableColumn.SortType.ASCENDING){
                            dataTableColumn.setSortNode(new Label("*升序"));//这里可以放一个Node,一般用于设置升序时的图标(就是那个小三角)
                            param.getItems().sort(new Comparator<Data>() {
                                @Override
                                public int compare(Data o1, Data o2) {
                                    return o1.getName().compareTo(o2.getName());
                                }
                            });
                        }else if(dataTableColumn.getText().equals("姓名")
                                && dataTableColumn.getSortType() == TableColumn.SortType.DESCENDING){
                            dataTableColumn.setSortNode(new Label("*降序"));
                            //DESCENDING是降序的意思,这里是降序的逻辑
                            param.getItems().sort(new Comparator<Data>() {
                                @Override
                                public int compare(Data o1, Data o2) {
                                    return o2.getName().compareTo(o1.getName());
                                }
                            });
                        }
                    }
                });
                //假如返回false就没效果
                return true;
            }
        });
        tableView.getColumns().addAll(tc_name,tc_age,tc_score,tc_is);

        tc_name.setCellFactory(TextFieldTableCell.forTableColumn());
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

        Button button = new Button("执行排序");
        //Button button2 = new Button("选择当前cell左边的cell");
        //AnchorPane.setTopAnchor(button2,30.0);
        AnchorPane.setTopAnchor(tableView,60.0);
        anchorPane.getChildren().addAll(button,tableView);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        //自定义某个列的排序逻辑
        /*tc_age.setComparator(new Comparator<Number>() {
            @Override
            public int compare(Number o1, Number o2) {
                return 0;
            }
        });*/
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tc_name.setSortType(TableColumn.SortType.DESCENDING);//设置排序模式
                //进行排序,有自定义排序就触发自定义的,没有就触发默认的
                tableView.sort();//假如你不点击列头或不设值列的排序模式那么列就没有任何排序方式(ASCENDING或DESCENDING),这个方法将不起效果
            }
        });

    }
}
