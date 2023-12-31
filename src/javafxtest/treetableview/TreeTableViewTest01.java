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
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

//TreeTableView的简单使用
public class TreeTableViewTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        DataPropertyForTTV data1 = new DataPropertyForTTV("张一",18,true);
        DataPropertyForTTV data2 = new DataPropertyForTTV("张二",19,false);
        DataPropertyForTTV data3 = new DataPropertyForTTV("张三",10,false);
        DataPropertyForTTV data4 = new DataPropertyForTTV("张四",33,true);
        DataPropertyForTTV data5 = new DataPropertyForTTV("张五",32,false);

        //TreeTableView就是TreeView和TableView的结合体
        TreeTableView<DataPropertyForTTV> treeTableView = new TreeTableView<>();

        //TreeTableView中一行仍是一个TreeItem,一列是一个TreeTableColumn
        TreeItem<DataPropertyForTTV> rootTreeItem = new TreeItem<>(data1);
        TreeItem<DataPropertyForTTV> treeItem1 = new TreeItem<>(data2);
        TreeItem<DataPropertyForTTV> treeItem2 = new TreeItem<>(data3);
        TreeItem<DataPropertyForTTV> treeItem3 = new TreeItem<>(data4);
        TreeItem<DataPropertyForTTV> treeItem4 = new TreeItem<>(data5);
        rootTreeItem.getChildren().addAll(treeItem1,treeItem2,treeItem3);//设置TreeItem的父子关系
        treeItem3.getChildren().addAll(treeItem4);

        treeTableView.setRoot(rootTreeItem);//加载顶级TreeItem
        TreeTableColumn<DataPropertyForTTV,String> name_ttc = new TreeTableColumn<>("姓名");
        TreeTableColumn<DataPropertyForTTV,Number> age_ttc = new TreeTableColumn<>("年龄");
        TreeTableColumn<DataPropertyForTTV,Boolean> sex_ttc = new TreeTableColumn<>("性别");
        treeTableView.getColumns().addAll(name_ttc,age_ttc,sex_ttc);//加载列

        //设置列平分View
        treeTableView.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
        //设置根节点展开
        rootTreeItem.setExpanded(true);

        //加载数据!!!!!!!!!!!
        //与反射相关,根据提供的字符串(此处是name)自动寻找"getName()"方法获取值,由于此方法是直接返回的字符串自然被修改时不会触发界面更新,
        //但是当自定义数据类型中有返回Property属性的名为nameProperty()的方法时会调用此方法而不是调用getName(),这样一来修改数据时界面就可以自动更新了
        //总结:使用这种简单的加载数据的方法new TreeItemPropertyValueFactory时,会调用自定义数据类型中的方法获取值,类似"属性Property()"的方法的优先级高于
        //类似"get属性()"的方法的优先级,自定义数据类型中的获取Property属性的方法最好写成"属性Property()"(IDEA一直给我生成的就是这种方法,但我给改
        //成了"get属性Property()",我当时还在想为什么不带get这样写起来多顺手,IDEA是我没能理解你的好心...;之前好像总结过当两种写法同时存在时的界面更新情况,估计是错的吧)
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
            }
        });

        treeTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);//设置多选
        treeTableView.getSelectionModel().setCellSelectionEnabled(true);//启用单元格可选
        //选择监听
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
