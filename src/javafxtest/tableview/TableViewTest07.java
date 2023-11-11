package javafxtest.tableview;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

//5种TableCell内置的格式
public class TableViewTest07 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        ObservableList<DataProperty> observableList = FXCollections.observableArrayList();
        observableList.add(new DataProperty("张三",16,0.0,false));
        observableList.add(new DataProperty("张一",26,0.8,true));
        observableList.add(new DataProperty("李四",56,0.5,false));
        observableList.add(new DataProperty("王五",23,0.2,true));

        TableView<DataProperty> tableView = new TableView<>(observableList);
        TableColumn<DataProperty,String> tc_name = new TableColumn<>("姓名");
        tc_name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataProperty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DataProperty, String> param) {
                return param.getValue().getNameProperty();
            }
        });
        TableColumn<DataProperty,Number> tc_age = new TableColumn<>("年龄");
        tc_age.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataProperty, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<DataProperty, Number> param) {
                return param.getValue().getAgeProperty();
            }
        });
        //假如想实现进度条的样式那么就需要这样写
        //在当前类中会导致界面不更新(wasUpdate一直是false),见TableViewTest02
        TableColumn<DataProperty,Double> tc_score = new TableColumn<>("得分");
        tc_score.setCellValueFactory(new PropertyValueFactory<DataProperty,Double>("score"));
        //TableColumn<DataProperty,Number> tc_score = new TableColumn<>("得分");
        /*tc_score.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataProperty, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<DataProperty, Number> param) {
                return param.getValue().getScoreProperty();
            }
        });*/
        TableColumn<DataProperty,Boolean> tc_is = new TableColumn<>("布尔值");
        tc_is.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataProperty, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<DataProperty, Boolean> param) {
                return param.getValue().getIsProperty();
            }
        });
        tableView.getColumns().addAll(tc_name,tc_age,tc_score,tc_is);
        //设置列的对齐方式(是不是哪里搞错了,怎么整个列都右对齐了,而不是仅数据右对齐)
        tableView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        //启用编辑
        tableView.setEditable(true);
        //自定义单元格样式-使得单元格可进行数值的选则
        //ChoiceBoxTableCell.forTableColumn()和ComboBoxTableCell.forTableColumn()的效果一样
        /*tc_is.setCellFactory(ChoiceBoxTableCell.forTableColumn(new StringConverter<Boolean>() {
            @Override
            public String toString(Boolean object) {
                //将输入的值转为String
                return String.valueOf(object);
            }

            @Override
            public Boolean fromString(String string) {
                return Boolean.valueOf(string);
            }
        }, false, true,null));//可变长度参数列表;后面几个参数是对单元格设置的选项*/

        //选择框效果(非常适合boolean这种数据类型)
        tc_is.setCellFactory(CheckBoxTableCell.forTableColumn(tc_is));

        //进度条效果,值的范围0.0-1.0,必须是double类型
        tc_score.setCellFactory(ProgressBarTableCell.forTableColumn());

        Button button = new Button("更改进度条进度");

        AnchorPane.setTopAnchor(button,500.0);
        anchorPane.getChildren().addAll(tableView,button);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(600);
        primaryStage.setWidth(800);
        primaryStage.show();

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                observableList.get(0).setScore(0.5);
                tableView.refresh();//刷新解决(或者新new一个对象直接替换)
            }
        });
    }
}
//多任务??我不熟