package javafxtest.treetableview;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

//TreeTableView的内置样式,对加载数据方式的进一步解析
public class TreeTableViewTest02 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        DataPropertyForTTV data1 = new DataPropertyForTTV("张一",18,true,0.4);
        DataPropertyForTTV data2 = new DataPropertyForTTV("张二",19,false,0.1);
        DataPropertyForTTV data3 = new DataPropertyForTTV("张三",10,false,0.0);
        DataPropertyForTTV data4 = new DataPropertyForTTV("张四",33,true,0.9);
        DataPropertyForTTV data5 = new DataPropertyForTTV("张五",32,false,0.2);

        TreeTableView<DataPropertyForTTV> treeTableView = new TreeTableView<>();

        TreeItem<DataPropertyForTTV> rootTreeItem = new TreeItem<>(data1);
        TreeItem<DataPropertyForTTV> treeItem1 = new TreeItem<>(data2);
        TreeItem<DataPropertyForTTV> treeItem2 = new TreeItem<>(data3);
        TreeItem<DataPropertyForTTV> treeItem3 = new TreeItem<>(data4);
        TreeItem<DataPropertyForTTV> treeItem4 = new TreeItem<>(data5);
        rootTreeItem.getChildren().addAll(treeItem1,treeItem2,treeItem3);
        treeItem3.getChildren().addAll(treeItem4);

        treeTableView.setRoot(rootTreeItem);
        TreeTableColumn<DataPropertyForTTV,String> name_ttc = new TreeTableColumn<>("姓名");
        TreeTableColumn<DataPropertyForTTV,Number> age_ttc = new TreeTableColumn<>("年龄");
        TreeTableColumn<DataPropertyForTTV,Boolean> sex_ttc = new TreeTableColumn<>("性别");
        TreeTableColumn<DataPropertyForTTV,Double> score_ttc = new TreeTableColumn<>("分数");
        treeTableView.getColumns().addAll(name_ttc,age_ttc,sex_ttc,score_ttc);

        treeTableView.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
        rootTreeItem.setExpanded(true);

        //加载数据
        //name_ttc.setCellValueFactory(new TreeItemPropertyValueFactory<DataPropertyForTTV,String>("name"));
        name_ttc.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<DataPropertyForTTV, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<DataPropertyForTTV, String> param) {
                return param.getValue().getValue().getNameProperty();
            }
        });
        //age_ttc.setCellValueFactory(new TreeItemPropertyValueFactory<DataPropertyForTTV,Number>("age"));
        age_ttc.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<DataPropertyForTTV, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<DataPropertyForTTV, Number> param) {
                return param.getValue().getValue().getAgeProperty();
            }
        });
        //sex_ttc.setCellValueFactory(new TreeItemPropertyValueFactory<DataPropertyForTTV,Boolean>("sex"));
        sex_ttc.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<DataPropertyForTTV, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<DataPropertyForTTV, Boolean> param) {
                return param.getValue().getValue().getSexProperty();
            }
        });
        score_ttc.setCellValueFactory(new TreeItemPropertyValueFactory<DataPropertyForTTV,Double>("score"));

        //自定义单元格
        //启用编辑
        treeTableView.setEditable(true);
        //自带编辑实现
        //name_ttc.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        //自带数据选择实现(样式1)
        //name_ttc.setCellFactory(ChoiceBoxTreeTableCell.forTreeTableColumn("A","B"));
        //自带数据选择实现(样式2)
        name_ttc.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn("A","B"));
        //自带复选框实现
        sex_ttc.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(new TreeTableColumn<DataPropertyForTTV,Boolean>()));
        //自带进度条实现
        score_ttc.setCellFactory(ProgressBarTreeTableCell.forTreeTableColumn());

        Button button = new Button("修改数据");
        AnchorPane.setRightAnchor(button,0.0);
        anchorPane.getChildren().addAll(treeTableView,button);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(800);
        primaryStage.setWidth(800);
        primaryStage.show();

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                data1.setName("ZhangSan");
                data1.setScore(0.77);
            }
        });

        treeTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);//设置多选
        treeTableView.getSelectionModel().setCellSelectionEnabled(true);//启用单元格可选
        /*treeTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<DataPropertyForTTV>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<DataPropertyForTTV>> observable,
                                TreeItem<DataPropertyForTTV> oldValue, TreeItem<DataPropertyForTTV> newValue) {
                System.out.println("选择了:" + newValue);
            }
        });*/
        //输出选择的单元格的值
        treeTableView.getSelectionModel().getSelectedCells().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                ObservableList<TreeTablePosition<DataPropertyForTTV,?>> list = (ObservableList<TreeTablePosition<DataPropertyForTTV,?>>)observable;
                for(int i = 0;i < list.size();i++){
                    //获取单元格的位置对象
                    TreeTablePosition<DataPropertyForTTV,?> ttp = list.get(i);
                    //根据位置对象获取列对象,然后在列对象中用单元格的位置对象的行获取单元格包含的数据返回给Object
                    Object o = ttp.getTableColumn().getCellData(ttp.getRow());
                    //输出
                    System.out.println("行:" + ttp.getRow() + "列" + ttp.getColumn() + "数据" + o.toString());
                }
            }
        });
    }
}
