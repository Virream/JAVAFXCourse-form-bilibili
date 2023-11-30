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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

//自定义单元格
public class TreeTableViewTest03 extends Application {
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
        name_ttc.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<DataPropertyForTTV, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<DataPropertyForTTV, String> param) {
                return param.getValue().getValue().getNameProperty();
            }
        });
        age_ttc.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<DataPropertyForTTV, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<DataPropertyForTTV, Number> param) {
                return param.getValue().getValue().getAgeProperty();
            }
        });
        sex_ttc.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<DataPropertyForTTV, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<DataPropertyForTTV, Boolean> param) {
                return param.getValue().getValue().getSexProperty();
            }
        });
        score_ttc.setCellValueFactory(new TreeItemPropertyValueFactory<DataPropertyForTTV,Double>("score"));

        //自定义单元格
        treeTableView.setEditable(true);
        //对name的自定义(和前面的自定义方式没什么不同)
        name_ttc.setCellFactory(new Callback<TreeTableColumn<DataPropertyForTTV, String>, TreeTableCell<DataPropertyForTTV, String>>() {
            @Override
            public TreeTableCell<DataPropertyForTTV, String> call(TreeTableColumn<DataPropertyForTTV, String> param) {
                TreeTableCell<DataPropertyForTTV,String> tableCell = new CheckBoxTreeTableCell<>(){
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty == false){
                            HBox hBox = new HBox();
                            Label label = new Label(item);
                            hBox.getChildren().addAll(label);
                            this.setGraphic(hBox);
                            this.setText(" ");//很奇怪...假如没有这行代码,显示会有问题
                        }else if(empty == true){
                            this.setGraphic(null);
                        }
                    }
                    //自定义编辑

                    @Override
                    public void startEdit() {
                        super.startEdit();
                        //懒得再写HBox了...
                        //getTreeTableRow()自从java17后已经废弃,使用getTableRow()来代替似乎没有问题
                        TextField textField = new TextField(this.getTableRow().getItem().getName());
                        //TextField textField = new TextField(this.getTreeTableRow().getItem().getName());
                        this.setGraphic(textField);
                        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent event) {
                                if (event.getCode() == KeyCode.ENTER){
                                    commitEdit(textField.getText());
                                }
                            }
                        });
                    }

                    //假如不重写提交和取消编辑那么系统会默认提供实现逻辑,但是只要重写提交编辑那么就需要重写取消编辑的逻辑
                    @Override
                    public void commitEdit(String newValue) {
                        super.commitEdit(newValue);
                        this.setGraphic(new Label(newValue));
                    }
                    @Override
                    public void cancelEdit() {
                        super.cancelEdit();
                        HBox hBox = new HBox();
                        Label label = new Label(this.getTableRow().getItem().getName());
                        hBox.getChildren().addAll(label);
                        this.setGraphic(hBox);
                        this.setText(" ");
                    }
                };
                return tableCell;
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
                data1.setScore(0.77);
            }
        });
    }
}
